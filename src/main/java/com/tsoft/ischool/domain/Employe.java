package com.tsoft.ischool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Employe.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employe")
public class Employe extends Personne {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_entree")
    private LocalDate dateEntree;

    @Column(name = "salaire", precision = 10, scale = 2)
    private BigDecimal salaire;

//    @Column
//    private Long idPerson;
    @ManyToOne
    private Person person;

    @ManyToOne
    private EmployeFonction fonction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }

    public EmployeFonction getFonction() {
        return fonction;
    }

    public void setFonction(EmployeFonction fonction) {
        this.fonction = fonction;
    }

//    public Long getIdPerson() {
//        return idPerson;
//    }
//
//    public void setIdPerson(Long idPerson) {
//        this.idPerson = idPerson;
//    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employe employe = (Employe) o;
        if (employe.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employe{"
                + "id=" + id
                + ", dateEntree='" + dateEntree + "'"
                + ", salaire='" + salaire + "'"
                + '}';
    }
}
