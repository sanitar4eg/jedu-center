package edu.netcracker.center.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.netcracker.center.domain.enumeration.TypeEnumeration;

/**
 * A GroupOfStudent.
 */
@Entity
@Table(name = "group_of_student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GroupOfStudent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeEnumeration type;
    
    @Column(name = "description")
    private String description;
    
    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @OneToMany(mappedBy = "groupOfStudent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Student> students = new HashSet<>();

    @OneToOne
    private TimeTable timeTable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public TypeEnumeration getType() {
        return type;
    }
    
    public void setType(TypeEnumeration type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupOfStudent groupOfStudent = (GroupOfStudent) o;
        if(groupOfStudent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, groupOfStudent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GroupOfStudent{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", description='" + description + "'" +
            ", isActive='" + isActive + "'" +
            '}';
    }
}
