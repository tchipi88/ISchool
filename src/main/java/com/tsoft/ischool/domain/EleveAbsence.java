/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipnangngansopa
 */
@Entity
public class EleveAbsence extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private ClasseEleve classeEleve;
    @Min(1)
    @Max(6)
    @NotNull
    private Integer numeroSequence;
    
    @NotNull
    private Integer nombreHeures;
    
    private Integer nombreHeuresJustifiees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EleveAbsence)) {
            return false;
        }
        EleveAbsence other = (EleveAbsence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tsoft.ischool.domain.Absence[ id=" + id + " ]";
    }

    public ClasseEleve getClasseEleve() {
        return classeEleve;
    }

    public void setClasseEleve(ClasseEleve classeEleve) {
        this.classeEleve = classeEleve;
    }

    public Integer getNumeroSequence() {
        return numeroSequence;
    }

    public void setNumeroSequence(Integer numeroSequence) {
        this.numeroSequence = numeroSequence;
    }

    public Integer getNombreHeures() {
        return nombreHeures;
    }

    public void setNombreHeures(Integer nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    public Integer getNombreHeuresJustifiees() {
        return nombreHeuresJustifiees;
    }

    public void setNombreHeuresJustifiees(Integer nombreHeuresJustifiees) {
        this.nombreHeuresJustifiees = nombreHeuresJustifiees;
    }

    
}
