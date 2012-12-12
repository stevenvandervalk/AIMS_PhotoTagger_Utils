package aims.photo.lookups;


import aims.app.reefmonitoring.ejb3.AllSpecyEntity;

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
public class TestSpec {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    Query q = em.createQuery("SELECT s FROM AllSpecyEntity s");
    List<AllSpecyEntity> specys = q.getResultList();

        for (AllSpecyEntity specy : specys){
            System.out.println(specy.getSpeciesSid());
            System.out.println("     " + specy.getSpecies());
            System.out.println("     " + specy.getCommonNames());
            System.out.println("     " + specy.getGenus());
            System.out.println("     " + specy.getTaxonByGenus().getTaxa() + "\n");
        }
    }
}
