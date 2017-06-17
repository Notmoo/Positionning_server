package core.repository;

import javax.persistence.*;

/**
 * Created by Guillaume on 09/05/2017.
 */
@Entity
@Table (name = "TEMP_RSSI")
public class TempRssi implements IJavaBean{
    @Id @GeneratedValue
    private int id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private AccessPoint ap;
    
    private String client_mac_addr;
    private Double val;
    
    public TempRssi () {}
    
    public TempRssi (final AccessPoint accessPoint, final String clientMacAddr, final double val) {
        ap = accessPoint;
        client_mac_addr = clientMacAddr;
        this.val = val;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }
}
