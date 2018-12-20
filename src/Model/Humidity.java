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
    
    
    
    private Integer huId;
    private int huValue;
    private Date huDate;
    private String huComment;
    
    

    public Humidity(int huValue, Date huDate, String huComment) {
        this();
        this.huValue = huValue;
        this.huDate = huDate;
        this.huComment = huComment;
    }
    
    
    private SimpleDateFormat dateFormat;
    
    public Humidity(){
        dateFormat = new SimpleDateFormat("dd/MM");
    }

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

     public String getHuDate() {
        return dateFormat.format(huDate);

    }

    public void setHuDate(Date huDate) {
        this.huDate = huDate;
    }
    
      public Date getHuDateObject() {
        return this.huDate;
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
