package com.utbm.lo53.service;

import com.utbm.lo53.dao.HibernateDao;
import com.utbm.lo53.repository.AccessPoint;
import com.utbm.lo53.repository.RssiRecord;

import java.util.List;

/**
 * Created by Guillaume on 09/05/2017.
 */
public class CalibrationService {
    
    private HibernateDao dao;
    
    public CalibrationService(){this.dao = new HibernateDao();}
    public CalibrationService(HibernateDao dao){this.dao = dao;}
    
    public boolean registerCalibrationData(RssiRecord... records){
        return dao.saveRssiRecord(records);
    }
    public boolean registerAp(AccessPoint ap){
        return dao.saveAccessPoint(ap);
    }
    public List<AccessPoint> getAllAccessPoints(){return dao.getAccessPoint();}
}
