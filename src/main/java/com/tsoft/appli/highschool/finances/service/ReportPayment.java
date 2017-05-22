/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.finances.service;

import com.tsoft.appli.highschool.model.Encaissement;
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
public class ReportPayment extends EmptyCustomActionProcess {

    @Autowired
    AnneeService as;
    @Autowired
    JdbcTemplate jt;

    @RequestMapping(value = "/payment/{classe}",
            method = RequestMethod.GET)
    public List getDatas(@PathVariable int classe) throws Exception {

        String query = "select *,if  (restetranche2=0,\n" +
"                if(cumulverse-inscription-tranche1-tranche2-tranche3<0,cumulverse-inscription-tranche1-tranche2-tranche3,0),-tranche3) as restetranche3\n" +
"  from (\n" +
"select *,if (restetranche1=0,\n" +
"                if(cumulverse-inscription-tranche1-tranche2<0,cumulverse-inscription-tranche1-tranche2,\n" +
"                0),-tranche2) as restetranche2\n" +
"                                from(\n" +
"                select *,\n" +
"                if (resteinscription=0,\n" +
"                if(cumulverse-inscription-tranche1<0,cumulverse-inscription-tranche1,\n" +
"                0),-tranche1) as restetranche1\n" +
"                from(\n" +
"                select   *,\n" +
"                if (cumulverse<inscription,cumulverse-inscription,0) as resteinscription\n" +
"                from (\n" +
"                select (concat(e.code,concat('E',date_format(e.created_date,'%Y')))) as matricule,e.nom_prenom,\n" +
"                ifnull(sum(r.montant),0) as cumulverse,ei.statut,\n" +
"                (if(statut,inscription_new,inscription)) as inscription,\n" +
"                (if(statut,tranche1_new,tranche1)) as tranche1,\n" +
"                (if(statut,tranche2_new,tranche2)) as tranche2,\n" +
"                (if(statut,tranche3_new,tranche3)) as tranche3\n" +
"                from EleveInscrit ei join Eleve e on (ei.code_eleve=e.code and ei.code_classe="+classe+"  and ei.code_annee="+as.getAnneeCourante().getCode()+" )  \n" +
"                join Classe c on(c.code =ei.code_classe) join Serie s on (s.code=c.code_serie)\n" +
"                 left join Reglement r on (ei.code=r.code_eleveinscrit) group by ei.code\n" +
"                ) as result\n" +
"                )as result1\n" +
"                )as result2\n" +
"                )as result3";

        return jt.queryForList(query);
    }

    @Override
    public String libelle() {
        return "Etat des paiements";
    }

    @Override
    public String form() throws Exception {
        return "tpl/highschool/reportpayment.html";
    }

    @Override
    public Class rubrique() throws Exception {
        return  Encaissement.class;
    }

}
