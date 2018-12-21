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
        
        //// Instans of Alert types ERROR and INFORMATION.
        
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        
        //// Alert boxes methods.
    
        public void getNoServerConError(){
            String st = "Connection to server is refused";
            String sh = "Is the server connected?";
            String s = "Probem with server connection. Please ensure that the connection to the server is established and start the program again. ";
            alertError.setTitle(st);
            alertError.setHeaderText(sh);
            alertError.setContentText(s);
            alertError.showAndWait();
        }

        public void getPostAlertError(){
            String sh = "Input missing or format is wrong ";
            String s = "Empty input or the format of the input value is wrong. Value must be a rounded number.!";
            alertError.setHeaderText(sh);
            alertError.setContentText(s);
            alertError.showAndWait();
         }
        
        public void getPostPfSuccesInfo(){
            String sh = "The peakflow input has been saved! ";
            String s = "Please ensure to input humidity and pollen data for this date before clicking update charts!. ";
            alertInfo.setHeaderText(sh);
            alertInfo.setContentText(s);
            alertInfo.showAndWait();
        }
        
        public void getPostHuSuccesInfo(){
            String sh = "The humidity input has been saved! ";
            String s =  "Please ensure to also input pollen data on this date before clicking update charts!. ";
            alertInfo.setHeaderText(sh);
            alertInfo.setContentText(s);
            alertInfo.showAndWait();
        }
        
        public void getPostAlSuccesInfo(){
            String sh = "The pollen input has been saved! ";
            String s = "Please ensure that you have made inputs for peakflow and pollen on this date before clicking update charts!. ";
            alertInfo.setHeaderText(sh);
            alertInfo.setContentText(s);
            alertInfo.showAndWait();
    
        }
        
        
        
}
