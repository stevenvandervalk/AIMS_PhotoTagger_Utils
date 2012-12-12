package aims.app.reefmonitoring.ejb3;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: gcoleman
 * Date: 28/02/2008
 * Time: 11:09:06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema = "REEFMON", name = "ALL_SPECIES")
//@IdClass(aims.app.reefmonitoring.ejb3.AllSpecyEntityPK.class)
public class AllSpecyEntity implements Serializable {
    private String species;

//    @Id
    @Column(name = "SPECIES", nullable = false, length = 50)
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    private String genus;

//    @Id
    @Column(name = "GENUS", nullable = false, length = 50, updatable=false, insertable=false)
    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    private String commonNames;

    @Basic
    @Column(name = "COMMON_NAMES")
    public String getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(String commonNames) {
        this.commonNames = commonNames;
    }

    private BigInteger speciesSid;

    @Id
    @Column(name = "SPECIES_SID", length = 22)
    public BigInteger getSpeciesSid() {
        return speciesSid;
    }

    public void setSpeciesSid(BigInteger speciesSid) {
        this.speciesSid = speciesSid;
    }

    private TaxonEntity taxonByGenus;

    @ManyToOne
    @JoinColumn(name = "GENUS", referencedColumnName = "TAXA", nullable = false)
    public TaxonEntity getTaxonByGenus() {
        return taxonByGenus;
    }

    public void setTaxonByGenus(TaxonEntity taxonByGenus) {
        this.taxonByGenus = taxonByGenus;
    }
}
