package com.utbm.lo53.repository;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Guillaume on 09/05/2017.
 */
@Entity
@Table(name = "LOCATION")
public class Location implements Serializable {
    @Id @GeneratedValue
    private Integer id;
    private Double x, y;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Mape mape;
    
    public Location () {
    }
    
    public Integer getId(){return id;}
    
    public void setId(Integer id){this.id = id;}
    
    public Double getX () {
        return x;
    }
    
    public void setX (final Double x) {
        this.x = x;
    }
    
    public Double getY () {
        return y;
    }
    
    public void setY (final Double y) {
        this.y = y;
    }
    
    public Mape getMape () {
        return mape;
    }
    
    public void setMape (final Mape mape) {
        this.mape = mape;
    }
}