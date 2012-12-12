import aims.app.reefmonitoring.ejb3.AllSpecyEntity;
import aims.app.reefmonitoring.ejb3.TaxonEntity;
import com.google.resting.Resting;
import com.google.resting.component.impl.ServiceResponse;

import com.sun.xml.internal.stream.writers.XMLWriter;
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
 */
public class Scraper {

    public static void main(String[] args) {
        ServiceResponse response = Resting.get("http://www.catalogueoflife.org/col/webservice?name=Decapoda&response=full", 80);
        //System.out.println(response);
        String xml = response.getResponseString();
        //System.out.println(xml);

        TaxonEntity decapodas = makeTaxa("2341806");
        //TaxonEntity decapodas = makeTaxa("Hippolytidae");
        List<TaxonEntity> list = new ArrayList<TaxonEntity>();
        list.add(decapodas);


        try {
            XMLEncoder xmlfile = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("test.xml")));
            xmlfile.writeObject(decapodas);
            xmlfile.close();
             ObjectOutputStream e = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("test.bin")));
             e.writeObject(list);
            e.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

//       try {
//            XMLWriter e = new XMLWriter(new BufferedWriter(new FileWriter("test.xml")));
//            e.write(xml);
//            e.close();
//        } catch (IOException e1) {
//            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

//        SAXBuilder builder = new SAXBuilder();
//        try {
//            Document doc = builder.build(new File("test.xml"));
//            Element root = doc.getRootElement();
//            System.out.println(root.toString());
//            List<Element> taxon = root.getChild("result").getChild("child_taxa").getChildren("taxon");
//            List<TaxonEntity> taxons = new ArrayList<TaxonEntity>();
//            for (Element taxa : taxon){
//                String name = taxa.getChild("name").getText();
//                String supertaxa = "Decapoda";
//                String taxalevel = taxa.getChild("rank").getText();
//                TaxonEntity temptaxa = new TaxonEntity(name, supertaxa, taxalevel);
//                taxons.add(temptaxa);
//
//                //System.out.println(name);
//            }
//            for (TaxonEntity taxa : taxons){
//                System.out.println(taxa.getTaxa());
//            }
//
//        } catch (JDOMException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }


    }

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
}
