/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.init;

import com.tsoft.appli.highschool.model.AnneeScolaire;
import com.tsoft.appli.highschool.model.Creneau;
import com.tsoft.appli.highschool.model.Jour;
import com.tsoft.appli.highschool.model.Matiere;
import com.tsoft.appli.highschool.model.Niveau;
import com.tsoft.appli.highschool.model.Sequence;
import com.tsoft.appli.highschool.model.Serie;
import com.tsoft.appli.highschool.model.TypeMatiere;
import com.tsoft.appli.highschool.repository.AnneeScolaireRepository;
import com.tsoft.appli.highschool.repository.CreneauRepository;
import com.tsoft.appli.highschool.repository.JourRepository;
import com.tsoft.appli.highschool.repository.MatiereRepository;
import com.tsoft.appli.highschool.repository.SequenceRepository;
import com.tsoft.appli.highschool.repository.SerieRepository;
import com.tsoft.appli.highschool.repository.TypeMatiereRepository;
import com.tsoft.utils.enumerations.DataLifeCycle;
import com.tsoft.web.init.RealData;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
    AnneeScolaireRepository anneeScolaireRepository;
    @Autowired
    CreneauRepository creneauRepository;
    @Autowired
    SequenceRepository sequenceRepository;
    @Autowired
    TypeMatiereRepository typeMatiereRepository;
    @Autowired
    MatiereRepository matiereRepository;
    @Autowired
    JourRepository jourRepository;
    @Autowired
    SerieRepository serieRepository;

    @Override
    @Transactional
    public void populateData(HttpServletRequest req) throws Exception {
        //insert real data

        DataFactory df = new DataFactory();

        AnneeScolaire as = new AnneeScolaire();
        as.setDateDebut(LocalDate.now());
        as.setDateFin(LocalDate.now().plusMonths(9));
        as.setCycleVie(DataLifeCycle.ACTIF);
        anneeScolaireRepository.save(as);

        LocalTime timee = LocalTime.of(06, 30);
        List<Creneau> creneaux = new ArrayList();
        for (int i = 0; i < 9; i++) {
            Creneau cr = new Creneau();
            cr.setHeureDebut(timee);
            if (i == 2) {
                timee = timee.plusMinutes(15);
            } else if (i == 6) {
                timee = timee.plusMinutes(30);
            } else {
                timee = timee.plusMinutes(55);
            }
            cr.setHeureFin(timee);
            creneaux.add(cr);
        }
        creneauRepository.save(creneaux);

        List<Sequence> sequences = new ArrayList();
        for (int i = 0; i < 6; i++) {
            Sequence s = new Sequence();
            sequences.add(s);
        }
        sequenceRepository.save(sequences);

        TypeMatiere tm1 = new TypeMatiere();
        tm1.setLibelle("MATIERES SCIENTIFIQUES");
        typeMatiereRepository.save(tm1);
        TypeMatiere tm2 = new TypeMatiere();
        tm2.setLibelle("MATIERES LITTERAIRES");
        typeMatiereRepository.save(tm2);
        TypeMatiere tm3 = new TypeMatiere();
        tm3.setLibelle("MATIERES FORMATIONS HUMAINES");
        typeMatiereRepository.save(tm3);

        List<String> matieresScientiques = new ArrayList();
        matieresScientiques.add("Mathematique");
        matieresScientiques.add("Physique");
        matieresScientiques.add("Informatique");
        matieresScientiques.add("PCT");
        matieresScientiques.add("SVT");
        matiereRepository.save(createMatiere(matieresScientiques, tm1));

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
        matiereRepository.save(createMatiere(matieresLitteraires, tm2));

        List<String> matieressociales = new ArrayList();
        matieressociales.add("Education Vie Et Amour");
        matieressociales.add("ESF");
        matieressociales.add("Travail Manuel");
        matieressociales.add("Education a la Citoyennete");
        matiereRepository.save(createMatiere(matieressociales, tm3));

        Map<String, Niveau> series = new HashMap();
        series.put("Sixieme_6M", Niveau.NIVEAU1);
        series.put("Cinquieme_5M", Niveau.NIVEAU2);
        series.put("Quatrieme Espagnol_4ESP", Niveau.NIVEAU3);
        series.put("Quatrieme Allemand_4ALL", Niveau.NIVEAU3);
        series.put("Troisieme Espagnol_3ESP", Niveau.NIVEAU4);
        series.put("Troisieme Allemand_3ALL", Niveau.NIVEAU4);
        series.put("Seconde Espagnol_2ESP", Niveau.NIVEAU5);
        series.put("Seconde Allemand_2ALL", Niveau.NIVEAU5);
        series.put("Seconde C_2C", Niveau.NIVEAU5);
        series.put("Premiere Espagnol_PESP", Niveau.NIVEAU6);
        series.put("Premiere Allemand_PALL", Niveau.NIVEAU6);
        series.put("Premiere C_PC", Niveau.NIVEAU6);
        series.put("Premiere D_PD", Niveau.NIVEAU6);
        series.put("Terminale Espagnol_TESP", Niveau.NIVEAU7);
        series.put("Terminale Allemand_TALL", Niveau.NIVEAU7);
        series.put("Terminale C_TC", Niveau.NIVEAU7);
        series.put("Terminale D_TD", Niveau.NIVEAU7);

        List<Serie> seriess = new ArrayList();
        for (Map.Entry<String, Niveau> serie : series.entrySet()) {
            Serie s = new Serie();
            s.setLibelle(serie.getKey().split("_")[0]);
            s.setAbreviation(serie.getKey().split("_")[1]);
            s.setNiveau(serie.getValue());
            s.setInscription(new BigDecimal("10000"));
            s.setInscription_new(new BigDecimal("10000"));
            s.setTranche1(new BigDecimal("50000"));
            s.setTranche1_new(new BigDecimal("50000"));
            seriess.add(s);

        }
        serieRepository.save(seriess);

        List<Jour> jours = new ArrayList();
        Jour j = new Jour();
        j.setLibelle("Lundi");
        jours.add(j);
        j = new Jour();
        j.setLibelle("Mardi");
        jours.add(j);
        j = new Jour();
        j.setLibelle("Mercredi");
        jours.add(j);
        j = new Jour();
        j.setLibelle("Jeudi");
        jours.add(j);
        j = new Jour();
        j.setLibelle("Vendredi");
        jours.add(j);
        jourRepository.save(jours);

    }

}
