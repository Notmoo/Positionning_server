package core.repository;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Guillaume on 09/05/2017.
 */
@Entity
@Table(name = "LOCATION")
public class Location implements IJavaBean {
    @Id @GeneratedValue
    private int id;
    private Double x, y;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Map map;
    
    public Location () {
    }

    public Location(double posx, double posy, Map map) {
        this.x = posx;
        this.y = posy;
        this.map = map;
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
    
    public Map getMap() {
        return map;
    }
    
    public void setMap(final Map map) {
        this.map = map;
    }
}
