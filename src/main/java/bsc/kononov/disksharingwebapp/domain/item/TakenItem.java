package bsc.kononov.disksharingwebapp.domain.item;

import bsc.kononov.disksharingwebapp.domain.user.User;
import bsc.kononov.disksharingwebapp.domain.reference.ReferenceValue;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * История перемещений предметов
 */
@Entity
@Table(name = "taken_items")
public class TakenItem implements Serializable {

    private static final long serialVersionUID = 74183711065142477L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "disk_id", nullable = false)
    private Disk disk; // Предмет

    @OneToOne
    @JoinColumn(name = "from_id", nullable = false)
    private User from; // От кого

    @ManyToOne
    @JoinColumn(name = "to_id", nullable = false)
    private User to; // Кому

    @OneToOne
    @JoinColumn(name = "op_type_id", nullable = false)
    private ReferenceValue opType; // Тип операции

    @Column(name = "op_date", nullable = false)
    private Date opDate; // Дата операции

    protected TakenItem() {
    }

    public TakenItem(Disk disk, User from, User to, ReferenceValue opType, Date opDate) {
        this.disk = disk;
        this.from = from;
        this.to = to;
        this.opType = opType;
        this.opDate = opDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public ReferenceValue getOpType() {
        return opType;
    }

    public void setOpType(ReferenceValue opType) {
        this.opType = opType;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TakenItem)) return false;
        TakenItem item = (TakenItem) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(disk, item.disk) &&
                Objects.equals(from, item.from) &&
                Objects.equals(to, item.to) &&
                Objects.equals(opType, item.opType) &&
                Objects.equals(opDate, item.opDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, disk, from, to, opType, opDate);
    }
}
