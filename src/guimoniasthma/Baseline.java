/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;

import java.text.SimpleDateFormat;
import javafx.fxml.FXML;

/**
 *
 * @author ASW
 */
public class Baseline {
    
    
    private Integer baId;
    private int baValue;
    private String baDate;
    
    private SimpleDateFormat dateFormat;
    
    public Baseline() {
    }

    public Baseline(int baValue, String baDate) {
        this.baValue = baValue;
        this.baDate = baDate;
    }

    public Baseline(Integer baId, int baValue, String baDate) {
        //dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        this.baId = baId;
        this.baValue = baValue;
        this.baDate = baDate;
    }

    public Integer getBaId() {
        return baId;
    }

    public void setBaId(Integer baId) {
        this.baId = baId;
    }

    public int getBaValue() {
        return baValue;
    }

    @FXML
    public void setBaValue(int baValue) {
        this.baValue = baValue;
    }

    public String getBaDate() {
        return baDate;
    }

    @FXML
    public void setBaDate(String baDate) {
         
       this.baDate = baDate;
    }
    
      @Override
    public String toString() {
        return "com.samples.entity.Baseline[ baId=" + baId + baValue + baDate + " ]";
    }
    
}
