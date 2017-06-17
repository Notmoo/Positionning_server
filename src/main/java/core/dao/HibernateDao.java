package core.dao;

import core.repository.*;
import core.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;



/**
 * Dao class handling access to database's data using Hibernate
 *
 * Created by Guillaume on 09/05/2017.
 */
public class HibernateDao {
    
    public HibernateDao () {
    }

    /**
     * Execute a ITransactionProcess containing an Hibernate transaction,
     * and return a callback object containing a list of results filled by
     * the transactioin process and a boolean status.
     *
     * Handle properly Hibernate session's opening and closing, and handle exceptions that can occur.
     * @param itp transaction process to run
     * @return {@link TransactionCallBack}
     */
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

    /**
     * Generic method that return a list of objects that
     * are contained in the database handled by Hibernate.
     *
     * @param clazz class of elements requests
     *
     * @return a list of objects of class {@code clazz}
     */
    private <T extends IJavaBean> List<T> internal_getData(Class<T> clazz){
        TransactionCallBack callBack = execTransactionProcess((session)->{
            TransactionCallBack reply = new TransactionCallBack<IJavaBean>();
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

    /**
     * Generic method that push javabeans to the database using Hibernate.
     * @param objs
     * @return status, true if the transaction is successful, false otherwise
     */
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

    /**
     *
     * @param trs
     * @return
     */
    public boolean saveTempRssi(TempRssi... trs){
        return internal_saveData(trs);
    }

    /**
     *
     * @param macAddr
     * @return
     */
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

    /**
     *
     * @param rssis
     * @return
     */
    public boolean saveRssiRecord(RssiRecord... rssis){
        return internal_saveData(rssis);
    }
    /**
     *
     * @return
     */
    public List<RssiRecord> getRssiRecord(){
        return internal_getData(RssiRecord.class);
    }

    /**
     *
     * @param locationID
     * @return
     */
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

    /**
     *
     * @return
     */
    public List<Location> getLocations(){
        return internal_getData(Location.class);
    }

    /**
     *
     * @param aps
     * @return
     */
    public boolean saveAccessPoint (final AccessPoint... aps) {
        return internal_saveData(aps);
    }

    /**
     *
     * @return
     */
    public List<AccessPoint> getAccessPoint () {
       return internal_getData(AccessPoint.class);
    }

    /**
     *
     * @param map
     * @return
     */
    public boolean saveMap(final Map map){return internal_saveData(map);}

    /**
     *
     * @param mapId
     * @return
     */
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

    /**
     *
     * @param apMacAddress
     * @return
     */
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

    /**
     *
     * @param location
     * @return
     */
    public int saveLocation(Location location) {
        TransactionCallBack callBack = execTransactionProcess((session)->{
            TransactionCallBack<Integer> reply = new TransactionCallBack<Integer>();
            Integer id = (Integer) session.save(location);
            if(id!=null)
                reply.results.add(id);
            return reply;
        });

        if(callBack!=null && callBack.results.size()>0)
            return (Integer)callBack.results.get(0);
        return -1;
    }

    /**
     *
     * @param Location
     * @return
     */
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

    /**
     * This functional interface's purpose is to define a common
     * type to give process to internal methods.
     */
    private interface ITransactionProcess{
        TransactionCallBack exec(Session tr);
    }

    /**
     *  Class designed to contains results from transaction process.
     *
     * @param <T>
     */
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
