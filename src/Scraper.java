import aims.app.reefmonitoring.ejb3.AllSpecyEntity;
import aims.app.reefmonitoring.ejb3.TaxonEntity;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thart
 * Date: 12/12/12
 * Time: 8:58 AM
 * To change this template use File | Settings | File Templates.
 * Scraper creates all the bin files needed for use with the Photo Tagging App.
 * Accesses the Catalogue of Life webservices to extend the Taxons availible in the
 * REEFMON Database.  Further Taxons may be added to the bin file compilation as required.
 */

public class Scraper {

    /**
     * Creates 5 bin files.
     */
    public static void main(String[] args) {
        getEchinoderms();
        getDecapoda();
        createAllLookupFiles.createLookupFiles();
    }

    /**
     * Decapoda Bin creation. (Crabs, Lobsters, Prawns, etc)
     */
    private static void getDecapoda() {
        TaxonEntity decapodas = makeTaxa("2341806");
        List<TaxonEntity> decapodasList = new ArrayList<TaxonEntity>();
        decapodasList.add(decapodas);
        makeBins("decapodas", decapodasList);
    }

    /**
     * Echinodermata Bin creation. (Starfish, etc)
     */
    private static void getEchinoderms() {
        TaxonEntity echinodermata = makeTaxa("2339667");
        List<TaxonEntity> echinodermataList = new ArrayList<TaxonEntity>();
        echinodermataList.add(echinodermata);
        makeBins("echinodermata", echinodermataList);
    }

    /**
     * Creates a structured TaxonEntity from the Taxon name or ID String provided.
     * Data read from the Catalogue of Life site.
     * The relationships of Taxon levels will be constructed.
     * @param name, used in url query
     * @return TaxonEntity, structured.
     */
    private static TaxonEntity makeTaxa(String name) {

        String url = "http://www.catalogueoflife.org/annual-checklist/2012/webservice?id=" + name + "&response=full";

        SAXBuilder builder = new SAXBuilder();
        Document doc;

        TaxonEntity temptaxa = new TaxonEntity();
        try {
            doc = builder.build(url);
            temptaxa.setTaxa(doc.getRootElement().getChild("result").getChildText("name"));
            temptaxa.setTaxaLevel(doc.getRootElement().getChild("result").getChildText("rank"));
            temptaxa.setAimsCode(doc.getRootElement().getChild("result").getChildText("id"));


            if (temptaxa.getTaxaLevel().equals("Genus")) {

                temptaxa.setAllSpeciesesByTaxa(getSpecies(temptaxa.getAimsCode()));
                temptaxa.setTaxonsByTaxa(new ArrayList<TaxonEntity>());
                } else {
                temptaxa.setTaxonsByTaxa(getSubClasses(temptaxa.getAimsCode()));
                temptaxa.setAllSpeciesesByTaxa(new ArrayList<AllSpecyEntity>());
                }

        } catch (Exception e) {
            System.out.println("Error getting this " + url);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return temptaxa;

    }

    /**
     * Creates the Specie element of the Taxon structure.
     * Data read from the Catalogue of Life site.
     * @param name, used in url query
     * @return AllSpecyEntity
     */
    private static List<AllSpecyEntity> getSpecies(String name) {

        String url = "http://www.catalogueoflife.org/annual-checklist/2012/webservice?id=" + name + "&response=full";

        SAXBuilder builder = new SAXBuilder();
        Document doc;

        List<AllSpecyEntity> specyEntities = new ArrayList<AllSpecyEntity>();
        try {
            doc = builder.build(url);
            for (Element taxa : (List<Element>) doc.getRootElement().getChild("result").getChild("child_taxa").getChildren("taxon")) {

                AllSpecyEntity temptaxa = new AllSpecyEntity();
                temptaxa.setSpecies(taxa.getChildText("name"));
                specyEntities.add(temptaxa);
            }
        } catch (Exception e) {
            System.out.println("Error getiing this " + url);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return specyEntities;
    }

    /**
     * Constructs the relationship of each Taxon at each Tasa level.
     * @param name, used in url query
     * @return TaxonEntity
     */
    public static List<TaxonEntity> getSubClasses(String name) {

        String url = "http://www.catalogueoflife.org/annual-checklist/2012/webservice?id=" + name + "&response=full";

        SAXBuilder builder = new SAXBuilder();
        Document doc;
        List<TaxonEntity> subClasses = new ArrayList<TaxonEntity>();
        try {
            doc = builder.build(url);
            for (Element taxa : (List<Element>) doc.getRootElement().getChild("result").getChild("child_taxa").getChildren("taxon")) {
//                if (taxa.getChildText("rank").equals("Species")){
//                    AllSpecyEntity temptaxa = new AllSpecyEntity();
//                    temptaxa.setSpecies(taxa.getChildText("name"));
//                   }  else {
                TaxonEntity temptaxa = makeTaxa(taxa.getChildText("id"));
                subClasses.add(temptaxa);
                //}


            }
        } catch (JDOMException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NullPointerException ne){
            System.out.println(ne.toString());
            System.out.println("Entity not found");
        }


        return subClasses;
    }

    /**
     * Bin files
     * @param fileName
     * @param taxonEntityList
     */
    public static void makeBins(String fileName, List<TaxonEntity> taxonEntityList) {
        try {
            ObjectOutputStream e = new ObjectOutputStream(new FileOutputStream(fileName + ".bin"));
            e.writeObject(taxonEntityList);
            e.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    /**
     * XML files
     * @param fileName
     * @param taxonEntityList
     */
    public static void makeXml(String fileName, List<TaxonEntity> taxonEntityList) {
        try {
            XMLEncoder xmlfile = new XMLEncoder(new FileOutputStream(fileName + ".xml"));
            xmlfile.writeObject(taxonEntityList);
            xmlfile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
