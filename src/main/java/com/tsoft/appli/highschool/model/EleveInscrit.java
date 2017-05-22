/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsoft.utils.annotations.Libelle;
import com.tsoft.utils.annotations.Select;
import com.tsoft.utils.annotations.SpelValue;
import com.tsoft.domain.SimpleLifeCycleEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SqlFragmentAlias;

/**
 *
 * @author tchipi
 */
@Entity
@FilterDef(name = "isok")
@Filter(name = "isok", condition = "{t}.cycle_vie like 'ACTIF'", deduceAliasInjectionPoints = false,
        aliases = {
            @SqlFragmentAlias(alias = "t", table = "EleveInscrit")})
public class EleveInscrit extends SimpleLifeCycleEntity {

   
    @JoinColumn(name = "code_classe", referencedColumnName = "code")
    @ManyToOne
    @Select
    protected Classe classe;
    @JoinColumn(name = "code_eleve", referencedColumnName = "code")
    @ManyToOne
    @Libelle
    protected Eleve eleve;
    @JoinColumn(name = "code_annee", referencedColumnName = "code")
    @ManyToOne
    @Select
    protected AnneeScolaire annee;

    @Column
    private boolean redoublant;
    @Column(name = "statut")
    private boolean nouvel_eleve;

    @OneToMany(mappedBy = "eleveinscrit")
    @JsonIgnore
    protected List<Notes> listnotes = new ArrayList<>();

    @OneToMany(mappedBy = "eleveinscrit")
    @JsonIgnore
    protected List<Reglement> listreglements = new ArrayList<>();

   

    
    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public AnneeScolaire getAnnee() {
        return annee;
    }

    public void setAnnee(AnneeScolaire annee) {
        this.annee = annee;
    }

   

    public boolean isRedoublant() {
        return redoublant;
    }

    public void setRedoublant(boolean redoublant) {
        this.redoublant = redoublant;
    }

    public List<Notes> getListnotes() {
        return listnotes;
    }

    public void setListnotes(List<Notes> listnotes) {
        this.listnotes = listnotes;
    }

    public List<Reglement> getListreglements() {
        return listreglements;
    }

    public void setListreglements(List<Reglement> listreglements) {
        this.listreglements = listreglements;
    }

    

    public boolean isNouvel_eleve() {
        return nouvel_eleve;
    }

    public void setNouvel_eleve(boolean nouvel_eleve) {
        this.nouvel_eleve = nouvel_eleve;
    }

    
}
