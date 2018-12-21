/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;


import HttpMessageBodies.PeakflowMessageBodyWriter;
import HttpMessageBodies.PeakflowMessageBodyReader;
import HttpMessageBodies.HumidityMessageBodyWriter;
import HttpMessageBodies.HumidityMessageBodyReader;
import HttpMessageBodies.AllergiesMessageBodyReader;
import HttpMessageBodies.AllergiesMessageBodyWriter;
import Model.Alerts;
import Model.Peakflow;
import Model.Humidity;
import Model.Allergies;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ASW
 */



public class MoniAstmaController implements Initializable {
    
    //// Instans of class Alerts in Model package.
    
     Alerts alert = new Alerts();

    //// General instans fields.
   
    private String baseUrl = "http://localhost:8080/ServerSideMoniAsthma/webresources";

    private SimpleDateFormat dateFormat;
    
    private int baseline = 550;
    
    //public String sErrorHeader = "Input missing or format is wrong ";
    //public String sErrorContext = "Empty input or the format of the input value is wrong. Value must be a rounded number.!";
   
    //// Line chart declaration for peakflow values (with fxml tags).

    @FXML
    private LineChart<String,Number> pfchart;
    
    //// Table declaration for peakflow table view. 
    
    @FXML
    private TableView<Peakflow> tablePfView; 
   
    //// Humidity AreaChart declaration with fxml tags.
  
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

    
    //// GET data in charts methods.
    
   private void getPeakflowLineChart(){
       
        try{
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        client.register(PeakflowMessageBodyReader.class);
        clientTarget = client.target(this.baseUrl + "/pf");
        
        
       GenericType<List<Peakflow>> pflist = new GenericType<List<Peakflow>>() {};
       List<Peakflow> peakflows = clientTarget.request("application/json").get(pflist);
       //int baseline = 550;
       ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
       LineChart.Series<String,Number> seriesPf = new LineChart.Series<String,Number>();
       LineChart.Series<String,Number> seriesBl = new LineChart.Series<String,Number>();
       ObservableList<Peakflow> data = tablePfView.getItems();
       data.clear();
       
       for(Peakflow p : peakflows){
            seriesPf.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), p.getPfValue()));
            seriesBl.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), baseline));
             if(p.getPfComment().length() > 0){
               data.add(p);
           }
       }
      lineChartData.addAll(seriesPf,seriesBl);
      pfchart.setData(lineChartData);
    
      seriesPf.setName("Peak flow monitoration values");
      seriesBl.setName("Peak flow Baseline values");
      
      } catch(ProcessingException e) {
        System.out.println(e);
        e.printStackTrace();
        alert.getNoServerConError();
        }
        
  }
       
       public void getHumidityChart(){
       try{
       WebTarget clientTarget;
       Client client = ClientBuilder.newClient();
       client.register(HumidityMessageBodyReader.class);
 
       clientTarget = client.target(this.baseUrl + "/hu");

       GenericType<List<Humidity>> list = new GenericType<List<Humidity>>() {};
       List<Humidity> humidities = clientTarget.request("application/json").get(list);  

       ObservableList<XYChart.Series<String, Number>> areaChartData = FXCollections.observableArrayList();
       AreaChart.Series<String,Number> seriesH = new AreaChart.Series<String,Number>();
       
       for(Humidity h : humidities){
            seriesH.getData().add(new XYChart.Data<String,Number>(h.getHuDate(), h.getHuValue()));
            }
       
        areaChartData.add(seriesH);
        hchart.setData(areaChartData);
        hchart.setLegendVisible(false);
       }catch(ProcessingException e){}
    }
       
       public void getAllergiesBarChart(){
       
       try{
       WebTarget clientTarget;
       Client client = ClientBuilder.newClient();
       client.register(AllergiesMessageBodyReader.class);

       clientTarget = client.target(this.baseUrl + "/al");

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
       }catch(ProcessingException e){}
       }

       @FXML
       public void getAllCharts(){
            getPeakflowLineChart();
            getAllergiesBarChart();
            getHumidityChart();
       }
    
    //// POST method for peakflow POST.
       
         @FXML
         public void handlePostPf(ActionEvent event){
             
             try{
              int pf_value = Integer.parseInt(pfValue.getText());
              LocalDate pf_date = pfDatePicker.getValue();
              
              String date_Text = pfComment.getText();
              
              Date date = Date.from(pf_date.atStartOfDay(ZoneId.systemDefault()).toInstant());

              Peakflow pf = new Peakflow(pf_value, date, date_Text);  
              
              WebTarget clientTarget;
              Client client = ClientBuilder.newClient();
              client.register(PeakflowMessageBodyWriter.class);
              clientTarget = client.target(this.baseUrl + "/pf");
                            
              Response r = clientTarget.request("application/json").post(Entity.entity(pf, "application/json"));
              System.out.println(r);
              
              alert.getPostPfSuccesInfo();
              
             }catch(NumberFormatException ex){
               alert.getPostAlertError();
             }
         }
         
         
         @FXML
         public void handlePostHu(ActionEvent event){
             
            try{
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
             
             alert.getPostHuSuccesInfo();
            
            }catch(NumberFormatException ex){
             
                alert.getPostAlertError();
            }
             
         }
         
         @FXML
         public void handlePostAl(ActionEvent event){
            
            try{
             int alBirk_value = Integer.parseInt(birkValue.getText());
             int alSagebrush_value = Integer.parseInt(sagebrushValue.getText());
             int alElm_value = Integer.parseInt(elmValue.getText());
             int alEl_value = Integer.parseInt(elValue.getText());
             int alGrass_value = Integer.parseInt(grassValue.getText());
             
             LocalDate al_date = alDatePicker.getValue();
             Date dateAl = Date.from(al_date.atStartOfDay(ZoneId.systemDefault()).toInstant());
             
             String al_comment = alComment.getText();
             
             Allergies al = new Allergies(alBirk_value, alSagebrush_value, alElm_value, alEl_value, alGrass_value, dateAl,al_comment );
             
             WebTarget clientTarget;
             Client client = ClientBuilder.newClient();
             client.register(AllergiesMessageBodyWriter.class);
             clientTarget = client.target(this.baseUrl + "/al");
             
             Response r = clientTarget.request("application/json").post(Entity.entity(al, "application/json"));
             System.out.println(r);
             
             alert.getPostAlSuccesInfo();
            
            }catch(NumberFormatException ex){
             alert.getPostAlertError();
             
             }            
         }
               
    
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
        //int baseline = 550;
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
      
      
      ObservableList<Peakflow> data = tablePfView.getItems();
       data.clear();
      GenericType<List<Peakflow>> listpf = new GenericType<List<Peakflow>>() {
            };
      
      
       List<Peakflow> peakflowsTable = clientTarget.request("application/json").get(listpf);
       
       
       for(Peakflow p : peakflowsTable){
           if(p.getPfComment().length() > 0){
               data.add(p);
           }
       }
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
      hchart.setLegendVisible(false);
       
    
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    
        getAllCharts();
      
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
        fromDatePicker.setShowWeekNumbers(false);
        toDatePicker.setShowWeekNumbers(false);
        pfDatePicker.setConverter(stringConverter); 
        huDatePicker.setConverter(stringConverter);
        alDatePicker.setConverter(stringConverter);
        pfDatePicker.setValue(LocalDate.now());
        huDatePicker.setValue(LocalDate.now());
        alDatePicker.setValue(LocalDate.now());
        pfDatePicker.setShowWeekNumbers(false);
        huDatePicker.setShowWeekNumbers(false);
        alDatePicker.setShowWeekNumbers(false);
    }    
}



    
