import javax.persistence.*;
import java.io.Serializable;

@Entity
//@NamedQuery(name = "ReefGeolocale.findAll",
//    query = "select o from ReefGeolocale o")
@Table(name = "REEF_GEOLOCALE")
public class ReefGeolocale implements Serializable, Comparable {
    @Column(name="AA_SECTION")
    private String aaSection;
    private String comments;
    @Id
    @Column(name="FULLREEF_ID", nullable = false)
    private String fullreefId;
    @Column(name="GAZ_NAME")
    private String gazName;
    @Column(name="G_SECTION")
    private String gSection;
    @Column(name="INSHORE_MON_REEF_NAME")
    private String inshoreMonReefName;
    private String lastmodified;
    @Column(name="LAT_DEG")
    private Long latDeg;
    @Column(name="LAT_FACTOR")
    private Double latFactor;
    @Column(name="LAT_MIN")
    private Double latMin;
    @Column(name="LONG_DEG")
    private Long longDeg;
    @Column(name="LONG_MIN")
    private Double longMin;
    @Column(name="REEF_ID")
    private Long reefId;
    @Column(name="REEF_NAME")
    private String reefName;
    @Column(name="REEF_SUB_ID")
    private String reefSubId;
    private String shelf;

    public ReefGeolocale() {
    }

    public String getAaSection() {
        return aaSection;
    }

    public void setAaSection(String aaSection) {
        this.aaSection = aaSection;
    }



    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getFullreefId() {
        return fullreefId;
    }

    public void setFullreefId(String fullreefId) {
        this.fullreefId = fullreefId;
    }

    public String getGazName() {
        return gazName;
    }

    public void setGazName(String gazName) {
        this.gazName = gazName;
    }

    public String getGSection() {
        return gSection;
    }

    public void setGSection(String gSection) {
        this.gSection = gSection;
    }

    public String getInshoreMonReefName() {
        return inshoreMonReefName;
    }

    public void setInshoreMonReefName(String inshoreMonReefName) {
        this.inshoreMonReefName = inshoreMonReefName;
    }

    public String getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(String lastmodified) {
        this.lastmodified = lastmodified;
    }

    public Long getLatDeg() {
        return latDeg;
    }

    public void setLatDeg(Long latDeg) {
        this.latDeg = latDeg;
    }

    public Double getLatFactor() {
        return latFactor;
    }

    public void setLatFactor(Double latFactor) {
        this.latFactor = latFactor;
    }

    public Double getLatMin() {
        return latMin;
    }

    public void setLatMin(Double latMin) {
        this.latMin = latMin;
    }

    public Long getLongDeg() {
        return longDeg;
    }

    public void setLongDeg(Long longDeg) {
        this.longDeg = longDeg;
    }

    public Double getLongMin() {
        return longMin;
    }

    public void setLongMin(Double longMin) {
        this.longMin = longMin;
    }




    public Long getReefId() {
        return reefId;
    }

    public void setReefId(Long reefId) {
        this.reefId = reefId;
    }

    public String getReefName() {
        return reefName;
    }

    public void setReefName(String reefName) {
        this.reefName = reefName;
    }

    public String getReefSubId() {
        return reefSubId;
    }

    public void setReefSubId(String reefSubId) {
        this.reefSubId = reefSubId;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

//    private String oldColour;
//
//    @Basic
//    @Column(name = "OLD_COLOUR", length = 100)
//    public String getOldColour() {
//        return oldColour;
//    }
//
//    public void setOldColour(String oldColour) {
//        this.oldColour = oldColour;
//    }
//
//    private String oldReefName;
//
//    @Basic
//    @Column(name = "OLD_REEF_NAME", length = 30)
//    public String getOldReefName() {
//        return oldReefName;
//    }
//
//    public void setOldReefName(String oldReefName) {
//        this.oldReefName = oldReefName;
//    }
//
//    private String nrmRegion;
//
//    @Basic
//    @Column(name = "NRM_REGION", length = 50)
//    public String getNrmRegion() {
//        return nrmRegion;
//    }
//
//    public void setNrmRegion(String nrmRegion) {
//        this.nrmRegion = nrmRegion;
//    }
//
//    private String catchment;
//
//    @Basic
//    @Column(name = "CATCHMENT", length = 50)
//    public String getCatchment() {
//        return catchment;
//    }
//
//    public void setCatchment(String catchment) {
//        this.catchment = catchment;
//    }
//
//    private String newZone2004;
//
//    @Basic
//    @Column(name = "NEW_ZONE_2004", length = 50)
//    public String getNewZone2004() {
//        return newZone2004;
//    }
//
//    public void setNewZone2004(String newZone2004) {
//        this.newZone2004 = newZone2004;
//    }
//
//
//

    @ManyToOne
    @JoinColumn(name = "A_SECTOR", referencedColumnName = "A_SECTOR")
    private RmSectorEntity rmSectorByASector;
    public RmSectorEntity getRmSectorByASector() {
        return rmSectorByASector;
    }

    public void setRmSectorByASector(RmSectorEntity rmSectorByASector) {
        this.rmSectorByASector = rmSectorByASector;
    }


    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof ReefGeolocale)) {
            return -1;
        }
        ReefGeolocale r = (ReefGeolocale) o;
        return this.getReefName().compareTo(r.getReefName());
    }
}
