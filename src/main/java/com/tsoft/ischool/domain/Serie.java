/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.ischool.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsoft.ischool.domain.enumeration.Cycle;
import com.tsoft.ischool.domain.enumeration.Section;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class Serie extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Min(1)
    private Integer niveau;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Section section;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Cycle cycle;

    @Formula("(select count(c.id) from classe c join serie s on (s.id=c.serie_id) where s.id=id)")
    private Integer nombreClasses;

    @Transient
    @JsonProperty
    private Integer nombreClassesToAdd = 0;

    @Column
    @Lob
    private String description;

    public Serie() {
    }

    public Integer getNombreClassesToAdd() {
        return nombreClassesToAdd;
    }

    public void setNombreClassesToAdd(Integer nombreClassesToAdd) {
        this.nombreClassesToAdd = nombreClassesToAdd;
    }

    public Integer getNombreClasses() {
        return nombreClasses;
    }

    public void setNombreClasses(Integer nombreClasses) {
        this.nombreClasses = nombreClasses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

}
