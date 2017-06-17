package core.service;

import core.dao.HibernateDao;
import core.repository.AccessPoint;
import core.repository.TempRssi;

import java.util.List;

/**
 * MesaurementService is a service class providing methods to register positioning data.
 * One shouldn't use this service to registrer calibration data, as a dedicated service class
 * has been made to handle this kind of registrations.
 *
 * Created by Guillaume on 29/05/2017.
 */
public class MeasurementService {
    
    private HibernateDao dao;
    
    public MeasurementService(){
        dao = new HibernateDao();
    }

    /**
     * Register into the database a measured positioning data.
     * @param apMacAddr mac address of access point that measured the data
     * @param clientMacAddr mac address of client that has been use as base for measurement
     * @param val value measured
     */
    public void registerMeasurement(String apMacAddr, String clientMacAddr, double val){
        List<AccessPoint> aps = dao.getAccessPoints(apMacAddr);
        
        if(aps.size()>0){
            dao.saveTempRssi(new TempRssi(aps.get(0), clientMacAddr, val));
        }
    }
}
