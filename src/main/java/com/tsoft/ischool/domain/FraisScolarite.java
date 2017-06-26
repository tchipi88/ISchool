/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipnangngansopa
 */
@Entity
public class FraisScolarite extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Serie serie;

    @ManyToOne
    @NotNull
    private Annee annee;

    @NotNull
    private BigDecimal fraisExigible = BigDecimal.ZERO;

    @NotNull
    private BigDecimal ape = BigDecimal.ZERO;

    private BigDecimal fraisExamen = BigDecimal.ZERO;

    private BigDecimal autresFrais = BigDecimal.ZERO;
    
    @Lob
    private String descriptionAutreFrais;

    @Formula("frais_exigible+ape+frais_examen+autres_frais")
    private BigDecimal totalScolarite;

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
        if (!(object instanceof FraisScolarite)) {
            return false;
        }
        FraisScolarite other = (FraisScolarite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tsoft.ischool.domain.FraisScolarite[ id=" + id + " ]";
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public BigDecimal getFraisExigible() {
        return fraisExigible;
    }

    public void setFraisExigible(BigDecimal fraisExigible) {
        this.fraisExigible = fraisExigible;
    }

   

    public BigDecimal getApe() {
        return ape;
    }

    public void setApe(BigDecimal ape) {
        this.ape = ape;
    }

    public BigDecimal getFraisExamen() {
        return fraisExamen;
    }

    public void setFraisExamen(BigDecimal fraisExamen) {
        this.fraisExamen = fraisExamen;
    }

    public BigDecimal getAutresFrais() {
        return autresFrais;
    }

    public void setAutresFrais(BigDecimal autresFrais) {
        this.autresFrais = autresFrais;
    }

    public BigDecimal getTotalScolarite() {
        return totalScolarite;
    }

    public void setTotalScolarite(BigDecimal totalScolarite) {
        this.totalScolarite = totalScolarite;
    }

    public String getDescriptionAutreFrais() {
        return descriptionAutreFrais;
    }

    public void setDescriptionAutreFrais(String descriptionAutreFrais) {
        this.descriptionAutreFrais = descriptionAutreFrais;
    }

    
}
