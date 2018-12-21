/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.control.Alert;

/**
 *
 * @author ASW
 */
public class Alerts {    

    
        public void getNoServerConError(){
            
              Alert alertConError = new Alert(Alert.AlertType.ERROR);
         alertConError.setTitle("Connection to server is refused");
         alertConError.setHeaderText("Is the server connected?");
              String s = "Probem with server connection. Please ensure that the connection to the server is established and start the program again. ";
               alertConError.setContentText(s);
               alertConError.showAndWait();
        }

    
        public void getPostAlertError(){
             String sHeader = "Input missing or format is wrong ";
             String sContext = "Empty input or the format of the input value is wrong. Value must be a rounded number.!";
             
             Alert alertPostError = new Alert(Alert.AlertType.ERROR);
             alertPostError.setHeaderText(sHeader);
             alertPostError.setContentText(sContext);
             alertPostError.showAndWait();
            
         }
        public void getPostPfSuccesInfo(){
             String s = "Please ensure to input humidity and pollen data for this date before clicking update charts!. ";
             String sh = "The peakflow input has been saved! ";
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setHeaderText(sh);
             alert.setContentText(s);
             alert.showAndWait();
        }
        
        public void getPostHuSuccesInfo(){
            String s =  "Please ensure to also input pollen data on this date before clicking update charts!. ";
            String sh = "The humidity input has been saved! ";
            Alert alertPfSucces = new Alert(Alert.AlertType.INFORMATION);
            alertPfSucces.setHeaderText(sh);
            alertPfSucces.setContentText(s);
            alertPfSucces.showAndWait();
        }
        
        public void getPostAlSuccesInfo(){
        
            String s = "Please ensure that you have made inputs for peakflow and pollen on this date before clicking update charts!. ";
            String sh = "The pollen input has been saved! ";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(sh);
            alert.setContentText(s);
            alert.showAndWait();
    
        }
        
        
        
}
