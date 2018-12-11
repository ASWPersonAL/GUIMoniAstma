/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
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
    
    private String baseUrl = "http://localhost:8080/ServerSideMoniAsthma/webresources";

    private SimpleDateFormat dateFormat;
    

    
 
    
    @FXML
    DatePicker fromDatePicker = new DatePicker();
    @FXML
    DatePicker toDatePicker = new DatePicker();

    //@FXML
    //private LineChart<String,Number> chart;
    
    //// Scattered chart for peakflow values.
    
    @FXML
    CategoryAxis xAxisScat = new CategoryAxis();
    
    @FXML
    NumberAxis yAxisScat = new NumberAxis();
    
    @FXML
    private ScatterChart<String,Number> schart;
   
    //// LineChart for humidity values.
     @FXML
    CategoryAxis xAxisLine = new CategoryAxis();
    
    @FXML
    NumberAxis yAxisLine = new NumberAxis();
    
    @FXML
    private LineChart<String,Number> hchart;
    
    //// class Methods.

//  
   @FXML
    private void GetChartDataFromSearchDate(){
       WebTarget clientTarget;
       Client client = ClientBuilder.newClient();
       client.register(PeakflowMessageBodyReader.class);
       
       String fromDate = "NULL";
       String toDate = "NULL";
       DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
       if (fromDatePicker.getValue() != null) {
           fromDate = fromDatePicker.getValue().format(dateTimeFormatter);
       }
       if (toDatePicker.getValue() != null) {
           toDate = toDatePicker.getValue().format(dateTimeFormatter);
       }

       clientTarget = client
               .target(this.baseUrl + "/peakflow/searchByDate/{fromDate}/{toDate}")
               .resolveTemplate("fromDate", fromDate)
               .resolveTemplate("toDate", toDate);

       GenericType<List<Peakflow>> list = new GenericType<List<Peakflow>>() {};
       List<Peakflow> peakflows = clientTarget.request("application/json").get(list);  

       System.out.println(peakflows.size());
       
       ObservableList<XYChart.Series<String, Number>> scatterChartData = FXCollections.observableArrayList();
       ScatterChart.Series<String,Number> seriesS = new ScatterChart.Series<String,Number>();
       for(Peakflow p : peakflows){
         seriesS.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), p.getPfValue()));
         System.out.println(p.getPfComment());
                 System.out.println(p.getPfValue());
                }
                  
                  scatterChartData.add(seriesS);
  
      schart.setData(scatterChartData);
   }
  
     private void GetScatterChartData(){
         
         //// Initialiazing API url client.
         WebTarget clientTarget;
         
         //// Initizializing http client.
         Client client = ClientBuilder.newClient();
         
         client.register(PeakflowMessageBodyReader.class);

         //// her sættes clienten med url metode.
         
         // IVAN
         // StringBuilder a = new StringBuilder(this.baseUrl);
         // a.append("/preakflow");
         // clientTarget = client.target(a.toString());
         
         clientTarget = client.target(this.baseUrl + "/peakflow");
         
            ////Erklære en liste med Peakflow objecter.
            GenericType<List<Peakflow>> list = new GenericType<List<Peakflow>>() {};
             
            ////Får en liste med Json objekter
            List<Peakflow> peakflows = clientTarget.request("application/json").get(list);  
            
            System.out.println(peakflows);
            
            ////Liste deklaration til chart i xml.
            //ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
       
            ObservableList<XYChart.Series<String, Number>> scatterChartData = FXCollections.observableArrayList();
            
            ScatterChart.Series<String,Number> seriesS = new ScatterChart.Series<String,Number>();
            
             //// Foreacher over listen med json objekter.
             for(Peakflow p : peakflows){
          
                 seriesS.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), p.getPfValue()));
                 
                 System.out.println(p.getPfDate());
                 System.out.println(p.getPfValue());
                }
             scatterChartData.add(seriesS);
  
      ////Sætter observable list in i grafen til xml view.
      //chart.setData(lineChartData);
      
      schart.setData(scatterChartData);
      //chart.createSymbolsProperty();
 
   }
     
     
     public void GetLineChartData(){
         WebTarget clientTarget1;
         
         Client client1 = ClientBuilder.newClient();
         
         //System.out.print(client1);
         
         client1.register(HumidityMessageBodyReader.class);
         
         clientTarget1 = client1.target(this.baseUrl + "/humidity");
         
         //System.out.print(clientTarget1);
         
         GenericType<List<Humidity>> list1 = new GenericType<List<Humidity>>(){};
         
         List<Humidity> humidities = clientTarget1.request("application/json").get(list1);
         
         System.out.println(humidities.toString());
         
         ObservableList<XYChart.Series<String,Number>> linechartData = FXCollections.observableArrayList();
         
         LineChart.Series<String,Number> seriesLine = new LineChart.Series<String,Number>();
         
         for(Humidity h : humidities){
             seriesLine.getData().add(new XYChart.Data<String,Number>(h.gethDate(), h.gethValue()));
             
              
             System.out.println(h.gethValue());
             //System.out.println(h.gethDate());
             System.out.println(h.gethComment());
             System.out.println(h.gethId());
         }
         linechartData.add(seriesLine);
         
         hchart.setData(linechartData);
         
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
         StringConverter stringConverter = new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }
            
            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        };
        fromDatePicker.setConverter(stringConverter);
        toDatePicker.setConverter(stringConverter);
        
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        //// This method implements the peakflow data into the scatter chart. 
        GetScatterChartData();
        
        GetLineChartData();

    }    
}


    
//   @FXML
//   private void handleSearchAction() {
//       
//       // INITIATING API CLIENT.
//       WebTarget clientTarget;
//       // Declaring variable data. It is an observable list of type peakflow and equald instance field tableView items.
//       ObservableList<Peakflow> data = tableView.getItems();
//       // Clear table view before GET url for data is made.
//       data.clear();
//       
//       //Initiating http client.
//       Client client = ClientBuilder.newClient();
//       
//       //Jsonparser registered to client. Reads json and convert to java types.
//       client.register(PeakflowMessageBodyReader.class);
//       //Conditional statement. If textfield is used then call the search url method. And give the answer.
//       if(textFieldSearch.getText().length() > 0){
//       clientTarget = client.target("http://localhost:8080/ServerSideMoniAsthma/webresources/peakflow/search/{beginBy}");
//           clientTarget = clientTarget.resolveTemplate("beginBy", textFieldSearch.getText());
//       }else{
//           //if textfield is not used just show all elements in list using peakflow GET url.
//           clientTarget = client.target("http://localhost:8080/ServerSideMoniAsthma/webresources/peakflow");
//       }
//       //Define list of type peakflow
//       GenericType<List<Peakflow>> listpf = new GenericType<List<Peakflow>>() {
//            };
//       //Put json into list of type peak flow.
//       List<Peakflow> peakflows = clientTarget.request("application/json").get(listpf);
//       //Foreach though the elements in json list. Add them to variable data that is equal to the tableview in view.
//       for(Peakflow p : peakflows){
//           data.add(p);
//           System.out.println(p.toString());
//       }
//       
//   }
//  
//   @FXML
//   private void handleSearchDate() {
//       
//       // INITIATING API CLIENT.
//       WebTarget clientTarget;
//       // Declaring variable data. It is an observable list of type peakflow and equald instance field tableView items.
//       ObservableList<Peakflow> data = tableView.getItems();
//       // Clear table view before GET url for data is made.
//       data.clear();
//       
//       //Initiating http client.
//       Client client = ClientBuilder.newClient();
//       
//       //Jsonparser registered to client. Reads json and convert to java types.
//       client.register(PeakflowMessageBodyReader.class);
//       //Conditional statement. If textfield is used then call the search url method. And give the answer.
//       if(textFieldSearch.getText().length() > 0){
//       clientTarget = client.target("http://localhost:8080/ServerSideMoniAsthma/webresources/peakflow/searchByDate/{beginBy}");
//           clientTarget = clientTarget.resolveTemplate("beginBy", textFieldSearch.getText());
//       }else{
//           //if textfield is not used just show all elements in list using peakflow GET url.
//           clientTarget = client.target("http://localhost:8080/ServerSideMoniAsthma/webresources/peakflow");
//       }
//       //Define list of type peakflow
//       GenericType<List<Peakflow>> listpf = new GenericType<List<Peakflow>>() {
//            };
//       //Put json into list of type peak flow.
//       List<Peakflow> peakflows = clientTarget.request("application/json").get(listpf);
//       //Foreach though the elements in json list. Add them to variable data that is equal to the tableview in view.
//       for(Peakflow p : peakflows){
//           data.add(p);
//           System.out.println(p.toString());
//       }
//       
//   }

  //// This method fills the Tableview with a list of peakflow elements. 
        //handleSearchAction();
        
        //GetLineChartDataDB();