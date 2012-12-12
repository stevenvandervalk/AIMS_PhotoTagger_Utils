package aims.app.reefmon.ejb;


import aims.app.LoggerFactory;
import aims.app.reefmon.ejb.interfaces.ReefmonLookupsServer;
import aims.app.reefmonitoring.ejb3.Keywords;
import aims.app.reefmonitoring.ejb3.RmSectorEntity;
import aims.app.reefmonitoring.ejb3.TaxonEntity;
import org.jboss.annotation.ejb.Management;
import org.jboss.annotation.ejb.Service;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gcoleman
 * Date: 12/09/2008
 * Time: 17:38:15
 * To change this template use File | Settings | File Templates.
 */

@Service(name = "ReefmonLookupsEJB")
public class ReefmonLookups implements ReefmonLookupsServer {



    @Resource
    SessionContext context;

    @PersistenceContext(unitName="REEF-ORACLE")
    protected EntityManager em;
    private List<Keywords> keywords=null;
    private List<TaxonEntity> taxaList=null;
    private List<RmSectorEntity > sectorList=null;


    public void start() {
        makeKeywordList();
        makeSectorList();
        makeTaxaList();
    }

    public List<Keywords> getKeywords() {
        LoggerFactory.LogInfo("Keyword List Request");
        if (keywords == null) {
            makeKeywordList();

        }
        return keywords;
    }

    public void makeKeywordList() {
        LoggerFactory.LogInfo("Make Keyword List");
        Query q =em.createQuery("select o from Keywords o ");
        keywords = q.getResultList();
    }

    public List<TaxonEntity> getTaxa() {
        LoggerFactory.LogInfo("Taxa List Request");
        if (taxaList == null) {
            makeTaxaList();

        }
        return taxaList;

    }

    public void makeTaxaList() {
        LoggerFactory.LogInfo("Make Taxa List");
        Query q =em.createQuery("select o from TaxonEntity o where o.superTaxa is null ");
        taxaList = q.getResultList();
    }

    public List<RmSectorEntity> getSecors() {
        LoggerFactory.LogInfo("Sector List Request");
        if (sectorList == null) {
            makeSectorList();

        }
        return sectorList ;
    }

    public void makeSectorList() {
        LoggerFactory.LogInfo("Make Sector List");
        Query q =em.createQuery("select o from RmSectorEntity o");
        sectorList = q.getResultList();
        for (RmSectorEntity sector: sectorList) {
            sector.getReefGeolocalesByASector().toString();
        }

    }

    public void addKeyword(String kwString) {
        LoggerFactory.LogInfo("Add Keyword " + kwString);
        Keywords keyword = new Keywords();
        keyword.setKeyword(kwString);
        if (getKeywords().contains(keyword)) {
            LoggerFactory.LogInfo("Keyword Exists");
        } else {
            LoggerFactory.LogInfo("Keyword Does not Exist. Add it now.");

            em.persist(keyword);
            getKeywords().add(keyword);
            
        }


    }



}
