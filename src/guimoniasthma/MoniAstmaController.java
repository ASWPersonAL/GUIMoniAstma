/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ASW
 */
public class MoniAstmaController implements Initializable {
    
    //// General instans fields.
   
    private String baseUrl = "http://localhost:8080/ServerSideMoniAsthma/webresources";

    private SimpleDateFormat dateFormat;
    
    //// PostPeakflow instans fields with FXML tag using a peakflow apperature.
    
    @FXML
    private TextField pfValue;
    
    @FXML
    private DatePicker pfDatePicker;
    
    @FXML
    private TextField pfComment;
    
    //// Post humidity instans fields with FXML tag using a weather application measurement.
    
     @FXML
    private TextField huValue;
    
    @FXML
    private DatePicker huDatePicker;
    
    @FXML
    private TextField huComment;
    
    //// Post allergies instans fields with FXML tag using DMI pollen numbers. 
    
     @FXML
    private TextField birkValue;
     
    @FXML
    private TextField sagebrushValue;
   
    @FXML
    private TextField elmValue;
    
    @FXML
    private TextField elValue;
  
    @FXML
    private TextField grassValue;
    
    @FXML
    private DatePicker alDatePicker;
    
    @FXML
    private TextField alComment;
    
   
    
   
    //// Line chart declaration for peakflow values (with fxml tags).

    @FXML
    private LineChart<String,Number> pfchart;
   
    //// HUmidity AreaChart declaration with fxml tags.
  
    @FXML
    private AreaChart<String,Number> hchart;
    
    //// Barchart declaration with fxml tags.
    
    @FXML
    private BarChart<String,Number> alchart;
    
    //// FXML tagged instans field for datepickers for the Date search method.
    
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    
  
    //// Class methods.
   
     //// Method to seach by Date in peak flow chart. 
     
       private void getPFChartFromSearchDate(){
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
       int baseline = 470;
       ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
       LineChart.Series<String,Number> seriesS = new LineChart.Series<String,Number>();
       LineChart.Series<String,Number> seriesBl = new LineChart.Series<String,Number>();
       for(Peakflow p : peakflows){
            seriesS.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), p.getPfValue()));
            seriesBl.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), baseline));
       }
      lineChartData.addAll(seriesS,seriesBl);
      pfchart.setData(lineChartData);
    
      seriesS.setName("Peak flow monitoration values");
      seriesBl.setName("Peak flow Baseline values");
   }
       
       //// Method to seach by Date in humidity chart. 
       
       private void getHumidityChartFromSearchDate(){
       WebTarget clientTarget;
       Client client = ClientBuilder.newClient();
       client.register(HumidityMessageBodyReader.class);
       
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
               .target(this.baseUrl + "/hu/searchByDate/{fromDate}/{toDate}")
               .resolveTemplate("fromDate", fromDate)
               .resolveTemplate("toDate", toDate);

       GenericType<List<Humidity>> list = new GenericType<List<Humidity>>() {};
       List<Humidity> humidities = clientTarget.request("application/json").get(list);  

       ObservableList<XYChart.Series<String, Number>> areaChartData = FXCollections.observableArrayList();
       AreaChart.Series<String,Number> seriesH = new AreaChart.Series<String,Number>();
       
       for(Humidity h : humidities){
            seriesH.getData().add(new XYChart.Data<String,Number>(h.getHuDate(), h.getHuValue()));
       }
      areaChartData.add(seriesH);
     
      hchart.setData(areaChartData);
    
   }
        
        //// Method to seach by Date in allergies chart.
       
       private void getAllergiesChartFromSearchDate(){
       WebTarget clientTarget;
       Client client = ClientBuilder.newClient();
       client.register(AllergiesMessageBodyReader.class);
       
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
               .target(this.baseUrl + "/al/searchByDate/{fromDate}/{toDate}")
               .resolveTemplate("fromDate", fromDate)
               .resolveTemplate("toDate", toDate);

       GenericType<List<Allergies>> list = new GenericType<List<Allergies>>() {};
       List<Allergies> allergyList = clientTarget.request("application/json").get(list);  

        ObservableList<XYChart.Series<String,Number>> barchartData = FXCollections.observableArrayList();
         
         BarChart.Series<String,Number> seriesbarBirk = new BarChart.Series<String,Number>();
         BarChart.Series<String,Number> seriesbarSage = new BarChart.Series<String,Number>();
         BarChart.Series<String,Number> seriesbarElm = new BarChart.Series<String,Number>();
         BarChart.Series<String,Number> seriesbarEl = new BarChart.Series<String,Number>();
         BarChart.Series<String,Number> seriesbarGrass = new BarChart.Series<String,Number>();
         
         for(Allergies a : allergyList){
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
         
         seriesbarBirk.setName("Birk");
         seriesbarSage.setName("Sagebrush");
         seriesbarElm.setName("Elm");
         seriesbarEl.setName("El");
         seriesbarGrass.setName("Grass");
      
   }
       //// Method to call all three chart serahc by date functions. FXML tag and bound to onaction in button in view. 
       
       @FXML
       private void handleSearchByDate(){
           getPFChartFromSearchDate();
           getHumidityChartFromSearchDate();
           getAllergiesChartFromSearchDate();
           
       }
     
       //// Developing POST method for peakflow POST.
       
         @FXML
         public void handlePostPf(ActionEvent event){
             
              int pf_value = Integer.parseInt(pfValue.getText());
              System.out.println(pf_value);
              
              LocalDate pf_date = pfDatePicker.getValue();
              System.out.println(pf_date);
              
              String date_Text = pfComment.getText();
              System.out.println(date_Text);
              
              System.out.println("View input values end: ");
              
              Date date1 = Date.from(pf_date.atStartOfDay(ZoneId.systemDefault()).toInstant());

              Peakflow pf = new Peakflow(pf_value, date1, date_Text);              
              
              WebTarget clientTarget;
              Client client = ClientBuilder.newClient();
              client.register(PeakflowMessageBodyWriter.class);
              clientTarget = client.target(this.baseUrl + "/pf");
                            
              Response r = clientTarget.request("application/json").post(Entity.entity(pf, "application/json"));
              System.out.println(r);
    
           //getPFChartFromSearchDate();
         }
         
         @FXML
         public void handlePostHu(ActionEvent event){
             int hu_value = Integer.parseInt(huValue.getText());
             LocalDate hu_date = huDatePicker.getValue();
             String hu_comment = huComment.getText();
             
             Date dateHu = Date.from(hu_date.atStartOfDay(ZoneId.systemDefault()).toInstant());
             
             Humidity hu = new Humidity(hu_value, dateHu, hu_comment);
             
             WebTarget clientTarget;
             Client client = ClientBuilder.newClient();
             client.register(HumidityMessageBodyWriter.class);
             clientTarget = client.target(this.baseUrl + "/hu");
             
             Response r = clientTarget.request("application/json").post(Entity.entity(hu, "application/json"));
             System.out.println(r);
             
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
        pfDatePicker.setConverter(stringConverter); 
        huDatePicker.setConverter(stringConverter);
        
        fromDatePicker.setConverter(stringConverter);
        toDatePicker.setConverter(stringConverter);
        
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        getPFChartFromSearchDate();
        getHumidityChartFromSearchDate();
        getAllergiesChartFromSearchDate();
        pfDatePicker.setValue(LocalDate.now());
        huDatePicker.setValue(LocalDate.now());
        alDatePicker.setValue(LocalDate.now());
        
        //new Alert(Alert.AlertType.INFORMATION, "This is a box for information!").showAndWait();

    }    
}



    
