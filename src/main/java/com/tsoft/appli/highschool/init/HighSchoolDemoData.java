/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.init;

import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.appli.highschool.model.Eleve;
import com.tsoft.appli.highschool.model.EleveInscrit;
import com.tsoft.appli.highschool.model.Matiere;
import com.tsoft.appli.highschool.model.Professeur;
import com.tsoft.appli.highschool.model.Serie;
import com.tsoft.appli.highschool.repository.ClasseRepository;
import com.tsoft.appli.highschool.repository.EcoleRepository;
import com.tsoft.appli.highschool.repository.EleveInscritRepository;
import com.tsoft.appli.highschool.repository.EleveRepository;
import com.tsoft.appli.highschool.repository.MatiereRepository;
import com.tsoft.appli.highschool.repository.ProfesseurRepository;
import com.tsoft.appli.highschool.repository.SerieRepository;
import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.utils.ObjectUtils;
import com.tsoft.utils.enumerations.Civilite;
import com.tsoft.web.init.DemoData;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class HighSchoolDemoData implements DemoData {

    @Autowired
    MatiereRepository matiereRepository;
    @Autowired
    ProfesseurRepository professeurRepository;
    @Autowired
    SerieRepository serieRepository;
    @Autowired
    ClasseRepository classeRepository;
    @Autowired
    EleveRepository eleveRepository;
    @Autowired
    EleveInscritRepository eleveInscritRepository;
    @Autowired
    EcoleRepository ecoleRepository;
    @Autowired
    AnneeService as;
    @Autowired
    ObjectUtils objectUtils;

    @Override
    public void populateData(HttpServletRequest req) throws Exception {
//         test  data 
        DataFactory df = new DataFactory();

        Ecole ecole = new Ecole();
        ecole.setNom("College Evangelique de SOA");
        ecole.setAdresse(df.getAddress());
        ecole.setSiteweb("http://www.twinsol.com");
        ecole.setTelephone("237 679 99 49 49");
        ecole.setSlogan("Egalite-Fraternite-Solidarité");
        ecole.setEmail(df.getEmailAddress());
        ecoleRepository.save(ecole);

//        creation de 2 professeurs par matiere
        Date minDate = df.getDate(1980, 1, 1);
        Date maxDate = df.getDate(1950, 1, 1);
        List<Matiere> matieres = matiereRepository.findAll();
        for (Matiere m : matieres) {
            for (int i = 0; i < 2; i++) {
                Professeur u = new Professeur();
                u.setCivilite(i % 2 == 0 ? Civilite.M : Civilite.MME);
                u.setNomprenom(df.getName());
                u.setDate_naissance((df.getDateBetween(minDate, maxDate)));
                u.setEmail(df.getEmailAddress());
                u.setTelephone("237 " + df.getNumberText(8));
                u.setLieu_naissance(df.getCity());
                u.setNumero_cni(Integer.parseInt(df.getNumberText(8)));
                u.setMatiere(m);
                professeurRepository.save(u);

            }
        }

//        creation de 2 classe par serie
        minDate = df.getDate(2005, 1, 1);
        maxDate = df.getDate(1996, 1, 1);
        List<Serie> series = serieRepository.findAll();
        for (Serie s : series) {
            List<Classe> classes = classeRepository.findBySerieLibelle(s.getLibelle());
            int index = (CollectionUtils.isEmpty(classes) ? 1 : classes.size() + 1);
            for (int i = 0; i < 2; i++) {
                Classe c = new Classe();
                c.setLibelle(s.getAbreviation() + (index + i));
                c.setSerie(s); 
                classeRepository.save(c);

                for (int j = 0; j < 50; j++) {
                    Eleve e = new Eleve();
                    e.setCivilite(i % 2 == 0 ? Civilite.M : Civilite.MLLE);
                    e.setNomprenom(df.getName());
                    e.setDate_naissance((df.getDateBetween(minDate, maxDate)));
                    e.setEmail(df.getEmailAddress());
                    e.setTelephone("237 " + df.getNumberText(8));
                    e.setLieu_naissance(df.getCity());
                    e.setNom_mere(df.getName());
                    e.setNom_pere(df.getName());
                    e.setTelephone_pere("237 " + df.getNumberText(8));
                    e.setTelephone_mere("237 " + df.getNumberText(8));
                    eleveRepository.save(e);

                    EleveInscrit ei = new EleveInscrit();
                    ei.setClasse(c);
                    ei.setEleve(e);
                    ei.setAnnee(as.getAnneeCourante());
                    eleveInscritRepository.save(ei);
                }
            }
        }

    }

}
