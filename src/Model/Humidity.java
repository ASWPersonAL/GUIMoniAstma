/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASW
 */
public class Humidity implements Serializable {
    
//// Instants fields.    
    
    private Integer huId;
    private int huValue;
    private Date huDate;
    private String huComment;
    
    private SimpleDateFormat dateFormat;
    
    //// Default constructor with initiated local variable dataformat of type SimpleDateFormat.
    
    public Humidity(){
        dateFormat = new SimpleDateFormat("dd/MM");
    }
    
    //// Constructor with 3 local parameters used for POST method. The constructor inherents the default constructor.

    public Humidity(int huValue, Date huDate, String huComment) {
        this();
        this.huValue = huValue;
        this.huDate = huDate;
        this.huComment = huComment;
    }
    
//// Methods. 

    public Integer getHuId() {
        return huId;
    }

    public void setHuId(Integer huId) {
        this.huId = huId;
    }

    public int getHuValue() {
        return huValue;
    }

    public void setHuValue(int huValue) {
        this.huValue = huValue;
    }
    
      //// Get Date object of type string.
    
    public String getHuDate() {
        return dateFormat.format(huDate);

    }

    //// GetDate object of type Date.

    public Date getHuDateObject() {
        return this.huDate;
    }
    
    

    public void setHuDate(Date huDate) {
        this.huDate = huDate;
    }

    public String getHuComment() {
        return huComment;
    }

    public void setHuComment(String huComment) {
        this.huComment = huComment;
    }
    
   @Override
    public String toString() {
        return "com.samples.entity.Humidity[ huId=" + huId + " ]";
    }
    
}
