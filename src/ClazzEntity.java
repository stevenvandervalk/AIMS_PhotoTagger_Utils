import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amoore
 * Date: 29/11/12
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table (name = "CREEFS_CLAZZ")
public class ClazzEntity implements Serializable{
    private String classId;

    @Id
    @Column(name = "CLASS_ID")
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    private String clazz;

    @Basic
    @Column(name = "CLASS")
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    private PhylumEntity phylumByPhylumID;


    @ManyToOne
    @JoinColumn(name = "PHYLUM_ID", referencedColumnName = "PHYLUM_ID")
    public PhylumEntity getPhylumByPhylumID() {
        return phylumByPhylumID;
    }

    public void setPhylumByPhylumID(PhylumEntity phylumByPhylumID) {
        this.phylumByPhylumID = phylumByPhylumID;
    }


    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof ClazzEntity)) {
            return -1;
        }
        ClazzEntity r = (ClazzEntity) o;
        return this.getClazz().compareTo(r.getClazz());
    }

    private List<OrdersEntity> ordersByClazzID;

    @OneToMany(mappedBy = "clazzByClazzID", fetch=FetchType.LAZY)
    public List<OrdersEntity> getOrdersByClazzID() {
        return ordersByClazzID;
    }

    public void setOrdersByClazzID(List<OrdersEntity> ordersByClazzID) {
        this.ordersByClazzID = ordersByClazzID;
    }
}
