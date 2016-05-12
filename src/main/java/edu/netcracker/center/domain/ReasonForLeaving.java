package edu.netcracker.center.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.netcracker.center.domain.enumeration.TypeOfReason;

/**
 * A ReasonForLeaving.
 */
@Entity
@Table(name = "reason_for_leaving")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReasonForLeaving implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeOfReason type;
    
    @Column(name = "description")
    private String description;
    
    @OneToOne
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfReason getType() {
        return type;
    }
    
    public void setType(TypeOfReason type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReasonForLeaving reasonForLeaving = (ReasonForLeaving) o;
        if(reasonForLeaving.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reasonForLeaving.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReasonForLeaving{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
