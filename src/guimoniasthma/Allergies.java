/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASW
 */
public class Allergies {
    
    private Integer alId;
    private int alBirkvalue;
    private int alSagebrushvalue;
    private int alElmvalue;
    private int alElvalue;
    private int alGrassvalue;
    private Date alDate;
    private String alComment;

    public Allergies(int alBirkvalue, int alSagebrushvalue, int alElmvalue, int alElvalue, int alGrassvalue, Date alDate, String alComment) {
        this();
        this.alBirkvalue = alBirkvalue;
        this.alSagebrushvalue = alSagebrushvalue;
        this.alElmvalue = alElmvalue;
        this.alElvalue = alElvalue;
        this.alGrassvalue = alGrassvalue;
        this.alDate = alDate;
        this.alComment = alComment;
    }
    
    
    private SimpleDateFormat dateFormat;
    
    public Allergies(){
        dateFormat = new SimpleDateFormat("dd/MM");
    }

    public Integer getAlId() {
        return alId;
    }

    public void setAlId(Integer alId) {
        this.alId = alId;
    }

    public int getAlBirkvalue() {
        return alBirkvalue;
    }

    public void setAlBirkvalue(int alBirkvalue) {
        this.alBirkvalue = alBirkvalue;
    }

    public int getAlSagebrushvalue() {
        return alSagebrushvalue;
    }

    public void setAlSagebrushvalue(int alSagebrushvalue) {
        this.alSagebrushvalue = alSagebrushvalue;
    }

    public int getAlElmvalue() {
        return alElmvalue;
    }

    public void setAlElmvalue(int alElmvalue) {
        this.alElmvalue = alElmvalue;
    }

    public int getAlElvalue() {
        return alElvalue;
    }

    public void setAlElvalue(int alElvalue) {
        this.alElvalue = alElvalue;
    }

    public int getAlGrassvalue() {
        return alGrassvalue;
    }

    public void setAlGrassvalue(int alGrassvalue) {
        this.alGrassvalue = alGrassvalue;
    }

    public String getAlDate() {
        return dateFormat.format(alDate); 
    }

    public void setAlDate(Date alDate) {
        this.alDate = alDate;
    }
    
    public Date getAlDateObject() {
        return this.alDate;
    }

    public String getAlComment() {
        return alComment;
    }

    public void setAlComment(String alComment) {
        this.alComment = alComment;
    }
    
    
        @Override
    public String toString() {
        return "com.samples.entity.Allergies[ alId=" + alId + " ]";
    }
}
