package core.service;

import core.dao.DebianDao;
import core.dao.HibernateDao;
import core.repository.Location;
import core.repository.RssiRecord;
import core.repository.TempRssi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 09/05/2017.
 */
public class PositioningService {
    
    private static final double DEFAULT_POSITIONING_PRECISION = 7.5;
    
    private HibernateDao hibDao;
    private DebianDao debDao;
    
    public PositioningService () {
        this.debDao = new DebianDao();
        this.hibDao = new HibernateDao();
    }
    
    public Location getLocation (String ipAddr) {
    
        //Liste des RSSISample associé à un ap, mesuré pour un client
        List<TempRssi> clientRssi = hibDao.getTempRssi(debDao.getMacAddr(ipAddr));
        
        if(clientRssi.size()>=3) {
            Location bestLoc = null;
            double bestLocProbability = -1;
            for (Location loc : hibDao.getLocations()) {
                //Liste des RSSISample pour une position donnée, chaque RSSISample étant
                //assossié à un AccessPoint
                List<RssiRecord> rssis = hibDao.getRssiRecord(loc.getId());
        
        
                double currentLocProbability = probability(clientRssi, rssis, DEFAULT_POSITIONING_PRECISION);
                if (bestLocProbability == -1 || currentLocProbability > bestLocProbability) {
                    bestLoc = loc;
                    bestLocProbability = currentLocProbability;
                }
            }
            return bestLoc;
        }
        return null;
    }
    
    private double probability(List<TempRssi> temps, List<RssiRecord> rssis, double precision){
        try {
            double probability = 1;
            for (TempRssi temp : temps) {
                RssiRecord rssi = find(temp.getAp().getMac_addr(), rssis);
                probability*=gauss_probability(temp.getAvg(), temp.getStdDev(), rssi.getAvg(), rssi.getStdDev(), precision);
            }
            return probability;
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            return -1;
        }
    }
    private double gauss_probability(double avg1, double std1, double avg2, double std2, double precision){
        final double
                valDebut = Math.min(avg1-3*std1, avg2-3*std2),
                valFin = Math.max(avg1+3*std1, avg2+3*std2);
        double
                rectDebut = valDebut,
                rectFin = valDebut+precision,
                probabilite = 0;
        
        do{
            probabilite+=Math.min( gauss(avg1, std1, (rectDebut+rectFin)/2), gauss(avg2, std2, (rectDebut+rectFin)/2));
            rectDebut = rectFin;
            rectFin+=precision;
        }while(rectFin<valFin);
        
        return probabilite;
    }
    private double gauss (final double avg, final double std, final double x) {
        return (1/(std*Math.sqrt(2*Math.PI)))*Math.exp(-(Math.pow(x-avg, 2)/(2*Math.pow(std, 2))));
    }
    private RssiRecord find(String macAddr, List<RssiRecord> rrs){
        final List<RssiRecord> results = new ArrayList<>();
        rrs.stream().filter(rr->rr.getAp().getMac_addr().equals(macAddr)).forEach(rr->results.add(rr));
        
        switch(results.size()){
            case 0:
                throw new IllegalArgumentException("No matching RssiRecord for mac address <"+macAddr+">");
            case 1:
                return results.get(0);
            default :
                throw new IllegalArgumentException("Several("+results.size()+") matching RssiRecord for mac address <"+macAddr+">");
        }
    }
    
    public String getMacAddr (final String clientIpAddr) {
        return debDao.getMacAddr(clientIpAddr);
    }
}
