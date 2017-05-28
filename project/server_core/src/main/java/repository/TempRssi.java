package repository;

import javax.persistence.*;

/**
 * Created by Guillaume on 09/05/2017.
 */
@Entity
@Table (name = "TEMP_RSSI")
public class TempRssi {
    @Id @GeneratedValue
    private int id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private AccessPoint ap;
    
    private String client_mac_addr;
    private Double avg, stdDev;
    
    public TempRssi () {}
    
    public AccessPoint getAp () {
        return ap;
    }
    
    public void setAp (final AccessPoint ap) {
        this.ap = ap;
    }
    
    public String getClient_mac_addr () {
        return client_mac_addr;
    }
    
    public void setClient_mac_addr (final String client_mac_addr) {
        this.client_mac_addr = client_mac_addr;
    }
    
    public Double getAvg () {
        return avg;
    }
    
    public void setAvg (final Double avg) {
        this.avg = avg;
    }
    
    public Double getStdDev () {
        return stdDev;
    }
    
    public void setStdDev (final Double stdDev) {
        this.stdDev = stdDev;
    }
}
