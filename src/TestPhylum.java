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
public class TestPhylum {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    Query q = em.createQuery("SELECT t FROM PhylumEntity t");
    List<PhylumEntity> taxons =q.getResultList();

        for (PhylumEntity taxon : taxons){
            System.out.println(taxon.getPhylumId());
            System.out.println("     " + taxon.getPhylum());
            System.out.println("     " + taxon.getClazzByPhylumID() + "\n");
        }
    }
}
