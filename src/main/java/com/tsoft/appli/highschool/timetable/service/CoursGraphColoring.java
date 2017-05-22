/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.timetable.service;

import com.tsoft.appli.highschool.model.Creneau;
import com.tsoft.appli.highschool.model.Jour;
import com.tsoft.appli.highschool.model.PlageHoraire;
import com.tsoft.appli.highschool.model.Timetable;
import com.tsoft.appli.highschool.repository.CreneauRepository;
import com.tsoft.appli.highschool.repository.JourRepository;
import com.tsoft.appli.highschool.repository.TimetableRepository;
import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.appli.highschool.timetable.graphColoring.Constants;
import com.tsoft.appli.highschool.timetable.graphColoring.Graph;
import com.tsoft.appli.highschool.timetable.graphColoring.IteratedGreedy;
import com.tsoft.appli.highschool.timetable.graphColoring.LocalSearch;
import com.tsoft.appli.highschool.timetable.graphColoring.MaxClique;
import com.tsoft.appli.highschool.timetable.graphColoring.Node;
import com.tsoft.service.process.EmptyActionProcess;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CoursGraphColoring extends EmptyActionProcess {
    
    @Autowired
    CoursToGraph ctg;
    
    @Autowired
    TimetableRepository timetableRepository;
    @Autowired
    JourRepository jourRepository;
    @Autowired
    CreneauRepository creneauRepository;
    @Autowired
    AnneeService as;
    
    @Override
    public String libelle() {
        return "Generation Emploi de Temps";
    }
    
    @Override
    public Object run(HttpSession session, HttpServletRequest request) throws Exception {
        //suppression de l'ancienne generation faite
        List<Timetable> edts_old = timetableRepository.findByAnnee(as.getAnneeCourante());
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
            int vertex = ((Integer) it.next()).intValue();
            Node node = graph.nodes[vertex];
            node.colorNode(col);
            col++;
        }
        
        Node[] nodes = graph.nodes;
        PossibleColorsComparator comparator = new PossibleColorsComparator();
        TreeSet uncoloredNodes = new TreeSet(comparator);
        List listNodes = new ArrayList();
        LinkedHashSet flags = new LinkedHashSet();
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
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
        //todo penser a enlever les creneux de mercredi apres midi et les heurs de pause
        List<Creneau> creneaux = creneauRepository.findAll();
        List<Jour> jours = jourRepository.findAll();
        List<PlageHoraire> phs = new ArrayList();
        Map<Integer, PlageHoraire> mapPlages = new HashMap();
        
        for (Jour j : jours) {
            for (Creneau c : creneaux) {
                if (!c.isDisponible()) {
                    continue;
                }
                if (j.getLibelle().equalsIgnoreCase("Mercredi") && c.getHeureDebut().isAfter((LocalTime.NOON))) {
                    continue;
                }
                PlageHoraire ph = new PlageHoraire();
                ph.setJour(j);
                ph.setCreneau(c);
                phs.add(ph);
            }
        }

        // int color = 0;
        //distribution des colors aux plages horairs
        for (int color = 1; color <= maxColor; color++) {
            mapPlages.put(color,getRandomPlagehoraire(phs));
        }

        //construction de l'EDT
//       Le graphe etant colorie chaque sommet(cours dans notre cas) a une couleur (creneau)
        List<Timetable> tosave = new ArrayList();
        for (int i = 0; i < colors.length; i++) {
            Timetable tm = new Timetable();
            tm.setCours(ctg.getCoursoccurences().get(i).getCours());
            tm.setCreneau(mapPlages.get(colors[i]).getCreneau());
            tm.setJour(mapPlages.get(colors[i]).getJour());
            tm.setAnnee(as.getAnneeCourante());
            tosave.add(tm);
        }
        timetableRepository.save(tosave);
        
        return true;
    }
    
    public PlageHoraire getRandomPlagehoraire(List<PlageHoraire> phs) {
        DataFactory df = new DataFactory();
        int pos = df.getNumberBetween(0, phs.size());
        PlageHoraire ph = phs.get(pos);
        phs.remove(pos);
        return  ph;
       
    }

    @Override
    public Class rubrique() throws Exception {
        return  Timetable.class;
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
