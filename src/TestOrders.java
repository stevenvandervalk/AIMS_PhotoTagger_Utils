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
public class TestOrders {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    Query q = em.createQuery("SELECT t FROM OrdersEntity t");
    List<OrdersEntity> taxons =q.getResultList();

        for (OrdersEntity taxon : taxons){
            System.out.println(taxon.getOrdersId());
            System.out.println("     " + taxon.getOrders());
            System.out.println("     " + taxon.getClazzByClazzID().getClazz() + "\n");
        }
    }
}
