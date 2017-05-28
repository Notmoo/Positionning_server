import com.utbm.lo53.dao.HibernateDao;
import com.utbm.lo53.repository.AccessPoint;
import com.utbm.lo53.repository.Mape;

/**
 * Created by Guillaume on 09/05/2017.
 */
public class ServerApp {
    public static void main(String args[]){
        HibernateDao dao = new HibernateDao();
        AccessPoint ap = new AccessPoint();
        ap.setMac_addr("aa:aa:aa:aa:aa:aa:aa:aa");
        dao.saveAccessPoint(ap);
        /*
        Mape mape = new Mape();
        dao.saveMap(mape);/**/
    }
}
