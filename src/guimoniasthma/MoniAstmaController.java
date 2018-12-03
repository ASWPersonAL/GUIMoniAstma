/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    }    
    
}
