import aims.app.reefmonitoring.ejb3.TaxonEntity;
import com.sun.corba.se.impl.logging.ORBUtilSystemException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.*;
import java.util.*;
import java.beans.XMLEncoder;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amoore
 * Date: 28/11/12
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 * Uses data in the REEFMON database to construct bin files for use in the Photo Tagger App.
 */
public class createAllLookupFiles {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static EntityManager em = factory.createEntityManager();

    public static void main(String[] args) {
        createLookupFiles();
    }

    /**
     * Creates the 3 bin files required by the Photo Tagger App, built from the data in the REEFMON database.
     */
    public static void createLookupFiles() {
        List<Keywords> keywords = getQuery("Keywords");
        List<RmSectorEntity> sectors = getQuery("RmSectorEntity");
        List<TaxonEntity> taxons = getQuery("TaxonEntity");

        for (RmSectorEntity sect : sectors){
            sect.setReefGeolocalesByASector(sect.getReefGeolocalesByASector());
        }
        writetoBin(keywords, "keywords");
        writetoBin(sectors, "sectors");
        writetoBin(taxons, "taxons");

    }

    /**
     * Queries a table in REEFMON database to get data.
     * @param tableName,
     * @return List
     */
    public static List getQuery(String tableName){
        Query q = em.createQuery("select o from "+ tableName + " o ");
        return q.getResultList();
    }

    /**
     * Writes the entity list to a bin file, following the relationship structure of the data.
     * @param entityList
     * @param fileName
     */
    public static void writetoBin(List entityList, String fileName)  {
        ObjectOutputStream e = null;
        try {
            e = new ObjectOutputStream(
                            new FileOutputStream(fileName +".bin"));
            e.writeObject(entityList);

            e.close();

        } catch (Exception e2) {
            System.out.println(e2);
        }
    }
    /**
     * Writes the entity list to a XML file, following the relationship structure of the data.
     * @param entityList
     * @param fileName
     */
    public static void writetoXML (List<Object> entityList, String fileName){
        XMLEncoder xml = null;
        try {
            xml = new XMLEncoder(new FileOutputStream(fileName + ".xml"));
            xml.writeObject(entityList);
            xml.close();
        } catch (IOException iox) {
            System.out.println(iox);
        }
    }
}
