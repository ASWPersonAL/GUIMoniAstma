/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author ASW
 */
public class MoniAstmaController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private TextField textFieldSearch;
    
    @FXML
    private Button buttonSearch;
    
    @FXML
    private TableView<Peakflow> tableView; 
    
    
    //Line chart
    
    
     @FXML
    final CategoryAxis xAxisPF = new CategoryAxis();
    
    @FXML
    final NumberAxis yAxisPF = new NumberAxis();
    
    @FXML 
    LineChart<String, Number> chartPFdata; 
    
    
    //Methods
    
    @FXML
   private void handleSearchAction(){
       WebTarget clientTarget;
       ObservableList<Peakflow> data = tableView.getItems();
       data.clear();
       Client client = ClientBuilder.newClient();
       client.register(PeakflowMessageBodyReader.class);
       if(textFieldSearch.getText().length() > 0){
           clientTarget = client.target("http://localhost:8080/ServerSideMoniAsthma/webresources/peakflow/search/{beginBy}");
           clientTarget = clientTarget.resolveTemplate("beginBy", textFieldSearch.getText());
       }else{
           clientTarget = client.target("http://localhost:8080/ServerSideMoniAsthma/webresources/peakflow");
       }
       GenericType<List<Peakflow>> listpf = new GenericType<List<Peakflow>>() {
            };
       List<Peakflow> peakflows = clientTarget.request("application/json").get(listpf);
       
       for(Peakflow p : peakflows){
           data.add(p);
           System.out.println(p.toString());
       }
       
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleSearchAction();
        
//        WebTarget clientTarget;
//        
//        Client client = ClientBuilder.newClient();
//        
//        clientTarget = client.target("http://localhost:8080/ServerSideMoniAsthma/webresources/peakflow");
//        

        
        
             Connection con1;
        try{
            con1 = DriverManager.getConnection("jdbc:derby://localhost:1527/fifi", "fifi", "fifi");
            Statement stmt = con1.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM FIFI.PEAKFLOW");
            //FETCH FIRST 3 ROWS ONLY");
            
            LineChart.Series<String,Number> series10 = new LineChart.Series<String,Number>();
            while(rs.next())
            {
                
                series10.getData().add(new XYChart.Data<String,Number>(rs.getDate(3).toString(),rs.getInt(2)));
                
            } 

             chartPFdata.getData().add(series10);
          
        }
        catch (SQLException ex){
            Logger.getLogger(GUIMoniAsthma.class.getName()).log(Level.SEVERE, null, ex);
        }
//        
        
    }    
    
}
