package aims.app.reefmonitoring.ejb3;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: gcoleman
 * Date: 27/02/2008
 * Time: 11:27:51
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema = "REEFMON", name = "TAXON")
public class TaxonEntity implements Serializable {
    private String taxa;

    @Id
    @Column(name = "TAXA", nullable = false, length = 50)
    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    private String superTaxa;

    @Basic
    @Column(name = "SUPER_TAXA", length = 50, updatable=false, insertable=false)
    public String getSuperTaxa() {
        return superTaxa;
    }

    public void setSuperTaxa(String superTaxa) {
        this.superTaxa = superTaxa;
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

    private String taxaLevel;

    @Basic
    @Column(name = "TAXA_LEVEL", length = 20)
    public String getTaxaLevel() {
        return taxaLevel;
    }

    public void setTaxaLevel(String taxaLevel) {
        this.taxaLevel = taxaLevel;
    }

    private String aimsCode;

    @Basic
    @Column(name = "AIMS_CODE", length = 10)
    public String getAimsCode() {
        return aimsCode;
    }

    public void setAimsCode(String aimsCode) {
        this.aimsCode = aimsCode;
    }

    private TaxonEntity taxonBySuperTaxa;

    @ManyToOne
    @JoinColumn(name = "SUPER_TAXA", referencedColumnName = "TAXA")
    public TaxonEntity getTaxonBySuperTaxa() {
        return taxonBySuperTaxa;
    }

    public void setTaxonBySuperTaxa(TaxonEntity taxonBySuperTaxa) {
        this.taxonBySuperTaxa = taxonBySuperTaxa;
    }

    private Collection<TaxonEntity> taxonsByTaxa;

    @OneToMany(mappedBy = "taxonBySuperTaxa", fetch=FetchType.EAGER)
    public Collection<TaxonEntity> getTaxonsByTaxa() {
        return taxonsByTaxa;
    }

    public void setTaxonsByTaxa(Collection<TaxonEntity> taxonsByTaxa) {
        this.taxonsByTaxa = taxonsByTaxa;
    }

    private Collection<AllSpecyEntity> allSpeciesesByTaxa;

    @OneToMany(mappedBy = "taxonByGenus", fetch=FetchType.EAGER)
    public Collection<AllSpecyEntity> getAllSpeciesesByTaxa() {
        return allSpeciesesByTaxa;
    }

    public void setAllSpeciesesByTaxa(Collection<AllSpecyEntity> allSpeciesesByTaxa) {
        this.allSpeciesesByTaxa = allSpeciesesByTaxa;
    }
}
