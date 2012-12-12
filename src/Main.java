

 import org.eclipse.persistence.descriptors.changetracking.ObjectChangeTrackingPolicy;

 import javax.persistence.*;
 import java.beans.XMLEncoder;
 import java.io.*;
 import java.sql.Statement;
 import java.util.List;
 import java.sql.ResultSet;

public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
         factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

       Query q = em.createQuery("SELECT k FROM Keywords k");
        List<Keywords> keywords =  q.getResultList();

       /* for (Keywords keyword : keywords){
            System.out.println(keyword.getKeyword());
        }
       */ /*Query q = em.createQuery("SELECT g FROM TaxonEntity g ");

        List<TaxonEntity> locales =q.getResultList();



        XMLEncoder xml = null;

        try {
            xml = new XMLEncoder( new BufferedOutputStream(new FileOutputStream("c:/Documents and Settings/amoore/Desktop/animals.xml")));
            xml.writeObject(locales);
            xml.close();
        }   catch (IOException iox) {
            System.out.println(iox);
            }        */

        ObjectOutputStream e = null;

        try {
            e = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream("c:/Documents and Settings/thart/Desktop/AIMSPhotoTagger/keywords.bin")));
            e.writeObject(keywords);
            e.close();

        } catch (Exception e2) {

        } /*

        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream( new BufferedInputStream(new FileInputStream("c:/Documents and Settings/thart/Desktop/keywords.bin")));
            List<Keywords> keywords2 = (List) in.readObject();
        }   catch (IOException fx){
            System.out.println(fx);
        }   catch (ClassNotFoundException cx){
            System.out.println(cx);
        }
        //for (Keywords keyword : keywords){
         //   System.out.println(keyword.getKeyword());
         }           */ }
}
