package repository;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Created by Guillaume on 09/05/2017.
 */
@Entity
@Table(name ="ACCESS_POINT")
public class AccessPoint implements Serializable{
    @Id @GeneratedValue
    private Integer id;
    
    private String mac_addr;
    
    public AccessPoint () {
    }
    
    public AccessPoint(Integer id, String mac_addr){
        this.mac_addr = mac_addr;
    }
    
    public Integer getId(){return id;}
    
    public void setId(Integer id){this.id = id;}
    
    public String getMac_addr () {
        return mac_addr;
    }
    
    public void setMac_addr (final String mac_addr) {
        this.mac_addr = mac_addr;
    }
}
