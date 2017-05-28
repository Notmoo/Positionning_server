package com.utbm.lo53.repository;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Guillaume on 09/05/2017.
 */
@Entity
@Table (name = "RSSI_RECORD")
public class RssiRecord implements Serializable {
    @Id @GeneratedValue
    private Integer id;
    
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Location loc;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private AccessPoint ap;
    
    private Double avg;
    private Double stdDev;
    
    public RssiRecord () {
    }
    
    public RssiRecord(Location loc, AccessPoint ap, Double avg, Double stdDev){
        id = null;
        this.loc = loc;
        this.ap = ap;
        this.avg = avg;
        this.stdDev = stdDev;
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
