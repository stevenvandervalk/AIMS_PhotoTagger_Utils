import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: amoore
 * Date: 29/11/12
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * SpeciesEntity
 * Builds a SpeciesEntity from the data in the REEFMON database in a format that can be turned into a TaxonEntity.
 * Only links to the GenusEntity above in the Taxon structure.  This is the lowest level in the REEFMON database for
 * individual taxon levels.
 */
@Entity
@Table (name = "CREEFS_SPECIES")
public class SpeciesEntity implements Serializable{
    private String speciesId;

    @Id
    @Column(name = "SPECIES_ID")
    public String getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesId;
    }

    private String species;

    @Basic
    @Column(name = "SPECIES")
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    private GenusEntity genusByGenusID;


    @ManyToOne
    @JoinColumn(name = "GENUS_ID", referencedColumnName = "GENUS_ID")
    public GenusEntity getGenusByGenusID() {
        return genusByGenusID;
    }

    public void setGenusByGenusID(GenusEntity genusByGenusID) {
        this.genusByGenusID = genusByGenusID;
    }


    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof SpeciesEntity)) {
            return -1;
        }
        SpeciesEntity r = (SpeciesEntity) o;
        return this.getSpecies().compareTo(r.getSpecies());
    }
}
