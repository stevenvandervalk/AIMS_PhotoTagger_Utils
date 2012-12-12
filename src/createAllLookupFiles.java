import aims.app.reefmonitoring.ejb3.TaxonEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.*;
import java.util.*;
import java.beans.XMLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: amoore
 * Date: 28/11/12
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class createAllLookupFiles {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static EntityManager em = factory.createEntityManager();

    public static void main(String[] args) {

        List<Keywords> keywords = getQuery("Keywords");
        List<RmSectorEntity> sectors = getQuery("RmSectorEntity");
        //List<TaxonEntity> taxons = getQuery("TaxonEntity");

        for (RmSectorEntity sect : sectors){
            sect.setReefGeolocalesByASector(sect.getReefGeolocalesByASector());
        }
        //writetoBin(keywords, "keywords");
        writetoXML(sectors, "sectors");
        //writetoBin(taxons, "taxons");
    }
    public static List getQuery(String a){
        Query q = em.createQuery("select o from "+ a + " o ");
        return q.getResultList();
    }

    public static void writetoBin(List a, String b)  {
        ObjectOutputStream e = null;
        try {
            e = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(b +".bin")));
            e.writeObject(a);
            e.close();

        } catch (Exception e2) {
            System.out.println(e2);
        }
    }

    public static void writetoXML (List a, String b){
        XMLEncoder xml = null;
        try {
            xml = new XMLEncoder( new BufferedOutputStream(new FileOutputStream(b + ".xml")));
            xml.writeObject(a);
            xml.close();
        } catch (IOException iox) {
            System.out.println(iox);
        }
    }
}
