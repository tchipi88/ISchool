/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.init;

import com.tsoft.ischool.domain.Classe;
import com.tsoft.ischool.domain.ClasseEleve;
import com.tsoft.ischool.domain.Coefficient;
import com.tsoft.ischool.domain.Eleve;
import com.tsoft.ischool.domain.Matiere;
import com.tsoft.ischool.domain.Note;
import com.tsoft.ischool.domain.NotePeriode;
import com.tsoft.ischool.domain.Professeur;
import com.tsoft.ischool.domain.Serie;
import com.tsoft.ischool.domain.enumeration.Civilite;
import com.tsoft.ischool.domain.enumeration.Sexe;
import com.tsoft.ischool.repository.ClasseEleveRepository;
import com.tsoft.ischool.repository.ClasseRepository;
import com.tsoft.ischool.repository.CoefficientRepository;
import com.tsoft.ischool.repository.EleveRepository;
import com.tsoft.ischool.repository.MatiereRepository;
import com.tsoft.ischool.repository.NoteRepository;
import com.tsoft.ischool.repository.NotesPeriodeRepository;
import com.tsoft.ischool.repository.ProfesseurRepository;
import com.tsoft.ischool.repository.SerieRepository;
import com.tsoft.ischool.service.AnneeService;
import java.time.LocalDate;
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
    ClasseEleveRepository eleveInscritRepository;
    @Autowired
    CoefficientRepository coefficientRepo;

    @Autowired
    NotesPeriodeRepository notesPeriodeRepository;
    @Autowired
    NoteRepository noterepo;

    @Autowired
    AnneeService as;

    @Override
    public void populateData(HttpServletRequest req) throws Exception {
//         test  data 
        DataFactory df = new DataFactory();

//        creation de 2 professeurs par matiere
        Date minDate = df.getDate(1980, 1, 1);
        Date maxDate = df.getDate(1950, 1, 1);
        List<Matiere> matieres = matiereRepository.findAll();
        List<Serie> series = serieRepository.findAll();

        //populate notes
        NotePeriode np = new NotePeriode();
        np.setDateDebut(LocalDate.now().minusMonths(1));
        np.setDateFin(LocalDate.now().plusMonths(5));
        notesPeriodeRepository.save(np);

        matieres.stream().forEach((Matiere m)
                -> {
            series.stream().forEach(s -> {
                Coefficient c = new Coefficient();
                c.setMatiere(m);
                switch (m.getType()) {
                    case MATIERES_FORMATIONS_HUMAINES:
                        c.setValeur(1D);
                        break;
                    case MATIERES_LITTERAIRES:
                        c.setValeur(3D);
                        break;
                    case MATIERES_SCIENTIFIQUES:
                        c.setValeur(4D);
                        break;
                }

                c.setSerie(s);
                coefficientRepo.save(c);
            });

        });

        matieres.forEach((m) -> {
            for (int i = 0; i < 2; i++) {
                Professeur u = new Professeur();
                u.setCivilite(i % 2 == 0 ? Civilite.MR : Civilite.MRS);
                u.setNom(df.getName());
                u.setDateNaissance(LocalDate.now());
                u.setEmail(df.getEmailAddress());
                u.setTelephone("237 " + df.getNumberText(8));
                u.setLieuNaissance(df.getCity());
                u.setNumeroCNI(Integer.parseInt(df.getNumberText(8)));
                u.setMatiere(m);
                professeurRepository.save(u);

            }
        });

//        creation de 2 classe par serie
        minDate = df.getDate(2005, 1, 1);
        maxDate = df.getDate(1996, 1, 1);

        for (Serie s : series) {
            List<Classe> classes = classeRepository.findBySerie(s);
            int index = (CollectionUtils.isEmpty(classes) ? 1 : classes.size() + 1);
            for (int i = 0; i < 2; i++) {
                Classe c = new Classe();
                c.setId(s.getId() + (index + i));
                c.setSerie(s);
                classeRepository.save(c);

                for (int j = 0; j < 50; j++) {
                    Eleve e = new Eleve();
                    e.setSexe(j % 2 == 0 ? Sexe.F : Sexe.M);
                    e.setNom(df.getName());
                    e.setDateNaissance(LocalDate.now());
                    e.setTelephone("237 " + df.getNumberText(8));
                    e.setLieuNaissance(df.getCity());
                    e.setNomMere(df.getName());
                    e.setNomPere(df.getName());
                    e.setTelephonePere("237 " + df.getNumberText(8));
                    e.setTelephoneMere("237 " + df.getNumberText(8));
                    eleveRepository.save(e);

                    ClasseEleve ei = new ClasseEleve();
                    ei.setClasse(c);
                    ei.setEleve(e);
                    ei.setAnnee(as.getAnneeCourante());
                    eleveInscritRepository.save(ei);

                    matieres.stream().forEach(m -> {
                        Note n1 = new Note();
                        n1.setClasseEleve(ei);
                        n1.setMatiere(m);
                        n1.setNumeroSequence(1);
                        n1.setValeur(0.5D);
                        noterepo.save(n1);
                        Note n2 = new Note();
                        n2.setClasseEleve(ei);
                        n2.setMatiere(m);
                        n2.setNumeroSequence(2);
                        n2.setValeur(12.8D);
                        noterepo.save(n2);
                    });
                }
            }
        }

    }

}
