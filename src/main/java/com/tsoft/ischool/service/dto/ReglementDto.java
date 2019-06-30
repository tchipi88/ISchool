package com.tsoft.ischool.service.dto;

import com.tsoft.ischool.domain.Caisse;
import com.tsoft.ischool.domain.Eleve;
import com.tsoft.ischool.domain.Reglement;
import com.tsoft.ischool.domain.enumeration.CaisseMouvementMotif;
import com.tsoft.ischool.domain.enumeration.ModePaiement;
import com.tsoft.ischool.service.template.Fichier;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReglementDto {

    private Long id;
    @NotNull
    private Eleve eleve;
    @NotNull
    private Caisse caisse;
    @NotNull
    private LocalDate dateVersement;
    @NotNull
    private BigDecimal montant;
    @NotNull
    private ModePaiement modePaiement;
    @NotNull
    private CaisseMouvementMotif motif;
    private MultipartFile fileReference;

    public ReglementDto() {}

    public ReglementDto(Reglement reglement) {
        this.id = reglement.getId();
        this.eleve = reglement.getEleve();
        this.caisse = reglement.getCaisse();
        this.dateVersement = reglement.getDateVersement();
        this.montant = reglement.getMontant();
//        this.fileReference = MultipartFile(reglement.getFileReference());
        this.modePaiement = reglement.getModePaiement();
        this.motif = reglement.getMotif();
    }

    public ReglementDto(Long id, Eleve eleve, Caisse caisse, LocalDate dateVersement, BigDecimal montant, MultipartFile fileReference,
                        ModePaiement modePaiement, CaisseMouvementMotif motif) {
        this.id = id;
        this.eleve = eleve;
        this.caisse = caisse;
        this.dateVersement = dateVersement;
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.motif = motif;
        this.fileReference = fileReference;
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

    public MultipartFile getFileReference() {
        return fileReference;
    }

    public void setFileReference(MultipartFile fileReference) {
        this.fileReference = fileReference;
    }
}
