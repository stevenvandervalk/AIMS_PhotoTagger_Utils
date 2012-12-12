import aims.app.reefmonitoring.ejb3.TaxonEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amoore
 * Date: 28/11/12
 * Time: 8:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestTaxon {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    Query q = em.createQuery("SELECT t FROM TaxonEntity t");
    List<TaxonEntity> taxons =q.getResultList();

        for (TaxonEntity taxon : taxons){
            System.out.println(taxon.getTaxa());
            System.out.println("     " + taxon.getTaxa());
            System.out.println("     " + taxon.getSuperTaxa());
            System.out.println("     " + taxon.getAimsCode());
            System.out.println("     " + taxon.getCommonNames());
            System.out.println("     " + taxon.getTaxaLevel());
            System.out.println("     " + taxon.getTaxonBySuperTaxa().getTaxonsByTaxa() + "\n");
        }
    }
}
