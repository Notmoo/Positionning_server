package service;

import dao.HibernateDao;
import repository.AccessPoint;
import repository.Location;
import repository.RssiRecord;

import java.util.List;

/**
 * Created by Guillaume on 09/05/2017.
 */
public class CalibrationService {
    
    private HibernateDao dao;
    
    public CalibrationService(){this.dao = new HibernateDao();}
    public CalibrationService(HibernateDao dao){this.dao = dao;}

    public boolean addCalibrationData(int locationId, String apMacAddress, double avg, double stdDev){
        Location loc = dao.getLocation(locationId);
        AccessPoint ap = dao.getAccessPoints(apMacAddress).get(0);
        return dao.saveRssiRecord(new RssiRecord(loc, ap, avg, stdDev));
    }
    public boolean registerCalibrationData(RssiRecord... records){
        return dao.saveRssiRecord(records);
    }
    public boolean registerAp(AccessPoint ap){
        return dao.saveAccessPoint(ap);
    }
    public List<AccessPoint> getAllAccessPoints(){return dao.getAccessPoint();}
}
