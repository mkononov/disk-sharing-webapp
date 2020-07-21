package bsc.kononov.disksharingwebapp.domain.item;

import bsc.kononov.disksharingwebapp.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Диск
 */
@Entity
@Table(name = "disks")
public class Disk implements Serializable {

    private static final long serialVersionUID = -2525652674404751233L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private User owner; // Владелец

    @OneToOne
    @JoinColumn(name = "holder_id")
    @JsonIgnore
    private User holder; // Держатель (тот, кто взял)

    @Column(name = "description")
    private String description; // Описание

    protected Disk() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Disk)) return false;
        Disk disk = (Disk) o;
        return Objects.equals(id, disk.id) &&
                Objects.equals(owner, disk.owner) &&
                Objects.equals(holder, disk.holder) &&
                Objects.equals(description, disk.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, owner, holder, description);
    }

    @Override
    public String toString() {
        return "Disk{" +
                "id=" + id +
                ", owner=" + owner +
                ", holder=" + holder +
                '}';
    }
}
