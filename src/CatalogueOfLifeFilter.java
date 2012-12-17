import aims.app.reefmonitoring.ejb3.AllSpecyEntity;
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
 * Accesses Catalogue of Life files added to the REEFMON database.  Is no longer used in the photo tagger app.
 */
public class CatalogueOfLifeFilter {
    private static final String PERSISTENCE_UNIT_NAME = "REEF-DERBY";
    private static EntityManagerFactory factory;
    private List<TaxonEntity> taxons;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();


        List<TaxonEntity> decapoda = getTaxonEntities(em);


        writetoXML(decapoda, "decapoda");

        //Checking data and structure is correct.
        int count = 1;
        for (TaxonEntity taxon : decapoda){
            System.out.println(count);
            System.out.println(taxon.getTaxa());
            System.out.println("     " + taxon.getSuperTaxa());
            System.out.println("     " + taxon.getTaxaLevel() + "\n");
            count++;
        }
    }

    /**
     * Runs the Query and builds the Taxon structure from the REEFMON database.
     * @param em
     * @return decapoda
     */
    private static List<TaxonEntity> getTaxonEntities(EntityManager em) {
        // This query is used to get all the starfish
        // Query q = em.createQuery("SELECT t FROM SpeciesEntity t WHERE t.genusByGenusID.familyByFamilyID.ordersByOrdersID.clazzByClazzID.phylumByPhylumID.phylumId IN ('68.00')");

        // This query is used to get all the crustaceans (crabs)
        Query q = em.createQuery("SELECT t FROM SpeciesEntity t WHERE t.genusByGenusID.familyByFamilyID.ordersByOrdersID.clazzByClazzID.classId IN ('203.00')");

        //This block is required for constructing the Echinodermata (Starfish) Taxons.
//        Query q = em.createQuery("Select t From PhylumEntity t Where t.phylumId = '68.00'");
//        List<PhylumEntity> phylums = q.getResultList();
//        List<TaxonEntity> echinoderm = getPhylumTaxons(phylums);
//        for (int i = 0; i < phylums.size(); i++) {
//            List<ClazzEntity> clazzes = phylums.get(i).getClazzByPhylumID();
//            List<TaxonEntity> taxonClazzes = getClazzTaxons(clazzes);
//            echinoderm.get(i).setTaxonsByTaxa(taxonClazzes);
//            echinoderm.get(i).setAllSpeciesesByTaxa(new ArrayList<AllSpecyEntity>());
//            for (int j = 0; j < clazzes.size(); j++) {
//                List<OrdersEntity> orders = clazzes.get(j).getOrdersByClazzID();
//                List<TaxonEntity> taxonOrders = getOrdersTaxons(orders);
//                taxonClazzes.get(j).setTaxonsByTaxa(taxonOrders);
//                taxonClazzes.get(j).setAllSpeciesesByTaxa(new ArrayList<AllSpecyEntity>());
//        Query q = em.createQuery("Select t From OrdersEntity t Where t.ordersId = '661.00'");
        List<OrdersEntity> orders = q.getResultList();
        List<TaxonEntity> decapoda = getOrdersTaxons(orders);
        for (int k = 0; k < orders.size(); k++) {
            List<FamilyEntity> families = orders.get(k).getfamilyByOrdersID();
            List<TaxonEntity> taxonFamilies = getFamilyTaxons(families);
            decapoda.get(k).setTaxonsByTaxa(taxonFamilies);
            decapoda.get(k).setAllSpeciesesByTaxa(new ArrayList<AllSpecyEntity>());
            for (int l = 0; l < families.size(); l++) {
                List<GenusEntity> genus = families.get(l).getGenusByFamilyID();
                List<TaxonEntity> taxonGenus = getGenusTaxons(genus);
                taxonFamilies.get(l).setTaxonsByTaxa(taxonGenus);
                taxonFamilies.get(l).setAllSpeciesesByTaxa(new ArrayList<AllSpecyEntity>());
                for (int m = 0; m < genus.size(); m++) {
                    List<SpeciesEntity> species = genus.get(m).getSpeciesByGenusID();
                    List<AllSpecyEntity> taxonSpecies = getSpeciesTaxons(species);
                    taxonGenus.get(m).setTaxonsByTaxa(new ArrayList<TaxonEntity>());
                    taxonGenus.get(m).setAllSpeciesesByTaxa(taxonSpecies);
                }
            }
        }
//            }
//        }
        return decapoda;
    }

    /**
     * Creates the Phylum level of the Taxon structure.
     * @param species
     * @return phylumTaxons
     */
   public static List<TaxonEntity> getPhylumTaxons (List<PhylumEntity> species){
       List<TaxonEntity> phylumTaxons = new ArrayList<TaxonEntity>();
       String previousTaxa = null;
       for (PhylumEntity specy : species){
           String taxa = specy.getPhylum();
           String superTaxa = "Animalia";
           String taxaLevel = "Phylum";
           TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
           if (taxa.equals(previousTaxa)){

           }
           else {
               phylumTaxons.add(tempTaxon);
           }

           previousTaxa = taxa;

       }
       return phylumTaxons;
   }

    /**
     * Creates the Class level of the Taxon structure.
     * @param species
     * @return clazzTaxons
     */
    public static List<TaxonEntity> getClazzTaxons (List<ClazzEntity> species){
        List<TaxonEntity> clazzTaxons = new ArrayList<TaxonEntity>();
        String previousTaxa = null;
        for (ClazzEntity specy : species){
            String taxa = specy.getClazz();
            String superTaxa = specy.getPhylumByPhylumID().getPhylum();
            String taxaLevel = "Class";
            TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
            if (taxa.equals(previousTaxa)){

            }
            else {
                clazzTaxons.add(tempTaxon);
            }

            previousTaxa = taxa;

        }
        return clazzTaxons;
    }

    /**
     * Creates the Orders level of the Taxon structure.
     * @param species
     * @return ordersTaxon
     */
    public static List<TaxonEntity> getOrdersTaxons (List<OrdersEntity> species){
        List<TaxonEntity> ordersTaxons = new ArrayList<TaxonEntity>();
        String previousTaxa = null;
        for (OrdersEntity specy : species){
            String taxa = specy.getOrders();
            String superTaxa = specy.getClazzByClazzID().getClazz();
            String taxaLevel = "Orders";
            TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
            if (taxa.equals(previousTaxa)){

            }
            else {
                ordersTaxons.add(tempTaxon);
            }

            previousTaxa = taxa;

        }
        return ordersTaxons;
    }

    /**
     * Creates the Family level of the Taxon structure.
     * @param species
     * @return familyTaxon
     */
    public static List<TaxonEntity> getFamilyTaxons (List<FamilyEntity> species){
        List<TaxonEntity> familyTaxons = new ArrayList<TaxonEntity>();
        String previousTaxa = null;
        for (FamilyEntity specy : species){
            String taxa = specy.getFamily();
            String superTaxa = specy.getOrdersByOrdersID().getOrders();
            String taxaLevel = "Family";
            TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
            if (taxa.equals(previousTaxa)){

            }
            else {
                familyTaxons.add(tempTaxon);
            }

            previousTaxa = taxa;

        }
        return familyTaxons;
    }

    /**
     * Creates the Genus level of the Taxon structure.
     * @param species
     * @return genusTaxon
     */
    public static List<TaxonEntity> getGenusTaxons (List<GenusEntity> species){
        List<TaxonEntity> genusTaxons = new ArrayList<TaxonEntity>();
        String previousTaxa = null;
        for (GenusEntity specy : species){
            String taxa = specy.getGenus();
            String superTaxa = specy.getFamilyByFamilyID().getFamily();
            String taxaLevel = "Genus";
            TaxonEntity tempTaxon = new TaxonEntity(taxa, superTaxa, taxaLevel);
            if (taxa.equals(previousTaxa)){

            }
            else {
                genusTaxons.add(tempTaxon);
            }

            previousTaxa = taxa;

        }
        return genusTaxons;
    }

    /**
     * Creates the Species level of the Taxon structure using the AllSpecyEntity.
     * @param species
     * @return speciesTaxon
     */
    public static List<AllSpecyEntity> getSpeciesTaxons (List<SpeciesEntity> species){
        List<AllSpecyEntity> speciesTaxons = new ArrayList<AllSpecyEntity>();
        for (SpeciesEntity specy : species){
            String taxa = specy.getSpecies();
            String superTaxa = specy.getGenusByGenusID().getGenus();
            String taxaLevel = "Species";
            AllSpecyEntity tempTaxon = new AllSpecyEntity(taxa, superTaxa);
            speciesTaxons.add(tempTaxon);
        }
        return speciesTaxons;
    }

    /**
     * Makes the bin file.
     * @param taxonList
     * @param fileName
     */
    public static void writetoBin(List taxonList, String fileName)  {
        ObjectOutputStream e = null;
        try {
            e = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(fileName +".bin")));
            e.writeObject(taxonList);
            e.close();

        } catch (Exception e2) {
            System.out.println(e2);
        }
    }

    /**
     * Makes the xml file.
     * @param list
     * @param fileName
     */
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
