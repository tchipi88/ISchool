/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.timetable.service;

import com.tsoft.appli.highschool.model.Classe;
import com.tsoft.appli.highschool.model.Ecole;
import com.tsoft.appli.highschool.model.Professeur;
import com.tsoft.appli.highschool.model.Timetable;
import com.tsoft.appli.highschool.repository.ClasseRepository;
import com.tsoft.appli.highschool.repository.EcoleRepository;
import com.tsoft.appli.highschool.repository.ProfesseurRepository;
import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.exception.BusinessException;
import com.tsoft.service.process.EmptyCustomActionProcess;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tchipi
 */
@Scope("session")
@RestController
@RequestMapping("/app")
public class PrintTimetable extends EmptyCustomActionProcess {

    @Autowired
    JdbcTemplate jt;
    @Autowired
    AnneeService as;

    @Override
    public String libelle() {
        return "Emploi de temps";
    }

    @Autowired
    EcoleRepository ecoleRepository;
    @Autowired
    ClasseRepository classeRepository;
    @Autowired
    ProfesseurRepository professeurRepository;

    @RequestMapping(value = "/printTimetablesALLC",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> printTimetablesALLC() throws Exception {
        String reportfile = "classpath:com/tsoft/appli/highschool/timetable/service/EDTClasseAll.jasper";
        //remplissage des parametres du report
        Map params = new HashMap();
        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("codeannee", as.getAnneeCourante().getCode());

        //information about school
        if (ecoleRepository.findAll().isEmpty()) {
            throw new com.tsoft.exception.BusinessException("Paramètres de l'ecole non definis");
        }
        Ecole ecole = ecoleRepository.findAll().get(0);
        params.put("nomecole", ecole.getNom());
        params.put("adressecole", ecole.getAdresse());
        params.put("telecole", ecole.getTelephone());
        //determination du chemin des subreports
        params.put("SUBREPORT_DIR", resourceLoader.getResource(reportfile).getFile().getParent() + File.separator);

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTClasseALL"
                + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                , params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return download(destfile);
    }

    @RequestMapping(value = "/printTimetablesALLP",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> printTimetablesALLP() throws Exception {
        String reportfile = "classpath:com/tsoft/appli/highschool/timetable/service/EDTProfAll.jasper";
        //remplissage des parametres du report
        Map params = new HashMap();
        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("codeannee", as.getAnneeCourante().getCode());

        //information about school
         if (ecoleRepository.findAll().isEmpty()) {
            throw new com.tsoft.exception.BusinessException("Paramètres de l'ecole non definis");
        }
        Ecole ecole = ecoleRepository.findAll().get(0);
        params.put("nomecole", ecole.getNom());
        params.put("adressecole", ecole.getAdresse());
        params.put("telecole", ecole.getTelephone());
        //determination du chemin des subreports
        params.put("SUBREPORT_DIR", resourceLoader.getResource(reportfile).getFile().getParent() + File.separator);

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTProfALL"
                + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                , params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return download(destfile);
    }

    @RequestMapping(value = "/printTimetableC/{codeclasse}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> printTimetablesC(@PathVariable int codeclasse) throws Exception {
        Classe classe = classeRepository.findOne(codeclasse);
        String reportfile = "classpath:com/tsoft/appli/highschool/timetable/service/EDTClasse.jasper";
        //remplissage des parametres du report
        Map params = new HashMap();
        params.put("classe", classe.getLibelle());
        params.put("codeclasse", classe.getCode());

        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("codeannee", as.getAnneeCourante().getCode());

        //information about school
        if (ecoleRepository.findAll().isEmpty()) {
            throw new com.tsoft.exception.BusinessException("Paramètres de l'ecole non definis");
        }
        Ecole ecole = ecoleRepository.findAll().get(0);
        params.put("nom_ecole", ecole.getNom());
        params.put("address_ecole", ecole.getAdresse());
        params.put("tel_ecole", ecole.getTelephone());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTClasse"
                + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                , params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return download(destfile);
    }

    @RequestMapping(value = "/printTimetableP/{prof}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> printTimetablesP(@PathVariable int prof) throws Exception {
        Professeur professeur = professeurRepository.findOne(prof);
        String reportfile = "classpath:com/tsoft/appli/highschool/timetable/service/EDTProf.jasper";
        //remplissage des parametres du report
        Map params = new HashMap();
        params.put("codeprof", professeur.getCode());
        params.put("nomprof", professeur.getNomprenom());
        params.put("matricule", professeur.getMatricule());
        params.put("civilite", professeur.getCivilite());

        params.put("annee", as.getAnneeCourante().getLibelle());
        params.put("codeannee", as.getAnneeCourante().getCode());

        //information about school
         if (ecoleRepository.findAll().isEmpty()) {
            throw new com.tsoft.exception.BusinessException("Paramètres de l'ecole non definis");
        }
        Ecole ecole = ecoleRepository.findAll().get(0);
        params.put("nom_ecole", ecole.getNom());
        params.put("address_ecole", ecole.getAdresse());
        params.put("tel_ecole", ecole.getTelephone());

        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        String destfile = uploadedfile.getAbsolutePath() + File.separator + "EDTProf"
                + ".pdf";
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream() //file jasper
                , params, //params report
                dataSource.getConnection());  //datasource

        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return download(destfile);
    }

    @RequestMapping(value = "/getTimetableP/{prof}",
            method = RequestMethod.GET)
    public List getTimetablesP(@PathVariable int prof) throws Exception {
        String query = "select lundi.libCreneau,lundi.cours as lundi,mardi.cours as mardi,mercredi.cours as mercredi,jeudi.cours as jeudi,vendredi.cours as vendredi \n"
                + "\n"
                + "from\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT concat(date_format(heure_debut,'%H:%i'),concat('-',date_format(heure_fin,'%H:%i'))) as libCreneau,c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=1) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_professeur=" + prof + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as lundi join\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=2) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_professeur=" + prof + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as mardi  on (lundi.code_c=mardi.code_c)  join\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=3) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_professeur=" + prof + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as mercredi  on (lundi.code_c=mercredi.code_c)\n"
                + "\n"
                + "join\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=4) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_professeur=" + prof + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as jeudi  on (lundi.code_c=jeudi.code_c)\n"
                + "join\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=5) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_professeur=" + prof + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as vendredi  on (lundi.code_c=vendredi.code_c)\n"
                + "";
        return jt.queryForList(query);
    }

