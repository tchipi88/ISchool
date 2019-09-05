/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import com.tsoft.ischool.domain.enumeration.CaisseMouvementMotif;
import com.tsoft.ischool.domain.enumeration.ModePaiement;
import com.tsoft.ischool.service.template.Fichier;
import com.tsoft.ischool.service.template.Image;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class Reglement extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne
    private Eleve eleve;

    @NotNull
    @ManyToOne
    private Caisse caisse;

    @NotNull
    private LocalDate dateVersement;

    @Column
    @NotNull
    private BigDecimal montant;

    @Column
    private String reference;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ModePaiement modePaiement;
    @Enumerated(EnumType.STRING)
    @NotNull
    private CaisseMouvementMotif motif;

    @ManyToOne
    private Classe classe;
    @Lob
    @Column(name = "image")
    @Image
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "print_number")
    private int printNumber=0;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reglement other = (Reglement) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    

   

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public LocalDate getDateVersement() {
        return dateVersement;
    }

    public void setDateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public CaisseMouvementMotif getMotif() {
        return motif;
    }

    public void setMotif(CaisseMouvementMotif motif) {
        this.motif = motif;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public int getPrintNumber() {
        return printNumber;
    }

    public void setPrintNumber(int printNumber) {
        this.printNumber = printNumber;
    }
}
