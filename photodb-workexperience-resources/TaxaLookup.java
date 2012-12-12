package aims.photo.uploader.Utils;

import aims.app.generic.logger.LoggerFactory;
import aims.app.generic.utilities.CaseInsensitiveString;
import aims.app.generic.utilities.Utils;
import aims.app.reefmonitoring.ejb3.TaxonEntity;
import aims.app.reefmonitoring.model.GenusSpecies;

import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gcoleman
 * Date: 2/07/2008
 * Time: 14:59:16
 * To change this template use File | Settings | File Templates.
 */
public class TaxaLookup {
    private static TaxaLookup ourInstance = new TaxaLookup ();

    public static TaxaLookup getInstance() {
        return ourInstance;
    }

    private TaxaLookup () {
    }

    private List<TaxonEntity> list;
    private List<CaseInsensitiveString> caseInsensitiveList;
    private Map<String, TaxonConvenient> nameToTaxon;

    public void populate() {

        if (readFromServer()) {
               LoggerFactory.LogInfo("Taxa List obtained from the server.");
               writeToXML();
           } else {
               if (readFromXML()){
                   LoggerFactory.LogInfo("Taxa List obtained from the local file.");
               } else {
                   throw new RuntimeException("ERROR!!! You are not connected to the AIMS server. Also it appears you have not run this program before. The first time you run this program, you should be connected to the AIMS server.");            }

           }

        nameToTaxon = new HashMap<String, TaxonConvenient>(list.size());
        caseInsensitiveList = new ArrayList<CaseInsensitiveString>(list.size());
        makeHashTable(list);


    }

    private void makeHashTable(Collection<TaxonEntity> taxonList) {
        if (taxonList != null) {
            for (TaxonEntity t: taxonList) {
                caseInsensitiveList.add(new CaseInsensitiveString(t.getTaxa()));
                nameToTaxon.put(t.getTaxa(), new TaxonConvenient(t));
                makeHashTable(t.getTaxonsByTaxa());

            }

        }
    }

    public GenusSpecies isSpecies (String genus, String species) {
        TaxonConvenient genusC = nameToTaxon.get(genus);
        if (genusC != null) {
            String species1 = genusC.isSpecies(species);
            if (species1 == null) {
                return null;
            } else {
                return new GenusSpecies(genusC.getTaxonEntity().getTaxa(), species1);

            }
        } else {
            return null;
        }
    }

    public String isTaxa(String s) {
        try {
            int i = caseInsensitiveList.indexOf(new CaseInsensitiveString(s));
            if (i ==-1 ) {
                return null;
            } else {
                return caseInsensitiveList.get(i).getString();
            }

        } catch (Exception e) {
            LoggerFactory.LogSevereException(e);
             return null;
        }
    }

    private boolean readFromServer() {
        try {
            list = ReefmonLookupsAccessor.getInstance().getReefmonLookups().getTaxa();
            return true;
        } catch (Throwable e) {
            return false;
        }

    }
    

    public List<TaxonEntity> getList() {
        return list;
    }

    public void writeToXML() {
        ObjectOutputStream e = null;

        File dir = new File("c:/aims");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            e = new ObjectOutputStream (
                               new BufferedOutputStream(
                                   new FileOutputStream("c:/aims/taxon.bin")));
           e.writeObject(list);
           e.close();

        } catch (Exception e2) {
            LoggerFactory.LogSevereException(e2);
        }
    }
    private boolean readFromXML() {
        try {
            ObjectInputStream d = null;
            d = new ObjectInputStream (
                               new BufferedInputStream(
                                   new FileInputStream("c:/aims/taxon.bin")));
            list = (List) d.readObject();
            d.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String fullTaxaInfo(String taxa, String species) {
        TaxonEntity taxon = nameToTaxon.get(taxa).getTaxonEntity();
        String taxaInfo = fullTaxaInfo(taxon);
        if (!Utils.isNullOrEmpty(species) ) {
            taxaInfo = taxaInfo + "!" + species;
        }

        return taxaInfo;

    }



    public static final String inBrackets(String s) {
        if (Utils.isNullOrEmpty(s)) {
            return "";
        } else {
            return "(" + s + ")";
        }
    }
    public String fullTaxaInfo(TaxonEntity taxon) {
        TaxonEntity superTaxa = taxon.getTaxonBySuperTaxa();
        if (superTaxa== null) {
            return "TAXONOMY!" + taxon.getTaxa() + inBrackets(taxon.getCommonNames());
        } else {
            return fullTaxaInfo(superTaxa) + "!" + taxon.getTaxa()+ inBrackets(taxon.getCommonNames());
        }

    }

    public static void main(String[] args) {
        TaxaLookup.getInstance().populate();

//        LoggerFactory.LogInfo(KeywordLookup.getInstance().getList().toString());
        System.exit(0);

    }




}
