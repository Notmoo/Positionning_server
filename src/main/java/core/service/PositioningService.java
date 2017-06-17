package core.service;

import core.dao.HibernateDao;
import core.repository.Location;
import core.repository.RssiRecord;
import core.repository.TempRssi;

import java.util.ArrayList;
import java.util.List;

/**
 * PositioningService is a service class designed to provide methods to compute one's location using measured data.
 *
 * To register measured data, please use {@link MeasurementService}. To register calibration data, please use {@link CalibrationService}
 * Created by Guillaume on 09/05/2017.
 */
public class PositioningService {
    
    private HibernateDao hibDao;
    
    public PositioningService () {
        this.hibDao = new HibernateDao();
    }
    
    public Location getLocation (String macAddr) {
    
        //Liste des RSSISample associé à un ap, mesuré pour un client
        List<TempRssi> clientRssi = hibDao.getTempRssi(macAddr);
        
        if(clientRssi.size()>=3) {

            Location bestLoc = null;
            double bestLocDistance = -1;

            for (Location loc : hibDao.getLocations()) {
                //Liste des RSSISample pour une position donnée, chaque RSSISample étant
                //assossié à un AccessPoint
                List<RssiRecord> rssis = hibDao.getRssiRecord(loc.getId());

                double currentLocDistance = rssiDistance(clientRssi, rssis);
                if (bestLocDistance == -1 || currentLocDistance < bestLocDistance) {
                    bestLoc = loc;
                    bestLocDistance = currentLocDistance;
                }
            }
            return bestLoc;
        }
        return null;
    }
    
    private double rssiDistance(List<TempRssi> temps, List<RssiRecord> rssis){
        try {
            double distance = 0;
            for (TempRssi temp : temps) {
                RssiRecord rssi = find(temp.getAp().getMac_addr(), rssis);
                if(rssi!=null)
                    distance+=(temp.getVal()- rssi.getVal())*(temp.getVal()- rssi.getVal());
                else
                    distance+=(temp.getVal()+95)*(temp.getVal()+95);
            }

            for(RssiRecord rssi : rssis){
                if(containsMacAddr(rssi.getAp().getMac_addr(), temps))
                    distance+=(rssi.getVal()+95)*(rssi.getVal()+95);
            }


            return Math.sqrt(distance);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            return -1;
        }
    }

    private RssiRecord find(String macAddr, List<RssiRecord> rrs){
        final List<RssiRecord> results = new ArrayList<>();
        rrs.stream().filter(rr->rr.getAp().getMac_addr().equals(macAddr)).forEach(rr->results.add(rr));
        
        switch(results.size()){
            case 0:
                return null;
            case 1:
                return results.get(0);
            default :
                throw new IllegalArgumentException("Several("+results.size()+") matching RssiRecord for mac address <"+macAddr+">");
        }
    }

    private boolean containsMacAddr(String macAddr, List<TempRssi> temps){
        for(TempRssi temp : temps){
            if(temp.getAp().getMac_addr().equals(macAddr))
                return true;
        }
        return false;
    }
}
