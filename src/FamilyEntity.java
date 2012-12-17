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
 * FamilyEntity
 * Builds a FamilyEntity from the data in the REEFMON database in a format that can be turned into a TaxonEntity.
 * The FamilyEntity will link to the OrdersEntity above it and the GenusEntity below it in the Taxon structure via IDs
 */
@Entity
@Table (name = "CREEFS_FAMILY")
public class FamilyEntity implements Serializable{
    private String familyId;

    @Id
    @Column(name = "FAMILY_ID")
    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    private String family;

    @Basic
    @Column(name = "FAMILY")
    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    private OrdersEntity ordersByOrdersID;


    @ManyToOne
    @JoinColumn(name = "ORDERS_ID", referencedColumnName = "ORDERS_ID")
    public OrdersEntity getOrdersByOrdersID() {
        return ordersByOrdersID;
    }

    public void setOrdersByOrdersID(OrdersEntity ordersByOrdersID) {
        this.ordersByOrdersID = ordersByOrdersID;
    }


    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof FamilyEntity)) {
            return -1;
        }
        FamilyEntity r = (FamilyEntity) o;
        return this.getFamily().compareTo(r.getFamily());
    }

    private List<GenusEntity> genusByFamilyID;

    @OneToMany(mappedBy = "familyByFamilyID", fetch=FetchType.LAZY)
    public List<GenusEntity> getGenusByFamilyID() {
        return genusByFamilyID;
    }

    public void setGenusByFamilyID(List<GenusEntity> genusByFamilyID) {
        this.genusByFamilyID = genusByFamilyID;
    }
}
