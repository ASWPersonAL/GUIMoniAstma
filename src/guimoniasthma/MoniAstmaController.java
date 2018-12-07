/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
    
    
//    //Line chart with local db data Select from syntax
//    
//    
//     @FXML
//    final CategoryAxis xAxisPF = new CategoryAxis();
//    
//    @FXML
//    final NumberAxis yAxisPF = new NumberAxis();
//    
//    @FXML 
//    private LineChart<String, Number> chartPFdata; 
    
    //linechart data def with webservice.
    
       @FXML
    CategoryAxis xAxis = new CategoryAxis();
    
    @FXML
    NumberAxis yAxis = new NumberAxis();
    
  
    @FXML
    private LineChart<String,Number> chart;
   
    
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
  
   
   
   
   
     private void GetLineChartData(){
         
         ////API url client
         WebTarget clientTarget;
         
         ////http clienten
         Client client = ClientBuilder.newClient();
         
         client.register(PeakflowMessageBodyReader.class);

         ////her sættes clienten med url metode.
         clientTarget = client.target("http://localhost:8080/ServerSideMoniAsthma/webresources/peakflow");
         
            ////Erklære en liste med Peakflow objecter.
            GenericType<List<Peakflow>> list = new GenericType<List<Peakflow>>() {};
             
            ////Får en liste med Json objekter
            List<Peakflow> peakflows = clientTarget.request("application/json").get(list);  
            
            ////Liste deklaration til chart i xml.
            ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
       
            ////Erklære en serie til chart data
            LineChart.Series<String,Number> series30 = new LineChart.Series<String,Number>();
            
             ////Foreacher over listen med json objekter?
             for(Peakflow p : peakflows){
          
                 series30.getData().add(new XYChart.Data<String,Number>(p.getPfComment(), p.getPfValue()));
                 
                 System.out.println(p.getPfComment());
                 System.out.println(p.getPfValue());
                }
             
              ////Tilføjer serie til observableList
                  lineChartData.add(series30);
  
      ////Sætter observable list in i grafen til xml view.
      chart.setData(lineChartData);
      //chart.getData().add(lineChartData);
      chart.createSymbolsProperty();
 
   }
   
//   private void GetLineChartDataDB(){
//    
//       Connection con1;
//        try{
//            con1 = DriverManager.getConnection("jdbc:derby://localhost:1527/fifi", "fifi", "fifi");
//            Statement stmt = con1.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM FIFI.PEAKFLOW");
//            //FETCH FIRST 3 ROWS ONLY");
//            
//            LineChart.Series<String,Number> series10 = new LineChart.Series<String,Number>();
//            while(rs.next())
//            {
//                
//                series10.getData().add(new XYChart.Data<String,Number>(rs.getDate(3).toString(),rs.getInt(2)));
//                
//            } 
//
//             chartPFdata.getData().add(series10);
//          
//        }
//        catch (SQLException ex){
//            Logger.getLogger(GUIMoniAsthma.class.getName()).log(Level.SEVERE, null, ex);
//        }
//   }
   
 
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleSearchAction();
        //GetLineChartDataDB();
        
        
        GetLineChartData();
                 
   
        
    }    
    
}
