package com.sapient.personnel.mgmt.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="EMPLOYEE")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee implements Serializable {

    @Id
    @Column(name="EMPLOYEE_ID", nullable = false)
    private Long id;

    @NotNull
    @Column(name="NAME", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="TITLE", nullable = false)
    private Title title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="BUSINESS_UNIT", nullable = false)
    private BusinessUnit businessUnit;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="PLACE", nullable = false)
    private Place place;

    @NotNull
    @Column(name="SALARY", nullable = false)
    private BigDecimal salary;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="COMPETENCY", nullable = false)
    private Competency competency;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="SUPERVISOR_ID")
    @JsonIdentityReference(alwaysAsId = true)
    private Employee supervisor;

    @JsonIgnore
    @OneToMany(mappedBy= "supervisor")
    private Set<Employee> supervisees = new HashSet<>();

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title=" + title +
                ", businessUnit=" + businessUnit +
                ", place=" + place +
                ", salary=" + salary +
                ", competency=" + competency +
                ", supervisor=" + supervisor +
                '}';
    }
}
