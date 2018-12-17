/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASW
 */
public class Peakflow implements Serializable {
    
    private Integer pfId;
    public int pfValue;
    
    
    //Skal bruge en data konverter til visning af date i liste og derefter på graf.
    private Date pfDate;
    private String pfComment;
    private SimpleDateFormat dateFormat;

    public Peakflow(int pfValue, Date pfDate, String pfComment) {
        this();
        this.pfValue = pfValue;
        this.pfDate = pfDate;
        this.pfComment = pfComment;
    }
    
    
    public Peakflow(){
        dateFormat = new SimpleDateFormat("dd/MM");
    }

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

    public String getPfDate() {
        return dateFormat.format(pfDate);
    }

    public void setPfDate(Date pfDate) {
        this.pfDate = pfDate;
    }
    
    public Date getPfDateObject() {
        return this.pfDate;
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
