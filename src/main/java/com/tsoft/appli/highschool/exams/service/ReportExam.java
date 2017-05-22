/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.exams.service;

import com.tsoft.appli.highschool.model.Notes;
import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.service.process.EmptyCustomActionProcess;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tchipi
 */
@RestController
@RequestMapping("/app")
public class ReportExam extends EmptyCustomActionProcess {
    
    @Autowired
    AnneeService  as;

    @Autowired
    JdbcTemplate jt;
    
    @RequestMapping(value = "/reportexam/{type}/{numtype}",
            method = RequestMethod.GET)
    public List retrievedata(@PathVariable String type, @PathVariable int numtype) throws Exception {
        String query = "";
        if (type.equals("seq")) {
            query = "select libelle,max(moyseq) as max,min(moyseq) as min,avg(moyseq) as avg,count(elevecode) as effectif,sum(moyennesup) as nbrsup, "
                    + "sum(moyennesup)/count(elevecode) as percent from (\n"
                    + "                   select e.code as elevecode,c.code as classecode,c.libelle,sum(coef.valeur) as "
                    + "totalcoefs,sum(note*coef.valeur) as totalpts,sum(note*coef.valeur)/sum(coef.valeur)"
                    + " as moyseq,if (sum(note*coef.valeur)/sum(coef.valeur)>10,1,0) as moyennesup\n"
                    + "from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join Classe c on "
                    + "(c.code_serie=coef.code_serie) join EleveInscrit ei on "
                    + "(ei.code_classe=c.code and ei.code_annee="+as.getAnneeCourante().getCode()+") join Eleve e "
                    + "on (ei.code_eleve=e.code)  left join Notes n on "
                    + "(n.code_eleveinscrit=ei.code and n.code_coefficient=coef.code  "
                    + "and (n.code_sequence = "+numtype+"))     "
                    + "  group by e.code ) as result  group by  classecode ;";
        } else {
            query
                    = "select libelle,max(moyseq) as max,min(moyseq) as min,avg(moyseq) as avg,count(elevecode) as effectif,sum(moyennesup) as nbrsup,sum(moyennesup)/count(elevecode)  as percent  from (\n"
                    + "                    select elevecode,classecode,libelle,sum(coefvaleur) as totalcoefs,sum(note*coefvaleur) as totalpts,sum(note*coefvaleur)/sum(coefvaleur) as moyseq,if (sum(note*coefvaleur)/sum(coefvaleur)>10,1,0) as moyennesup \n"
                    + "                     from (\n"
                    + "                    select e.code as elevecode,c.code as classecode,c.libelle,coef.code as coefcode,s.code as seqcode,ei.code as eicode,coef.valeur as coefvaleur\n"
                    + "                    from Coefficient coef join Matiere m on (m.code=coef.code_matiere) join Classe c on (c.code_serie=coef.code_serie) join EleveInscrit ei on (ei.code_classe=c.code and ei.code_annee="+as.getAnneeCourante().getCode()+") join Eleve e on (ei.code_eleve=e.code),Sequence s\n"
                    + "                    where  s.code in ("+numtype+"* 2 - 1,"+numtype+" * 2 ) ) as result1\n"
                    + "                     left join Notes n on (n.code_eleveinscrit=eicode and n.code_coefficient=coefcode  and (n.code_sequence=seqcode))\n"
                    + "                     group by elevecode\n"
                    + "                    ) as result group by classecode";

        }

        return jt.queryForList(query);
    }

    @Override
    public String libelle() {
        return "Statitisques Examens";
    }

    @Override
    public String form() throws Exception {
        return "tpl/highschool/reportexam.html";
    }

    @Override
    public Class rubrique() throws Exception {
        return  Notes.class;
    }

}
