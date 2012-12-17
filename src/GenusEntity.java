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
 * GenusEntity
 * Builds a GenusEntity from the data in the REEFMON database in a format that can be turned into a TaxonEntity.
 * The GenusEntity will link to the FamilyEntity above it and the SpeciesEntity below it in the Taxon structure via IDs.
 */
@Entity
@Table (name = "CREEFS_GENUS")
public class GenusEntity implements Serializable{
    private String genusId;

    @Id
    @Column(name = "GENUS_ID")
    public String getGenusId() {
        return genusId;
    }

    public void setGenusId(String genusId) {
        this.genusId = genusId;
    }

    private String genus;

    @Basic
    @Column(name = "GENUS")
    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    private FamilyEntity familyByFamilyID;


    @ManyToOne
    @JoinColumn(name = "FAMILY_ID", referencedColumnName = "FAMILY_ID")
    public FamilyEntity getFamilyByFamilyID() {
        return familyByFamilyID;
    }

    public void setFamilyByFamilyID(FamilyEntity familyByFamilyID) {
        this.familyByFamilyID = familyByFamilyID;
    }


    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof GenusEntity)) {
            return -1;
        }
        GenusEntity r = (GenusEntity) o;
        return this.getGenus().compareTo(r.getGenus());
    }

    private List<SpeciesEntity> speciesByGenusID;

    @OneToMany(mappedBy = "genusByGenusID", fetch=FetchType.LAZY)
    public List<SpeciesEntity> getSpeciesByGenusID() {
        return speciesByGenusID;
    }

    public void setSpeciesByGenusID(List<SpeciesEntity> speciesByGenusID) {
        this.speciesByGenusID = speciesByGenusID;
    }
}
