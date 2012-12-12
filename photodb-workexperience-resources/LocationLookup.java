package aims.photo.uploader.Utils;

import aims.app.generic.logger.LoggerFactory;
import aims.app.reefmonitoring.ejb3.RmSectorEntity;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gcoleman
 * Date: 11/08/2008
 * Time: 14:47:06
 * To change this template use File | Settings | File Templates.
 */
public class LocationLookup {
    private static LocationLookup ourInstance = new LocationLookup ();

    public static LocationLookup getInstance() {
        return ourInstance;
    }

    private LocationLookup () {
    }

    private List<RmSectorEntity> list;

    public void populate() {


        if (readFromServer()) {
            LoggerFactory.LogInfo("Sector List obtained from the server.");
            writeToXML();
        } else {
            if (readFromXML()){
                LoggerFactory.LogInfo("Sector List obtained from the local file.");
            } else {
                throw new RuntimeException("ERROR!!! You are not connected to the AIMS server. Also it appears you have not run this program before. The first time you run this program, you should be connected to the AIMS server. Your keyword tree will be empty");            }

        }


    }

    private boolean readFromServer() {
        try {
            list = ReefmonLookupsAccessor.getInstance().getReefmonLookups().getSecors();
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }

    }


    public List<RmSectorEntity> getList() {
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
                                   new FileOutputStream("c:/aims/location.bin")));
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
                                   new FileInputStream("c:/aims/location.bin")));
            list = (List) d.readObject();
            Collections.sort(list);
            d.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        LocationLookup.getInstance().populate();
        LoggerFactory.LogInfo(LocationLookup.getInstance().getList().toString());
        System.exit(0);

    }


}
