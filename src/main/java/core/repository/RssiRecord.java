package core.repository;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Guillaume on 09/05/2017.
 */
@Entity
@Table (name = "RSSI_RECORD")
public class RssiRecord implements IJavaBean {
    @Id @GeneratedValue
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Location loc;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private AccessPoint ap;
    
    private Double val;
    
    public RssiRecord () {
    }
    
    public RssiRecord(Location loc, AccessPoint ap, Double val){
        this.loc = loc;
        this.ap = ap;
        this.val = val;
    }
    
    public Location getLoc () {
        return loc;
    }
    
    public void setLoc (final Location loc) {
        this.loc = loc;
    }
    
    public AccessPoint getAp () {
        return ap;
    }
    
    public void setAp (final AccessPoint ap) {
        this.ap = ap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }
}
