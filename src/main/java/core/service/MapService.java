package core.service;

import core.dao.HibernateDao;
import core.repository.Map;

/**
 * MapService is a service class providing methods to access map data
 * stored in the database according to a map id.
 *
 * Created by Guillaume on 29/05/2017.
 */
public class MapService {
    
    private HibernateDao dao;
    
    public MapService(){
        dao = new HibernateDao();
    }
    
    public Byte[] getMapContent(int mapId){
        return dao.getMap(mapId).getContent();
    }
    
    public Map getMap(int mapId){
        return dao.getMap(mapId);
    }
}