    @RequestMapping(value = "/getTimetableC/{classe}",
            method = RequestMethod.GET)
    public List getTimetablesC(@PathVariable int classe) throws Exception {
        String query = "select lundi.libCreneau,lundi.cours as lundi,mardi.cours as mardi,mercredi.cours as mercredi,jeudi.cours as jeudi,vendredi.cours as vendredi \n"
                + "\n"
                + "from\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT concat(date_format(heure_debut,'%H:%i'),concat('-',date_format(heure_fin,'%H:%i'))) as libCreneau,c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=1) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_classe=" + classe + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as lundi join\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=2) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_classe=" + classe + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as mardi  on (lundi.code_c=mardi.code_c)  join\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=3) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_classe=" + classe + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as mercredi  on (lundi.code_c=mercredi.code_c)\n"
                + "\n"
                + "join\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=4) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_classe=" + classe + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as jeudi  on (lundi.code_c=jeudi.code_c)\n"
                + "join\n"
                + "\n"
                + "(select plage.*,edt.cours  from (SELECT c.code as code_c,j.code as code_j,j.libelle as jour FROM Creneau c ,Jour j  where j.code=5) as plage\n"
                + "left join \n"
                + "(SELECT t.code_creneau,t.code_jour,c.libelle as cours FROM  Timetable t join Cours c on (t.code_cours=c.code) where c.code_classe=" + classe + ") as edt\n"
                + "on (edt.code_creneau=plage.code_c and edt.code_jour=plage.code_j)) as vendredi  on (lundi.code_c=vendredi.code_c)\n"
                + "";
        return jt.queryForList(query);
    }

    @Override
    public String form() throws Exception {
        return "tpl/highschool/edt.html";
    }

    @Override
    public Class rubrique() throws Exception {
       return  Timetable.class;
    }

}
