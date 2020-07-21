package bsc.kononov.disksharingwebapp.domain.reference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Справочник
 */
@Entity
@Table(name = "reference")
public class Reference implements Serializable {

    private static final long serialVersionUID = -7176960470214065182L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name; // Название категории (группы значений)

    @OneToMany(mappedBy = "reference", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReferenceValue> referenceValues; // Значения

    protected Reference() {
    }

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

    public List<ReferenceValue> getReferenceValues() {
        return referenceValues;
    }

    public void setReferenceValues(List<ReferenceValue> referenceValues) {
        this.referenceValues = referenceValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reference)) return false;
        Reference reference = (Reference) o;
        return Objects.equals(id, reference.id) &&
                Objects.equals(name, reference.name) &&
                Objects.equals(referenceValues, reference.referenceValues);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, referenceValues);
    }

    @Override
    public String toString() {
        return "Reference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
