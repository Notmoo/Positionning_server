package core.dao;

import core.repository.*;
import core.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Guillaume on 09/05/2017.
 */
public class HibernateDao {
    
    public HibernateDao () {
    }
    
    private TransactionCallBack execTransactionProcess(ITransactionProcess itp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        TransactionCallBack reply = new TransactionCallBack();
        try {
            session.beginTransaction();
            reply = itp.exec(session);
            session.getTransaction().commit();
            reply.setStatus(true);
            return reply;
        } catch (HibernateException he) {
            he.printStackTrace();
            if (session.getTransaction() != null) {
                try {
                    session.getTransaction().rollback();
                } catch (HibernateException he2) {
                    he2.printStackTrace();
                }
            }
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return reply;
    }
    private <T> List<T> internal_getData(Class<T> clazz){
        TransactionCallBack callBack = execTransactionProcess((session)->{
            TransactionCallBack reply = new TransactionCallBack<T>();
            Query query = session.createQuery("from "+clazz.getSimpleName());
            List<Object> results = query.list();
            for(Object result : results){
                if(clazz.isInstance(result))
                    reply.getResults().add(result);
            }
            return reply;
        });
        return callBack.getResults();
    }
    private boolean internal_saveData(Object... objs){
        TransactionCallBack callBack = execTransactionProcess((session)->{
            TransactionCallBack reply = new TransactionCallBack();
            for(Object obj : objs){
                session.persist(obj);
            }
            return reply;
        });
        
        return callBack!=null && callBack.isStatus();
    }
    
    public boolean saveTempRssi(TempRssi... trs){
        return internal_saveData(trs);
    }
    public List<TempRssi> getTempRssi(String macAddr){
        if(macAddr == null)
            return new ArrayList<>();
        
        TransactionCallBack callBack = execTransactionProcess((session)->{
            TransactionCallBack<TempRssi> reply = new TransactionCallBack<TempRssi>();
            Query query = session.createQuery("from TempRssi tr where tr.client_mac_addr = :mac_addr");
            query.setParameter("mac_addr", macAddr);
            List<Object> results = query.list();
            for(Object result : results){
                if(result instanceof TempRssi)
                    reply.getResults().add((TempRssi)result);
            }
            return reply;
        });
        return callBack.getResults();
    }
    
    public boolean saveRssiRecord(RssiRecord... rssis){
        return internal_saveData(rssis);
    }
    public List<RssiRecord> getRssiRecord(){
        return internal_getData(RssiRecord.class);
    }
    public List<RssiRecord> getRssiRecord(Integer locationID){
        if(locationID == null)
            return getRssiRecord();
        
        TransactionCallBack callBack = execTransactionProcess((session)->{
            TransactionCallBack reply = new TransactionCallBack<RssiRecord>();
            Query query = session.createQuery("from RssiRecord rr where rr.id = :id");
            query.setParameter("id", locationID);
            List<Object> results = query.list();
            for(Object result : results){
                if(result instanceof RssiRecord)
                    reply.getResults().add(result);
            }
            return reply;
        });
        return callBack.getResults();
    }
    
    public List<Location> getLocations(){
        return internal_getData(Location.class);
    }
    
    public boolean saveAccessPoint (final AccessPoint... aps) {
        return internal_saveData(aps);
    }
    public List<AccessPoint> getAccessPoint () {
       return internal_getData(AccessPoint.class);
    }
    
    public boolean saveMap(final Map map){return internal_saveData(map);}
    public Map getMap(int mapId){
        TransactionCallBack callBack = execTransactionProcess((session)->{
            TransactionCallBack reply = new TransactionCallBack<Map>();
            Query query = session.createQuery("from Map m where m.id = :id");
            query.setParameter("id", mapId);
            List<Object> results = query.list();
            for(Object result : results){
                if(result instanceof Map)
                    reply.getResults().add(result);
            }
            return reply;
        });
        return (callBack.getResults().isEmpty()?null:(Map)callBack.getResults().get(0));
    }

    public List<AccessPoint> getAccessPoints(String apMacAddress) {
        TransactionCallBack callBack = execTransactionProcess((session)->{
            TransactionCallBack reply = new TransactionCallBack<AccessPoint>();
            Query query = session.createQuery("from AccessPoint ap where ap.mac_addr = :addr");
            query.setParameter("addr", apMacAddress);
            List<Object> results = query.list();
            for(Object result : results){
                if(result instanceof AccessPoint)
                    reply.getResults().add(result);
            }
            return reply;
        });

        List<AccessPoint> reply = new ArrayList<>();
        callBack.getResults().forEach(result->reply.add((AccessPoint)result));
        return reply;
    }

    public Location getLocation(int Location) {
        TransactionCallBack callBack = execTransactionProcess((session)->{
            TransactionCallBack reply = new TransactionCallBack<Location>();
            Query query = session.createQuery("from Location loc where loc.id = :id");
            query.setParameter("id", Location);
            List<Object> results = query.list();
            for(Object result : results){
                if(result instanceof Location)
                    reply.getResults().add(result);
            }
            return reply;
        });
        return (callBack.getResults().isEmpty()?null:(Location)callBack.getResults().get(0));
    }

    private interface ITransactionProcess{
        TransactionCallBack exec(Session tr);
    }
    
    private class TransactionCallBack<T>{
        private boolean status;
        private List<T> results;
        
        TransactionCallBack () {
            status = false;
            results = new ArrayList<T>();
        }
        
        boolean isStatus () {
            return status;
        }
        
        void setStatus (final boolean status) {
            this.status = status;
        }
        
        List<T> getResults () {
            return results;
        }
        
        void setResults (final List<T> results) {
            this.results = results;
        }
    }
}
