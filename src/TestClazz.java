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
public class TestClazz {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    Query q = em.createQuery("SELECT t FROM ClazzEntity t WHERE t.phylumId = '68.00'");
    List<ClazzEntity> taxons =q.getResultList();

        for (ClazzEntity taxon : taxons){
            System.out.println(taxon.getClassId());
            System.out.println("     " + taxon.getClazz());
            System.out.println("     " + taxon.getPhylumByPhylumID().getPhylum() + "\n");
        }
    }
}
