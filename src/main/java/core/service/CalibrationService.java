package core.service;

import core.dao.HibernateDao;
import core.repository.AccessPoint;
import core.repository.Location;
import core.repository.RssiRecord;

import java.util.List;

/**
 * Created by Guillaume on 09/05/2017.
 */
public class CalibrationService {
    
    private HibernateDao dao;
    
    public CalibrationService(){this.dao = new HibernateDao();}

    public boolean addCalibrationData(int locationId, String apMacAddress, double val){
        Location loc = dao.getLocation(locationId);
        AccessPoint ap = dao.getAccessPoints(apMacAddress).get(0);
        return dao.saveRssiRecord(new RssiRecord(loc, ap, val));
    }
    public int registerLocation(double posx, double posy, int mapId){
        return dao.saveLocation(new Location(posx, posy, dao.getMap(mapId)));
    }
}
