/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.service;

import com.tsoft.ischool.domain.Cours;
import com.tsoft.ischool.domain.Creneau;
import com.tsoft.ischool.domain.PlageHoraire;
import com.tsoft.ischool.domain.Timetable;
import com.tsoft.ischool.repository.CoursRepository;
import com.tsoft.ischool.repository.CreneauRepository;
import com.tsoft.ischool.repository.TimetableRepository;
import com.tsoft.ischool.service.dto.TimetableDTO;
import com.tsoft.ischool.service.timetable.Constants;
import com.tsoft.ischool.service.timetable.CoursToGraph;
import com.tsoft.ischool.service.timetable.Graph;
import com.tsoft.ischool.service.timetable.IteratedGreedy;
import com.tsoft.ischool.service.timetable.LocalSearch;
import com.tsoft.ischool.service.timetable.MaxClique;
import com.tsoft.ischool.service.timetable.Node;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
public class TimetableService {

    @Autowired
    CoursToGraph ctg;

    @Autowired
    TimetableRepository timetableRepository;

    @Autowired
    CreneauRepository creneauRepository;
    @Autowired
    CreneauService creneauService;
    @Autowired
    CoursRepository coursRepo;
    @Autowired
    AnneeService as;

    public List<TimetableDTO> convertToDTO(List<Timetable> timetables) throws Exception {
        Map<Creneau, List<Timetable>> timetableGroupByCreaneau = timetables.stream().collect(Collectors.groupingBy(t -> t.getCreneau()));
        List<Long> idsCreneaux = timetableGroupByCreaneau.keySet().stream().map(c -> c.getId()).collect(toList());
        idsCreneaux.add(-1L);
        List<Creneau> creneauxRemaining = creneauRepository.findByIdNotIn(idsCreneaux);
        List< TimetableDTO> result = new ArrayList();
        //convert List of Timetable to TimetableDTO
        timetableGroupByCreaneau.forEach((key, value) -> {
            TimetableDTO timetableDTO = new TimetableDTO(key);
            for (Timetable t : value) {
                switch (t.getJour()) {
                    case LUNDI:
                        timetableDTO.setLundi(t);
                        break;
                    case MARDI:
                        timetableDTO.setMardi(t);
                        break;
                    case MERCREDI:
                        timetableDTO.setMercredi(t);
                        break;
                    case JEUDI:
                        timetableDTO.setJeudi(t);
                        break;
                    case VENDREDI:
                        timetableDTO.setVendredi(t);
                        break;
                }
            }
            result.add(timetableDTO);
        });
        //add All creneau 
        creneauxRemaining.stream().forEach(c -> {
            result.add(new TimetableDTO(c));
        });
        //sorted result by Creneau
        Comparator<TimetableDTO> byCreneau = (TimetableDTO t1, TimetableDTO t2) -> t1.getCreneau().getHeureDebut().compareTo(t2.getCreneau().getHeureDebut());
        List< TimetableDTO> resultSorted = result.stream().sorted(byCreneau).collect(toList());
        return resultSorted;

    }

    public List<TimetableDTO> getTimetablesC(String classe) throws Exception {
        List<Timetable> timetables = timetableRepository.findByCoursClasseIdAndCoursAnnee(classe, as.getAnneeCourante());
        return convertToDTO(timetables);
    }

    public List<TimetableDTO> getTimetablesP(Long prof) throws Exception {
        List<Timetable> timetables = timetableRepository.findByCoursProfesseurIdAndCoursAnnee(prof, as.getAnneeCourante());
        return convertToDTO(timetables);
    }

