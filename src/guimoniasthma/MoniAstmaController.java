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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
    
    //// General instans fields.
   
    private String baseUrl = "http://localhost:8080/ServerSideMoniAsthma/webresources";

    private SimpleDateFormat dateFormat;
    
    //// PostBaseline instans fields with FXML tag.
    
    @FXML
    private TextField baselineValue;
    
    @FXML
    private TextField baselineDate;
    
    @FXML
    private Button saveBaseline;
    
    
    //// Instans fields (with fxml tags) for Scattered chart for peakflow values.
    @FXML
    private CategoryAxis xAxisScat;
    
    @FXML
    private NumberAxis yAxisScat;
    
    @FXML
    private LineChart<String,Number> pfchart;
   
    //// LineChart instans fields with fxml tags.
    @FXML
    private CategoryAxis xAxisLine;
    
    @FXML
    private NumberAxis yAxisLine;
    
    @FXML
    private AreaChart<String,Number> hchart;
    //private LineChart<String,Number> hchart;
    
    //// Barchart instans fields with fxml tags.
    
     @FXML
    private CategoryAxis xAxisBar;
    
    @FXML
    private NumberAxis yAxisBar;
    
    @FXML
    private BarChart<String,Number> alchart;
    
    //// FXML tagged instans field for datepickers for the Date search method.
    
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
  
 
    //// Class methods.
    
    //// Method to Post baseline data. 
    
    private void postBaseline(){
  
        
    }
    
    //// Method to GET data for peakflow scattered chart. The method is called in the Initialize constructor. 
  
     private void GetScatterChartData(){
         //// Initialiazing API url client.
         WebTarget clientTarget;
         //// Initizializing http client.
         Client client = ClientBuilder.newClient();
         //// Add a BodyReader with json to the client. 
         client.register(PeakflowMessageBodyReader.class);

         // IVAN
         // StringBuilder a = new StringBuilder(this.baseUrl);
         // a.append("/preakflow");
         // clientTarget = client.target(a.toString());
         
            //// Defining the method url for the clientarget to use.
            clientTarget = client.target(this.baseUrl + "/pf");
            //// Declar a list for peakflow objects.
            GenericType<List<Peakflow>> list = new GenericType<List<Peakflow>>() {};
            //// Declars a list of type peakflow and in the list is the clienttarget result request in json.
            List<Peakflow> peakflows = clientTarget.request("application/json").get(list);  
            System.out.println(peakflows);
            //// Declar a scatter chart that is fxcollection observablearraylist.
            ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
            LineChart.Series<String,Number> seriesS = new LineChart.Series<String,Number>();
            LineChart.Series<String,Number> seriesBl = new LineChart.Series<String, Number>();
             //// Foreacher in peakflow list with json objects.
             for(Peakflow p : peakflows){
                 
                     seriesBl.getData().add(new XYChart.Data<String, Number>(p.getPfDate(), p.getPfBaseline()));
                     seriesS.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), p.getPfValue()));
                 
                 //// System out put for developing overview.
                 System.out.println(p.getPfDate());
                 System.out.println(p.getPfValue());
                }
             //// Adding the serieS to the scattered chart list.
             lineChartData.add(seriesS);
             lineChartData.add(seriesBl);
             //// Adding the scattered chart list to the chart in the FXML view. 
             pfchart.setData(lineChartData);
             xAxisScat.setLabel("Date");
             yAxisScat.setLabel("L per min");
             pfchart.setTitle("ScatterChart with Peak flow data:");
             
             seriesS.setName("Peak flow monitoration values");
            
             seriesBl.setName("Peak flow Baseline values");
             
             
             
   }

     //// Method to GET data for humidity line chart. The method is called in the Initialize constructor. 
     
     public void GetLineChartData(){
         WebTarget clientTarget1;
         Client client1 = ClientBuilder.newClient();
         client1.register(HumidityMessageBodyReader.class);
         clientTarget1 = client1.target(this.baseUrl + "/humidity");
         
         GenericType<List<Humidity>> list1 = new GenericType<List<Humidity>>(){};
         List<Humidity> humidities = clientTarget1.request("application/json").get(list1);
         
         System.out.println(humidities.toString());
         
         ObservableList<XYChart.Series<String,Number>> linechartData = FXCollections.observableArrayList();
         
         AreaChart.Series<String,Number> seriesLine = new AreaChart.Series<String,Number>();
         
         for(Humidity h : humidities){
             seriesLine.getData().add(new XYChart.Data<String,Number>(h.getHuDate(), h.getHuValue()));
         }
         linechartData.add(seriesLine);
         
         hchart.setData(linechartData);
         hchart.setTitle("LineChart for humidy data:");
         xAxisLine.setLabel("Date");
         yAxisLine.setLabel("Percentage %");
         
         seriesLine.setName("Humidity");
         
           yAxisLine.setAutoRanging(false);
           yAxisLine.setLowerBound(0);
           yAxisLine.setUpperBound(100);
           //yAxisLine.setTickUnit(0.1);

         
     }
     
  //// Method to GET data for allergies bar chart. The method is called in the Initialize constructor. 

     
     public void GetBarChart(){
         WebTarget clientTarget;
         Client client = ClientBuilder.newClient();
         client.register(AllergiesMessageBodyReader.class);
         clientTarget = client.target(this.baseUrl + "/allergies");
         
         GenericType<List<Allergies>> list = new GenericType<List<Allergies>>(){};
         List<Allergies> allergiesList = clientTarget.request("application/json").get(list);
         
         System.out.println(allergiesList.toString());
         
         ObservableList<XYChart.Series<String,Number>> barchartData = FXCollections.observableArrayList();
         
         BarChart.Series<String,Number> seriesbarBirk = new BarChart.Series<String,Number>();
         BarChart.Series<String,Number> seriesbarSage = new BarChart.Series<String,Number>();
         BarChart.Series<String,Number> seriesbarElm = new BarChart.Series<String,Number>();
         BarChart.Series<String,Number> seriesbarEl = new BarChart.Series<String,Number>();
         BarChart.Series<String,Number> seriesbarGrass = new BarChart.Series<String,Number>();
         
         for(Allergies a : allergiesList){
             seriesbarBirk.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlBirkvalue()));
             seriesbarSage.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlSagebrushvalue()));
             seriesbarElm.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlElmvalue()));
             seriesbarEl.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlElvalue()));
             seriesbarGrass.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlGrassvalue()));
         }
         barchartData.add(seriesbarBirk);
         barchartData.add(seriesbarSage);
         barchartData.add(seriesbarElm);
         barchartData.add(seriesbarEl);
         barchartData.add(seriesbarGrass);
         
         alchart.setData(barchartData);
         alchart.setTitle("Barchart for pollen data:");
         xAxisBar.setLabel("Date");
         yAxisBar.setLabel("Amount per m3");
         
         seriesbarBirk.setName("Birk");
         seriesbarSage.setName("Sagebrush");
         seriesbarElm.setName("Elm");
         seriesbarEl.setName("El");
         seriesbarGrass.setName("Grass");
     }
     
     //// Method to seach by Date in peak flow chart. 
     
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
               .target(this.baseUrl + "/pf/searchByDate/{fromDate}/{toDate}")
               .resolveTemplate("fromDate", fromDate)
               .resolveTemplate("toDate", toDate);

       GenericType<List<Peakflow>> list = new GenericType<List<Peakflow>>() {};
       List<Peakflow> peakflows = clientTarget.request("application/json").get(list);  

       System.out.println(peakflows.size());
       
       ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
       LineChart.Series<String,Number> seriesS = new LineChart.Series<String,Number>();
       LineChart.Series<String,Number> seriesBl = new LineChart.Series<String,Number>();
       for(Peakflow p : peakflows){
            seriesS.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), p.getPfValue()));
            seriesBl.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), p.getPfBaseline()));
            System.out.println(p.getPfComment());
            System.out.println(p.getPfValue());
       }
      lineChartData.add(seriesS);
      lineChartData.add(seriesBl);
      pfchart.setData(lineChartData);
      xAxisScat.setLabel("Date");
      yAxisScat.setLabel("L per min");
      pfchart.setTitle("ScatterChart with Peak flow data:");
      seriesS.setName("Peak flow monitoration values");
      seriesBl.setName("Peak flow Baseline values");
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

       
        GetScatterChartData();
        
        GetLineChartData();
        
        GetBarChart();

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