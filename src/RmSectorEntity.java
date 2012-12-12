import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gcoleman
 * Date: 11/08/2008
 * Time: 13:07:41
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "RM_SECTOR")
public class RmSectorEntity implements Serializable, Comparable {

    private String asector;

    @Id
    @Column(name = "A_SECTOR", nullable = false, length = 2)
    public String getAsector() {
        return asector;
    }

    public void setAsector(String aSector) {
        this.asector = aSector;
    }

    private String sectorName;

    @Basic
    @Column(name = "SECTOR_NAME", length = 30)
    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    private String desc1;

    @Basic
    @Column(name = "DESC1", length = 80)
    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    private String desc2;

    @Basic
    @Column(name = "DESC2", length = 80)
    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    private String desc3;

    @Basic
    @Column(name = "DESC3", length = 80)
    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    private String desc4;

    @Basic
    @Column(name = "DESC4", length = 80)
    public String getDesc4() {
        return desc4;
    }

    public void setDesc4(String desc4) {
        this.desc4 = desc4;
    }

    private String desc5;

    @Basic
    @Column(name = "DESC5", length = 80)
    public String getDesc5() {
        return desc5;
    }

    public void setDesc5(String desc5) {
        this.desc5 = desc5;
    }

    private String desc6;

    @Basic
    @Column(name = "DESC6", length = 80)
    public String getDesc6() {
        return desc6;
    }

    public void setDesc6(String desc6) {
        this.desc6 = desc6;
    }

    private String desc7;

    @Basic
    @Column(name = "DESC7", length = 80)
    public String getDesc7() {
        return desc7;
    }

    public void setDesc7(String desc7) {
        this.desc7 = desc7;
    }

    private String desc8;

    @Basic
    @Column(name = "DESC8", length = 80)
    public String getDesc8() {
        return desc8;
    }

    public void setDesc8(String desc8) {
        this.desc8 = desc8;
    }

    private Integer ns;

    @Basic
    @Column(name = "NS", length = 2)
    public Integer getNs() {
        return ns;
    }

    public void setNs(Integer ns) {
        this.ns = ns;
    }

    private String location;

    @Basic
    @Column(name = "LOCATION", length = 30)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private Integer sortOrder;

    @Basic
    @Column(name = "SORT_ORDER", length = 3)
    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

//    private String geography;

//    @Basic
//    @Column(name = "GEOGRAPHY", length = 0)
//    public String getGeography() {
//        return geography;
//    }
//
//    public void setGeography(String geography) {
//        this.geography = geography;
//    }

    private List<ReefGeolocale> reefGeolocalesByASector;

    @OneToMany(mappedBy = "rmSectorByASector", fetch=FetchType.LAZY)
//    @OneToMany(fetch=FetchType.LAZY)
//    @JoinColumn(name = "A_SECTOR", referencedColumnName = "A_SECTOR")
    public List<ReefGeolocale> getReefGeolocalesByASector() {
        return reefGeolocalesByASector;
    }

    public void setReefGeolocalesByASector(List<ReefGeolocale> reefGeolocalesByASector) {
        this.reefGeolocalesByASector = reefGeolocalesByASector;
    }

    public int compareTo(Object o) {
        try {
            RmSectorEntity s= (RmSectorEntity) o;
            int out = this.getLocation().compareTo(s.getLocation());
            if (out == 0) {
                out = this.getSectorName().compareTo(s.getSectorName());
            }
            return out;
        } catch (Exception e) {
            return -1;
        }

    }
}
