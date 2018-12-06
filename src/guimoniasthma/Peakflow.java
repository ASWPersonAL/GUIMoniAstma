/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ASW
 */
public class Peakflow implements Serializable {
    
    private Integer pfMeasureid;
    private int pfValue;
    
    private Date pfDate;
    private String pfComment;
    
    
    public Peakflow(){
        
    }
    
    public Peakflow(Date pfdate, int pfvalue){
        this.pfDate = pfdate;
        this.pfValue = pfvalue;
        
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

    public Date getPfDate() {
        return pfDate;
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
    
    
    
}
