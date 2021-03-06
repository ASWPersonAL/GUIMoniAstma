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
public class Peakflow implements Serializable {

//// Instant fields.
    
    private Integer pfId;
    public int pfValue;
    private Date pfDate;
    private String pfComment;
    private SimpleDateFormat dateFormat;
    
    ////  //// Default constructor with initiated local variable dataformat of type SimpleDateFormat.
    
    public Peakflow(){
        dateFormat = new SimpleDateFormat("dd/MM");
    }
    
    //// Constructor with 3 local parameters used for POST method. The constructor inherents the default constructor.

    public Peakflow(int pfValue, Date pfDate, String pfComment) {
        this();
        this.pfValue = pfValue;
        this.pfDate = pfDate;
        this.pfComment = pfComment;
    }
    
//// Methods.    
    
    public Integer getPfId() {
        return pfId;
    }

    public void setPfId(Integer pfId) {
        this.pfId = pfId;
    }

    public int getPfValue() {
        return pfValue;
    }

    public void setPfValue(int pfValue) {
        this.pfValue = pfValue;
    }
    
      //// Get Date object of type string.

    public String getPfDate() {
        return dateFormat.format(pfDate);
    }

    //// GetDate object of type Date.  
    
    public Date getPfDateObject() {
        return this.pfDate;
    }
    
  

    public void setPfDate(Date pfDate) {
        this.pfDate = pfDate;
    }
    
 

    public String getPfComment() {
        return pfComment;
    }

    public void setPfComment(String pfComment) {
        this.pfComment = pfComment;
    }
   
    
      @Override
    public String toString() {
        return "com.samples.entity.Peakflow[ pfId=" + pfId + " " + "pfValue: " + pfValue + " " + "pfDate: " + pfDate + " " + "pfComment: " + pfComment + " " +" ]";
    }
    
}
