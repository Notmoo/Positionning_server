package service;

import dao.HibernateDao;
import repository.Map;

/**
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
