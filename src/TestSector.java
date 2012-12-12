import org.eclipse.persistence.internal.queries.StatementQueryMechanism;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 27/11/12
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestSector {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;


    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        Query q = em.createQuery("SELECT g FROM RmSectorEntity g ");
        List<RmSectorEntity> sectors = q.getResultList();

        for (RmSectorEntity sector : sectors)  {
            System.out.println(sector.getAsector());
            System.out.println(sector.getSectorName());
        }

    }
}
