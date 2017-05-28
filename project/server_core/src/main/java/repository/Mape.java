package repository;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Guillaume on 09/05/2017.
 */
@Entity
@Table(name = "MAPE")
public class Mape implements Serializable{
    @Id @GeneratedValue
    private Integer id;
    private Integer px_width, px_height;
    private Byte[] content;
    private String desc;
    private Double m_width, m_height;
     
    public Mape () {
    }
    
    public Integer getId () {
        return id;
    }
    
    public void setId (final Integer id) {
        this.id = id;
    }
    
    public Integer getPx_width () {
        return px_width;
    }
    
    public void setPx_width (final Integer px_width) {
        this.px_width = px_width;
    }
    
    public Integer getPx_height () {
        return px_height;
    }
    
    public void setPx_height (final Integer px_height) {
        this.px_height = px_height;
    }
    
    public Byte[] getContent () {
        return content;
    }
    
    public void setContent (final Byte[] content) {
        this.content = content;
    }
    
    public String getDesc () {
        return desc;
    }
    
    public void setDesc (final String desc) {
        this.desc = desc;
    }
    
    public Double getM_width () {
        return m_width;
    }
    
    public void setM_width (final Double m_width) {
        this.m_width = m_width;
    }
    
    public Double getM_height () {
        return m_height;
    }
    
    public void setM_height (final Double m_height) {
        this.m_height = m_height;
    }
}
