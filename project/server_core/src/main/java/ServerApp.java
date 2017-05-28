import dao.HibernateDao;
import repository.AccessPoint;
import repository.Map;

/**
 * Created by Guillaume on 09/05/2017.
 */
public class ServerApp {
    public static void main(String args[]){
        HibernateDao dao = new HibernateDao();
        AccessPoint ap = new AccessPoint();
        ap.setMac_addr("aa:aa:aa:aa:aa:aa:aa:aa");
        dao.saveAccessPoint(ap);
        
        Map map = new Map();
        dao.saveMap(map);/**/
    }
}
