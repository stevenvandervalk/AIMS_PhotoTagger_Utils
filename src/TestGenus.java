import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amoore
 * Date: 28/11/12
 * Time: 8:19 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * TestGenus
 * Used to test all elements of the GenusEntity were populated by the REEFMON database and the relationship between levels was maintained.
 */
public class TestGenus {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    Query q = em.createQuery("SELECT t FROM GenusEntity t");
    //q.setMaxResults(5000);
    List<GenusEntity> taxons =q.getResultList();

        for (GenusEntity taxon : taxons){
            System.out.println(taxon.getGenusId());
            System.out.println("     " + taxon.getGenus());
            System.out.println("     " + taxon.getFamilyByFamilyID().getFamily() + "\n");
        }
    }
}
