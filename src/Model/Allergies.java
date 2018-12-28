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
public class Allergies implements Serializable {
    
//// Instans fields.
    
    private Integer alId;
    private int alBirkvalue;
    private int alSagebrushvalue;
    private int alElmvalue;
    private int alElvalue;
    private int alGrassvalue;
    private Date alDate;
    private String alComment;
    
    private SimpleDateFormat dateFormat;
    
    //// Default constructor with initiated local variable dataformat of type SimpleDateFormat.
    
    public Allergies(){
        dateFormat = new SimpleDateFormat("dd/MM");
    }
       
    //// Constructor with 7 local parameters. Used for POST method.The constructor inherents the default construtor. 

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
    
//// Methods.

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

    //// GetDate object of type Date.
    
    public Date getAlDate() {
        return this.alDate;
    }
    
    //// Get Date object of type string. 
    
    public String getAlDateAsString() {
        return dateFormat.format(alDate); 
    }

    public void setAlDate(Date alDate) {
        this.alDate = alDate;
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
