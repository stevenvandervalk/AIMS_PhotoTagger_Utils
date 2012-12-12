package aims.photo.lookups;


import aims.app.reefmonitoring.ejb3.*;



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

       /* List tempKeywords = em.createQuery("select o from Keywords o ").getResultList();
        ArrayList<Keywords> keywords = new ArrayList<Keywords>(tempKeywords);
        ArrayList<RmSectorEntity> sectors = new ArrayList<RmSectorEntity>(em.createQuery("select o from RmSectorEntity o ").getResultList());
        for (RmSectorEntity sector: sectors) {
            sector.setReefGeolocalesByASector(new ArrayList<ReefGeolocale>(sector.getReefGeolocalesByASector()));
        }
         */
        ArrayList<TaxonEntity> taxons = new ArrayList<TaxonEntity>(em.createQuery("Select o from TaxonEntity o").getResultList());
        for (TaxonEntity taxa : taxons){
            taxa.setAllSpeciesesByTaxa(new ArrayList<AllSpecyEntity>(taxa.getAllSpeciesesByTaxa()));
            taxa.setTaxonsByTaxa(new ArrayList<TaxonEntity>(taxa.getTaxonsByTaxa()));
        }



   //     writetoXML(keywords, "keywords");
 //       writetoXML(sectors, "sectors");
        writetoXML(taxons, "taxons");
    }
    public static ArrayList getQuery(String a){
        Query q = em.createQuery("select o from "+ a + " o ");
        return new ArrayList(q.getResultList());
    }

    public static void writetoBin(ArrayList a, String b)  {
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

    public static void writetoXML (ArrayList a, String b){
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
