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
public class Humidity implements Serializable {
    
    private Integer hId;
    private int hValue;
    private Date hDate;
    private String hComment;
    
    private SimpleDateFormat dateFormat;
    
    public Humidity(){
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public Integer gethId() {
        return hId;
    }

    public void sethId(Integer hId) {
        this.hId = hId;
    }

    public int gethValue() {
        return hValue;
    }

    public void sethValue(int hValue) {
        this.hValue = hValue;
    }

    public String gethDate() {
        return dateFormat.format(hDate);

    }

    public void sethDate(Date hDate) {
        this.hDate = hDate;
    }

    public String gethComment() {
        return hComment;
    }

    public void sethComment(String hComment) {
        this.hComment = hComment;
    }
    
   @Override
    public String toString() {
        return "com.samples.entity.Humidity[ hId=" + hId + " ]";
    }
    
}
