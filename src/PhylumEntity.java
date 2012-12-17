import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amoore
 * Date: 29/11/12
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * PhylumEntity
 * Builds a PhylumEntity from the data in the REEFMON database in a format that can be turned into a TaxonEntity.
 * Only links to the ClazzEntity below in the Taxon structure.  This is the highest level in the REEFMON database for
 * individual taxon levels.
 */
@Entity
@Table (name = "CREEFS_PHYLUM")
public class PhylumEntity implements Serializable{
    private String phylumId;

    @Id
    @Column(name = "PHYLUM_ID")
    public String getPhylumId() {
        return phylumId;
    }

    public void setPhylumId(String phylumId) {
        this.phylumId = phylumId;
    }

    private String phylum;

    @Basic
    @Column(name = "PHYLUM")
    public String getPhylum() {
        return phylum;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum;
    }

    private List<ClazzEntity> clazzByPhylumID;

    @OneToMany(mappedBy = "phylumByPhylumID", fetch=FetchType.EAGER)
    public List<ClazzEntity> getClazzByPhylumID() {
        return clazzByPhylumID;
    }

    public void setClazzByPhylumID(List<ClazzEntity> clazzByPhylumID) {
        this.clazzByPhylumID = clazzByPhylumID;
    }

    public int compareTo(Object o) {
        try {
            PhylumEntity s= (PhylumEntity) o;
            int out = this.getPhylumId().compareTo(s.getPhylumId());
            if (out == 0) {
                out = this.getPhylum().compareTo(s.getPhylum());
            }
            return out;
        } catch (Exception e) {
            return -1;
        }

    }
}
