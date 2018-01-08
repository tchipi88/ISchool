/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.init;

import com.tsoft.ischool.domain.Annee;
import com.tsoft.ischool.domain.Creneau;
import com.tsoft.ischool.domain.Matiere;
import com.tsoft.ischool.domain.Serie;
import com.tsoft.ischool.domain.enumeration.Cycle;
import com.tsoft.ischool.domain.enumeration.Section;
import com.tsoft.ischool.domain.enumeration.TypeMatiere;
import com.tsoft.ischool.repository.CreneauRepository;
import com.tsoft.ischool.repository.MatiereRepository;
import com.tsoft.ischool.repository.SerieRepository;
import com.tsoft.ischool.service.AnneeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class HighSchoolRealData implements RealData {

    public List<Matiere> createMatiere(List<String> Matieres, TypeMatiere tm) {
        List<Matiere> result = new ArrayList();
        for (String ss : Matieres) {
            Matiere m = new Matiere();
            m.setLibelle(ss);
            m.setType(tm);
            result.add(m);
        }
        return result;
    }

    @Autowired
    AnneeService anneeScolaireRepository;
    @Autowired
    CreneauRepository creneauRepository;

    @Autowired
    MatiereRepository matiereRepository;

    @Autowired
    SerieRepository serieRepository;

    @Override
    @Transactional
    public void populateData(HttpServletRequest req) throws Exception {
        //insert real data

        DataFactory df = new DataFactory();

        Annee as = new Annee();
        as.setDateDebut(LocalDate.of(LocalDate.now().getYear(), Month.SEPTEMBER, 18));
        as.setDateFin(LocalDate.now().plusMonths(9));
        anneeScolaireRepository.create(as);

        LocalTime timee = LocalTime.of(07, 30);
        List<Creneau> creneaux = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Creneau cr = new Creneau();
            cr.setHeureDebut(timee);
           if (i == 3) {
                timee = timee.plusMinutes(15);
                 cr.setPause(true);
            } else if (i == 7) {
                timee = timee.plusMinutes(45);
                cr.setPause(true);
            } 
            else {
                timee = timee.plusHours(1);
            }
            cr.setHeureFin(timee);
            creneaux.add(cr);
        }
        creneauRepository.save(creneaux);

        List<String> matieresScientiques = new ArrayList();
        matieresScientiques.add("Mathematique");
        matieresScientiques.add("Physique");
        matieresScientiques.add("Informatique");
        matieresScientiques.add("PCT");
        matieresScientiques.add("SVT");
        matiereRepository.save(createMatiere(matieresScientiques, TypeMatiere.MATIERES_SCIENTIFIQUES));

        List<String> matieresLitteraires = new ArrayList();
        matieresLitteraires.add("Francais");
        matieresLitteraires.add("Anglais");
        matieresLitteraires.add("Redaction");
        matieresLitteraires.add("Allemand");
        matieresLitteraires.add("Espagnol");
        matieresLitteraires.add("Litterature");
        matieresLitteraires.add("Philosophie");
        matieresLitteraires.add("Etude de Texte");
        matieresLitteraires.add("Histoire");
        matieresLitteraires.add("Geographie");
        matiereRepository.save(createMatiere(matieresLitteraires, TypeMatiere.MATIERES_LITTERAIRES));

        List<String> matieressociales = new ArrayList();
        matieressociales.add("Education Vie Et Amour");
        matieressociales.add("ESF");
        matieressociales.add("Travail Manuel");
        matieressociales.add("Education a la Citoyennete");
        matiereRepository.save(createMatiere(matieressociales, TypeMatiere.MATIERES_FORMATIONS_HUMAINES));

        Map<String, Integer> series = new HashMap();
        series.put("Sixieme_6M", 1);
        series.put("Cinquieme_5M", 2);
        series.put("Quatrieme Espagnol_4ESP", 3);
        series.put("Quatrieme Allemand_4ALL", 3);
        series.put("Troisieme Espagnol_3ESP", 4);
        series.put("Troisieme Allemand_3ALL", 4);
        series.put("Seconde Espagnol_2ESP", 5);
        series.put("Seconde Allemand_2ALL", 5);
        series.put("Seconde C_2C", 5);
        series.put("Premiere Espagnol_PESP", 6);
        series.put("Premiere Allemand_PALL", 6);
        series.put("Premiere C_PC", 6);
        series.put("Premiere D_PD", 6);
        series.put("Terminale Espagnol_TESP", 7);
        series.put("Terminale Allemand_TALL", 7);
        series.put("Terminale C_TC", 7);
        series.put("Terminale D_TD", 7);

        List<Serie> seriess = new ArrayList();
        for (Map.Entry<String, Integer> serie : series.entrySet()) {
            Serie s = new Serie();
            s.setId(serie.getKey().split("_")[1]);
            s.setDescription(serie.getKey().split("_")[0]);
            s.setNiveau(serie.getValue());
            s.setCycle(Cycle.SECONDAIRE);
            s.setSection(Section.FRANCOPHONE);

            seriess.add(s);

        }
        serieRepository.save(seriess);

    }

}
