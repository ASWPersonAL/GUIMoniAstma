/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author ASW
 */
public class GUIMoniAsthma extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("MoniAstma.fxml"));
        
        stage.setTitle("Asthmatical");
        stage.setWidth(1300);
        stage.setHeight(1300);
        
        Scene scene = new Scene(root);
       
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        stage.show();
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
