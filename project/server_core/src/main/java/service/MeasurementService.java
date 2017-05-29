package service;

import dao.HibernateDao;
import repository.AccessPoint;
import repository.TempRssi;

import java.util.List;

/**
 * Created by Guillaume on 29/05/2017.
 */
public class MeasurementService {
    
    private HibernateDao dao;
    
    public MeasurementService(){
        dao = new HibernateDao();
    }
    
    public void registerMeasurement(String apMacAddr, String clientMacAddr, double avg, double stdDev){
        List<AccessPoint> aps = dao.getAccessPoints(apMacAddr);
        
        if(aps.size()>0){
            dao.saveTempRssi(new TempRssi(aps.get(0), clientMacAddr, avg, stdDev));
        }
    }
}
