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
 * TestFamily
 * Used to test all elements of the FamilyEntity were populated by the REEFMON database and the relationship between levels was maintained.
 */
public class TestFamily {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    Query q = em.createQuery("SELECT t FROM FamilyEntity t");
    List<FamilyEntity> taxons =q.getResultList();

        for (FamilyEntity taxon : taxons){
            System.out.println(taxon.getFamilyId());
            System.out.println("     " + taxon.getFamily());
            System.out.println("     " + taxon.getOrdersByOrdersID().getOrders() + "\n");
        }
    }
}
