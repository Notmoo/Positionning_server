package repository;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Guillaume on 09/05/2017.
 */
@Entity
@Table(name = "MAP")
public class Map implements Serializable{
    @Id @GeneratedValue
    private int id;
    private Double x_topLeft, y_topLeft;
    private Byte[] content;
    private Double x_bottomRight, y_bottomRight;
     
    public Map() {
    }
    
    public Integer getId () {
        return id;
    }
    
    public void setId (final Integer id) {
        this.id = id;
    }
    
    public Double getX_topLeft () {
        return x_topLeft;
    }
    
    public void setX_topLeft (final Double x_topLeft) {
        this.x_topLeft = x_topLeft;
    }
    
    public Double getY_topLeft () {
        return y_topLeft;
    }
    
    public void setY_topLeft (final Double y_topLeft) {
        this.y_topLeft = y_topLeft;
    }
    
    public Byte[] getContent () {
        return content;
    }
    
    public void setContent (final Byte[] content) {
        this.content = content;
    }
    
    public Double getX_bottomRight () {
        return x_bottomRight;
    }
    
    public void setX_bottomRight (final Double x_bottomRight) {
        this.x_bottomRight = x_bottomRight;
    }
    
    public Double getY_bottomRight () {
        return y_bottomRight;
    }
    
    public void setY_bottomRight (final Double y_bottomRight) {
        this.y_bottomRight = y_bottomRight;
    }
}
