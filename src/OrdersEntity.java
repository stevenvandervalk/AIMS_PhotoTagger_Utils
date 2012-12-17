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

/**
 * OrdersEntity
 * Builds an OrdersEntity from the data in the REEFMON database in a format that can be turned into a TaxonEntity.
 * The OrdersEntity will link to the ClazzEntity above it and the FamilyEntity below it in the Taxon structure via IDs.
 */
@Entity
@Table (name = "CREEFS_ORDERS")
public class OrdersEntity implements Serializable{
    private String OrdersId;

    @Id
    @Column(name = "ORDERS_ID")
    public String getOrdersId() {
        return OrdersId;
    }

    public void setOrdersId(String ordersId) {
        this.OrdersId = ordersId;
    }

    private String orders;

    @Basic
    @Column(name = "ORDERS")
    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    private ClazzEntity clazzByClazzID;


    @ManyToOne
    @JoinColumn(name = "CLASS_ID", referencedColumnName = "CLASS_ID")
    public ClazzEntity getClazzByClazzID() {
        return clazzByClazzID;
    }

    public void setClazzByClazzID(ClazzEntity clazzByClazzID) {
        this.clazzByClazzID = clazzByClazzID;
    }


    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof OrdersEntity)) {
            return -1;
        }
        OrdersEntity r = (OrdersEntity) o;
        return this.getOrders().compareTo(r.getOrders());
    }

    private List<FamilyEntity> familyByOrdersID;

    @OneToMany(mappedBy = "ordersByOrdersID", fetch=FetchType.LAZY)
    public List<FamilyEntity> getfamilyByOrdersID() {
        return familyByOrdersID;
    }

    public void setfamilyByOrdersID(List<FamilyEntity> familyByOrdersID) {
        this.familyByOrdersID = familyByOrdersID;
    }
}
