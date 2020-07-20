package bsc.kononov.disksharingwebapp.domain.reference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Справочное значение
 */
@Entity
@Table(name = "reference_values")
public class ReferenceValue implements Serializable {

    private static final long serialVersionUID = -1429393047564310473L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ref_id", nullable = false)
    private Reference reference; // Категория в справочнике

    @Column(name = "value", unique = true, nullable = false)
    private String value; // Значение

    protected ReferenceValue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferenceValue)) return false;
        ReferenceValue that = (ReferenceValue) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(reference, that.reference) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, reference, value);
    }
}
