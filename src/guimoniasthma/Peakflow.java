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
    
    private Integer pfMeasureid;
    private int pfValue;
    
    
    //Skal bruge en data konverter til visning af date i liste og derefter på graf.
    private Date pfDate;
    private String pfComment;
    private SimpleDateFormat dateFormat;
    
    
    public Peakflow(){
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public Integer getPfMeasureid() {
        return pfMeasureid;
    }

    public void setPfMeasureid(Integer pfMeasureid) {
        this.pfMeasureid = pfMeasureid;
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

    public String getPfComment() {
        return pfComment;
    }

    public void setPfComment(String pfComment) {
        this.pfComment = pfComment;
    }
    
    
      @Override
    public String toString() {
        return "com.samples.entity.Peakflow[ pfMeasureid=" + pfMeasureid + " ]";
    }
    
}
