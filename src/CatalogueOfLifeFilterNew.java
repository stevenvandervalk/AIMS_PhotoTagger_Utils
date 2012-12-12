import aims.app.reefmonitoring.ejb3.TaxonEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amoore
 * Date: 30/11/12
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class CatalogueOfLifeFilterNew {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;
    private List<TaxonEntity> taxons;

    public static void main(String[] args) {
    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    /**
     * This query is used to get all the starfish
    */
    Query q = em.createQuery("SELECT t FROM PhylumEntity t WHERE t.phylumId IN ('68.00')");

    /**
    * This query is used to get all the crustaceans (crabs)
    */
    //Query q = em.createQuery("SELECT t FROM SpeciesEntity t WHERE t.genusByGenusID.familyByFamilyID.ordersByOrdersID.clazzByClazzID.classId IN ('203.00')");
        List<PhylumEntity> phylum = q.getResultList();

        for (PhylumEntity p : phylum)  {
            //System.out.println(p.getClazzes());
        }
        //List<TaxonEntity> allTaxons = getGenusTaxons(phylum);
//        allTaxons.addAll(getPhylumTaxons(species));
//        allTaxons.addAll(getClazzTaxons(species));
//        allTaxons.addAll(getOrdersTaxons(species));
//        allTaxons.addAll(getFamilyTaxons(species));
//        allTaxons.addAll(getGenusTaxons(species));
        int count = 1;

//        for (TaxonEntity taxa : allTaxons){
//            taxa.setAllSpeciesesByTaxa(taxa.getAllSpeciesesByTaxa());
//            taxa.setTaxonsByTaxa(taxa.getTaxonsByTaxa());
//            System.out.println(count);
//            System.out.println(taxa.getTaxa());
//            System.out.println("     " + taxa.getSuperTaxa());
//            System.out.println("     " + taxa.getTaxaLevel());
//            System.out.println(taxa.getAllSpeciesesByTaxa());
//            System.out.println(taxa.getTaxonsByTaxa() + "\n");
//            count ++;
//        }

     //   writetoXML(allTaxons, "echinoderm");

        /*for (TaxonEntity taxon : allTaxons){
            System.out.println(count);
            System.out.println(taxon.getTaxa());
            System.out.println("     " + taxon.getSuperTaxa());
            System.out.println("     " + taxon.getTaxaLevel() + "\n");
            count++;
        }   */
    }

   // public List<TaxonEntity> addClazzes (q)
   public static List<TaxonEntity> getPhylumTaxons (List<PhylumEntity> species){
       List<TaxonEntity> phylumTaxons = new ArrayList<TaxonEntity>();
       String previousTaxa = null;
       for (PhylumEntity specy : species){
           String taxa = specy.getPhylum();
           String superTaxa = "Animalia";
           String taxaLevel = "Phylum";
           TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
           if (taxa == previousTaxa){

           }
           else {
               phylumTaxons.add(tempTaxon);
           }

           previousTaxa = taxa;

       }
       return phylumTaxons;
   }

    public static List<TaxonEntity> getClazzTaxons (List<SpeciesEntity> species){
        List<TaxonEntity> clazzTaxons = new ArrayList<TaxonEntity>();
        String previousTaxa = null;
        for (SpeciesEntity specy : species){
            String taxa = specy.getGenusByGenusID().getFamilyByFamilyID().getOrdersByOrdersID().getClazzByClazzID().getClazz();
            String superTaxa = specy.getGenusByGenusID().getFamilyByFamilyID().getOrdersByOrdersID().getClazzByClazzID().getPhylumByPhylumID().getPhylum();
            String taxaLevel = "Class";
            TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
            if (taxa == previousTaxa){

            }
            else {
                clazzTaxons.add(tempTaxon);
            }

            previousTaxa = taxa;

        }
        return clazzTaxons;
    }

    public static List<TaxonEntity> getOrdersTaxons (List<SpeciesEntity> species){
        List<TaxonEntity> ordersTaxons = new ArrayList<TaxonEntity>();
        String previousTaxa = null;
        for (SpeciesEntity specy : species){
            String taxa = specy.getGenusByGenusID().getFamilyByFamilyID().getOrdersByOrdersID().getOrders();
            String superTaxa = specy.getGenusByGenusID().getFamilyByFamilyID().getOrdersByOrdersID().getClazzByClazzID().getClazz();
            String taxaLevel = "Orders";
            TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
            if (taxa == previousTaxa){

            }
            else {
                ordersTaxons.add(tempTaxon);
            }

            previousTaxa = taxa;

        }
        return ordersTaxons;
    }

    public static List<TaxonEntity> getFamilyTaxons (List<SpeciesEntity> species){
        List<TaxonEntity> familyTaxons = new ArrayList<TaxonEntity>();
        String previousTaxa = null;
        for (SpeciesEntity specy : species){
            String taxa = specy.getGenusByGenusID().getFamilyByFamilyID().getFamily();
            String superTaxa = specy.getGenusByGenusID().getFamilyByFamilyID().getOrdersByOrdersID().getOrders();
            String taxaLevel = "Family";
            TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
            if (taxa == previousTaxa){

            }
            else {
                familyTaxons.add(tempTaxon);
            }

            previousTaxa = taxa;

        }
        return familyTaxons;
    }

    public static List<TaxonEntity> getGenusTaxons (List<GenusEntity> species){
        List<TaxonEntity> genusTaxons = new ArrayList<TaxonEntity>();
        String previousTaxa = null;
        for (GenusEntity specy : species){
            String taxa = specy.getGenus();
            String superTaxa = specy.getFamilyByFamilyID().getFamily();
            String taxaLevel = "Genus";
            TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
            if (taxa == previousTaxa){

            }
            else {
                genusTaxons.add(tempTaxon);
            }

            previousTaxa = taxa;

        }
        return genusTaxons;
    }


    public static List<TaxonEntity> getSpeciesTaxons (List<SpeciesEntity> species){
        List<TaxonEntity> speciesTaxons = new ArrayList<TaxonEntity>();
        for (SpeciesEntity specy : species){
            String taxa = specy.getSpecies();
            String superTaxa = specy.getGenusByGenusID().getGenus();
            String taxaLevel = "Species";
            TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
            speciesTaxons.add(tempTaxon);
        }
        return speciesTaxons;
    }

    public static void writetoBin(List a, String fileName)  {
        ObjectOutputStream e = null;
        try {
            e = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(fileName +".bin")));
            e.writeObject(a);
            e.close();

        } catch (Exception e2) {
            System.out.println(e2);
        }
    }

    public static void writetoXML (List list, String fileName){
        XMLEncoder xml = null;
        try {
            xml = new XMLEncoder( new BufferedOutputStream(new FileOutputStream(fileName + ".xml")));
            xml.writeObject(list);
            xml.close();
        } catch (IOException iox) {
            System.out.println(iox);
        }
    }
}