    @Transactional
    public void generateEDT() throws Exception {
        //suppression de l'ancienne generation faite
        List<Timetable> edts_old = timetableRepository.findByCoursAnnee(as.getAnneeCourante());
        timetableRepository.delete(edts_old);

        System.out.println("Get Graph from Cours...");
        /* Read the graph from Constants.FILE */
        Graph graph = ctg.readGraph();

        /* Compute Clique */
        LinkedHashSet clique = MaxClique.computeClique(graph);

        /* Color the vertices of the clique with different colors */
        int k = clique.size();
        Iterator it = clique.iterator();
        int col = 1;
        while (it.hasNext()) {
            int vertex = ((Integer) it.next());
            Node node = graph.nodes[vertex];
            node.colorNode(col);
            col++;
        }

        Node[] nodes = graph.nodes;
        PossibleColorsComparator comparator = new PossibleColorsComparator();
        TreeSet uncoloredNodes = new TreeSet(comparator);
        List listNodes = new ArrayList();
        LinkedHashSet flags = new LinkedHashSet();
        for (Node node : nodes) {
            if (node.color == Constants.UNCOLORED) {
                node.computeDegreeSat(graph);
                uncoloredNodes.add(node);
                listNodes.add(node);
                flags.add(new Integer(node.value));
            }
        }

        while (!uncoloredNodes.isEmpty()) {
            Node node = (Node) uncoloredNodes.first();
            uncoloredNodes.remove(node);
            flags.remove(new Integer(node.value));
            //System.out.println(uncoloredNodes.size());
            for (int i = 1;; i++) {
                if (node.isValidColor(graph, i)) {
                    node.colorNode(i);
                    LinkedHashSet list = node.list;
                    it = list.iterator();
                    while (it.hasNext()) {
                        int vertex = ((Integer) it.next()).intValue();
                        Node n = graph.nodes[vertex];

                        if (uncoloredNodes.contains(n)) {
                            uncoloredNodes.remove(n);
                            n.computeDegreeSat(graph);
                            uncoloredNodes.add(n);
                        }
                    }

                    if (i > k) {
                        k = i;
                    }
                    break;
                }
            }
        }

        System.out.println(k + " coloring found using DSatur.");
        System.out.println("Applying Iterated Greedy Improvement...");

        int[] colors = IteratedGreedy.iteratedGreedy(k, graph);
        int maxColor = -1;
        for (int i = 0; i < colors.length; i++) {
            graph.nodes[i].color = colors[i];
            if (maxColor == -1) {
                maxColor = colors[i];
            } else if (maxColor < colors[i]) {
                maxColor = colors[i];
            }
        }

        System.out.println("Applying Local Search...");
        colors = LocalSearch.localSearch(graph, maxColor);
        maxColor = -1;
        for (int i = 0; i < colors.length; i++) {
            if (maxColor == -1) {
                maxColor = colors[i];
            } else if (maxColor < colors[i]) {
                maxColor = colors[i];
            }
        }

        System.out.println("Final Coloring of graph possible with " + maxColor + " colors.");
        System.out.println("Colors of Vertices: ");
        for (int i = 0; i < colors.length; i++) {
            System.out.print(colors[i] + " ");
        }

        //mappage des plages possibles avec les couleurs de graphes
        Map<Integer, PlageHoraire> mapPlages = new HashMap();
        List<PlageHoraire> phs = creneauService.getPlageHoraires();

        // int color = 0;
        //distribution des colors aux plages horairs
        for (int color = 1; color <= maxColor; color++) {
            mapPlages.put(color, getRandomPlagehoraire(phs));
        }

        //construction de l'EDT
//       Le graphe etant colorie chaque sommet(cours dans notre cas) a une couleur (creneau)
        List<Timetable> tosave = new ArrayList();
        for (int i = 0; i < colors.length; i++) {
            Timetable tm = new Timetable();
            tm.setCours(ctg.getCoursoccurences().get(i).getCours());
            tm.setCreneau(mapPlages.get(colors[i]).getCreneau());
            tm.setJour(mapPlages.get(colors[i]).getJour());
            tosave.add(tm);
        }
        timetableRepository.save(tosave);

    }

    public PlageHoraire getRandomPlagehoraire(List<PlageHoraire> phs) {
        DataFactory df = new DataFactory();
        int pos = df.getNumberBetween(0, phs.size());
        PlageHoraire ph = phs.get(pos);
        phs.remove(pos);
        return ph;

    }

    public Timetable save(Timetable timetable) throws Exception {
        Optional<Timetable> timetablePresent = null;
        //si la classe est libre 
        timetablePresent = timetableRepository.findByJourAndCreneauAndCoursClasseAndCoursAnnee(timetable.getJour(), timetable.getCreneau(), timetable.getCours().getClasse(), as.getAnneeCourante());
        if (timetablePresent.isPresent()) {
            throw new Exception("Le cours suivant : " + timetablePresent.get().getCours().getProfesseur().getNom() + "  - "
                    + timetablePresent.get().getCours().getMatiere().getLibelle() + " est deja attribué à cette classe "
                    + timetablePresent.get().getCours().getClasse().getId() + " pour la plage horaire "
                    + timetablePresent.get().getJour() + " " + timetablePresent.get().getCreneau().getHeureDebut());
        }
        //si le prof est libre
        timetablePresent = timetableRepository.findByJourAndCreneauAndCoursProfesseurAndCoursAnnee(timetable.getJour(), timetable.getCreneau(), timetable.getCours().getProfesseur(), as.getAnneeCourante());
        if (timetablePresent.isPresent()) {
            throw new Exception("Le cours suivant : " + timetablePresent.get().getCours().getProfesseur().getNom() + "  - "
                    + timetablePresent.get().getCours().getMatiere().getLibelle() + " est deja attribué à cette classe "
                    + timetablePresent.get().getCours().getClasse().getId() + " pour la plage horaire "
                    + timetablePresent.get().getJour() + " " + timetablePresent.get().getCreneau().getHeureDebut());
        }

        if (timetable.getId() == null) {
            //Lors d'un ajout via linterface on check son volume horaire
            Integer volumeHoraire = timetableRepository.countByCoursProfesseurAndCoursAnnee(timetable.getCours().getProfesseur(), as.getAnneeCourante());
            List<Cours> cours = coursRepo.findByAnneeAndProfesseurId(as.getAnneeCourante(), timetable.getCours().getProfesseur().getId());
            if (volumeHoraire >= cours.stream().map(c -> c.getDureeHeures()).reduce(0, Integer::sum)) {
                throw new Exception("Le volume horaire de " + volumeHoraire + " heures du professeur " + timetable.getCours().getProfesseur().getNom() + " est atteint");
            }
        }
        return timetableRepository.save(timetable);
    }

    public static class PossibleColorsComparator implements Comparator {

        public int compare(Object o1, Object o2) {
            Node node1 = (Node) o1;
            Node node2 = (Node) o2;

            if (node1.value == node2.value) {
                return 0;
            }

            if (node1.degreeSat < node2.degreeSat) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
