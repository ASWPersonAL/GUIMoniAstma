package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import TypeConverter.PeakflowMessageBodyWriter;
import TypeConverter.PeakflowMessageBodyReader;
import TypeConverter.HumidityMessageBodyWriter;
import TypeConverter.HumidityMessageBodyReader;
import TypeConverter.AllergiesMessageBodyReader;
import TypeConverter.AllergiesMessageBodyWriter;
import SystemDialog.Alerts;
import Model.Peakflow;
import Model.Humidity;
import Model.Allergies;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
    
    //// Instans of Alert object in Model package.
    
     Alerts alert = new Alerts();
     
//// Instans fields.
     
    //// The standard segment of the URL methods in webservice. 
   
    private final String baseUrl = "http://localhost:8080/ServerSideMoniAsthma/webresources";
    
    //// set variable for baseline value. Personal. 
    private int baseline = 550;
    
    //// Line chart declaration for peakflow values (with fxml tags).

    @FXML
    private LineChart<String,Number> pfChart;
    
    //// Table declaration for peakflow table view. 
    
    @FXML
    private TableView<Peakflow> tablePfView; 
   
    //// Humidity AreaChart declaration with fxml tags.
  
    @FXML
    private AreaChart<String,Number> huChart;
    
    //// Barchart declaration with fxml tags.
    
    @FXML
    private BarChart<String,Number> alChart;
    
    //// FXML tagged instans field for datepickers in view.
    
    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private DatePicker pfDatePicker;
    
    @FXML
    private DatePicker huDatePicker;
        
    @FXML
    private DatePicker alDatePicker;
    
    //// String converter to set DatePickers to dd-MM-yyyy format in view.
    
    StringConverter stringConverter;

     //// Instans fields with fxml tags for POST methods. 
    
     //// PostPeakflow instans fields for peakflow apperature input.
    
    @FXML
    private TextField pfValue;
    
    @FXML
    private TextField pfComment;
    
    //// Post humidity instans fields for phone weather application data.
    
     @FXML
    private TextField huValue;
    
    @FXML
    private TextField huComment;
    
    //// Post allergies instans fields using DMI pollen data. 
    
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
    private TextField alComment;
    
    //// Constructor with initialized stringconver.    

    public MoniAstmaController() {
        this.stringConverter = new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
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
    }
    
//// Class Methods.

    //// GET data in charts methods.
    
    public void getPeakflowLineChart(){
      try{
       Client client = ClientBuilder.newClient();
       client.register(PeakflowMessageBodyReader.class);
       WebTarget clientTarget;
       clientTarget = client.target(this.baseUrl + "/peakflow");
        
       GenericType<List<Peakflow>> pfList = new GenericType<List<Peakflow>>() {};
       List<Peakflow> peakflows = clientTarget.request("application/json").get(pfList);
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
      pfChart.setData(lineChartData);
    
      seriesPf.setName("Peakflow monitoration values");
      seriesBl.setName("Peakflow Baseline values");
      
      client.close();
      
     }catch(ProcessingException e) {
        System.out.println(e);
        alert.getNoServerConError();
        }
    }
       
    public void getHumidityAreaChart(){
      try{
   
      Client client = ClientBuilder.newClient();
       client.register(HumidityMessageBodyReader.class);
       WebTarget clientTarget;
       clientTarget = client.target(this.baseUrl + "/humidity");

       GenericType<List<Humidity>> huList = new GenericType<List<Humidity>>() {};
       List<Humidity> humidities = clientTarget.request("application/json").get(huList);  
       ObservableList<XYChart.Series<String, Number>> areaChartData = FXCollections.observableArrayList();
       AreaChart.Series<String,Number> seriesH = new AreaChart.Series<String,Number>();
       
       for(Humidity h : humidities){
            seriesH.getData().add(new XYChart.Data<String,Number>(h.getHuDate(), h.getHuValue()));
            }
       
        areaChartData.add(seriesH);
        huChart.setData(areaChartData);
        huChart.setLegendVisible(false);
        client.close();
       }catch(ProcessingException e){}
    }
       
    public void getAllergiesBarChart(){
      try{
       Client client = ClientBuilder.newClient();
       client.register(AllergiesMessageBodyReader.class);
       WebTarget clientTarget;
       clientTarget = client.target(this.baseUrl + "/allergies");

       GenericType<List<Allergies>> alList = new GenericType<List<Allergies>>() {};
       List<Allergies> allergyList = clientTarget.request("application/json").get(alList);  
       
       ObservableList<XYChart.Series<String,Number>> barChartData = FXCollections.observableArrayList();
         
       BarChart.Series<String,Number> seriesBirk = new BarChart.Series<String,Number>();
       BarChart.Series<String,Number> seriesSage = new BarChart.Series<String,Number>();
       BarChart.Series<String,Number> seriesElm = new BarChart.Series<String,Number>();
       BarChart.Series<String,Number> seriesEl = new BarChart.Series<String,Number>();
       BarChart.Series<String,Number> seriesGrass = new BarChart.Series<String,Number>();
         
       for(Allergies a : allergyList){
             seriesBirk.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlBirkvalue()));
             seriesSage.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlSagebrushvalue()));
             seriesElm.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlElmvalue()));
             seriesEl.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlElvalue()));
             seriesGrass.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlGrassvalue()));
        }
       barChartData.addAll(seriesBirk,seriesSage,seriesElm, seriesEl, seriesGrass);
       alChart.setData(barChartData);
         
       seriesBirk.setName("Birk");
       seriesSage.setName("Sagebrush");
       seriesElm.setName("Elm");
       seriesEl.setName("El");
       seriesGrass.setName("Grass");
       
       client.close();
       }catch(ProcessingException e){}
    }
      
    //// Method Get All charts with data . The method is bound to Button in view using FXML tag.
    
    @FXML
    public void getAllCharts(){
        getPeakflowLineChart();
        getHumidityAreaChart();
        getAllergiesBarChart();
    }
    
    //// POST method for peakflow POST. All including FXML tags for view binding to buttons.
       
    @FXML
    public void handlePostPf(){
       try{
           
           
           
        int pf_value = Integer.parseInt(pfValue.getText());
        LocalDate pf_dateView = pfDatePicker.getValue();
        String pf_comment = pfComment.getText();
        Date pf_date = Date.from(pf_dateView.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Peakflow pf = new Peakflow(pf_value, pf_date, pf_comment);  

        Client client = ClientBuilder.newClient();
        client.register(PeakflowMessageBodyWriter.class);
        WebTarget clientTarget;
        clientTarget = client.target(this.baseUrl + "/peakflow");
                            
        Response r = clientTarget.request("application/json").post(Entity.entity(pf, "application/json"));
        System.out.println(r);
              
        alert.getPostPfSuccesInfo();
        
        client.close();
        
        }catch(NumberFormatException ex){
               alert.getPostAlertError();
        }
    }
           
    @FXML
    public void handlePostHu(){
       try{
        int hu_value = Integer.parseInt(huValue.getText());
        LocalDate hu_dateView = huDatePicker.getValue();
        String hu_comment = huComment.getText();
        Date hu_date = Date.from(hu_dateView.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Humidity hu = new Humidity(hu_value, hu_date, hu_comment);
             
        Client client = ClientBuilder.newClient();
        client.register(HumidityMessageBodyWriter.class);
        WebTarget clientTarget;
        clientTarget = client.target(this.baseUrl + "/humidity");
             
        Response r = clientTarget.request("application/json").post(Entity.entity(hu, "application/json"));
        System.out.println(r);
             
        alert.getPostHuSuccesInfo();
        
        client.close();
        
        }catch(NumberFormatException ex){
                alert.getPostAlertError();
        }
    }
         
    @FXML
    public void handlePostAl(){
       try{
        int alBirk_value = Integer.parseInt(birkValue.getText());
        int alSagebrush_value = Integer.parseInt(sagebrushValue.getText());
        int alElm_value = Integer.parseInt(elmValue.getText());
        int alEl_value = Integer.parseInt(elValue.getText());
        int alGrass_value = Integer.parseInt(grassValue.getText());
        LocalDate al_dateView = alDatePicker.getValue();
        Date al_date = Date.from(al_dateView.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String al_comment = alComment.getText();
        Allergies al = new Allergies(alBirk_value, alSagebrush_value, alElm_value, alEl_value, alGrass_value, al_date,al_comment );
             
        Client client = ClientBuilder.newClient();
        client.register(AllergiesMessageBodyWriter.class);
        WebTarget clientTarget;
        clientTarget = client.target(this.baseUrl + "/allergies");
             
        Response r = clientTarget.request("application/json").post(Entity.entity(al, "application/json"));
        System.out.println(r);
             
        alert.getPostAlSuccesInfo();
        
        client.close();
            
        }catch(NumberFormatException ex){
             alert.getPostAlertError();
        }            
    }
   
    //// Method to seach by Date in peak flow chart. 
     
    public void getPFChartFromSearchDate(){
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

        WebTarget clientTarget;
        clientTarget = client
                .target(this.baseUrl + "/peakflow/searchByDate/{fromDate}/{toDate}")
                .resolveTemplate("fromDate", fromDate)
                .resolveTemplate("toDate", toDate);
       
        GenericType<List<Peakflow>> pfList = new GenericType<List<Peakflow>>() {};
        List<Peakflow> peakflows = clientTarget.request("application/json").get(pfList);
        ObservableList<XYChart.Series<String, Number>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<String,Number> seriesS = new LineChart.Series<String,Number>();
        LineChart.Series<String,Number> seriesBl = new LineChart.Series<String,Number>();
        ObservableList<Peakflow> data = tablePfView.getItems();
        data.clear();
       
        for(Peakflow p : peakflows){
             seriesS.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), p.getPfValue()));
             seriesBl.getData().add(new XYChart.Data<String,Number>(p.getPfDate(), baseline));
             if(p.getPfComment().length() > 0){
                data.add(p);
             }
        }
        lineChartData.addAll(seriesS,seriesBl);
        pfChart.setData(lineChartData);
    
        seriesS.setName("Peakflow monitoration values");
        seriesBl.setName("Peakflow Baseline values");
        
        client.close();
    }
       
    //// Method to seach by Date in humidity chart. 
       
    public void getHumidityChartFromSearchDate(){
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

        WebTarget clientTarget;
        clientTarget = client
                .target(this.baseUrl + "/humidity/searchByDate/{fromDate}/{toDate}")
                .resolveTemplate("fromDate", fromDate)
                .resolveTemplate("toDate", toDate);

        GenericType<List<Humidity>> huList = new GenericType<List<Humidity>>() {};
        List<Humidity> humidities = clientTarget.request("application/json").get(huList);  

        ObservableList<XYChart.Series<String, Number>> areaChartData = FXCollections.observableArrayList();
        AreaChart.Series<String,Number> seriesH = new AreaChart.Series<String,Number>();
       
        for(Humidity h : humidities){
            seriesH.getData().add(new XYChart.Data<String,Number>(h.getHuDate(), h.getHuValue()));
        }
        areaChartData.add(seriesH);
     
        huChart.setData(areaChartData);
        huChart.setLegendVisible(false);
        
        client.close();
    }
        
    //// Method to seach by Date in allergies chart.
       
    public void getAllergiesChartFromSearchDate(){
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

        WebTarget clientTarget;
        clientTarget = client
                .target(this.baseUrl + "/allergies/searchByDate/{fromDate}/{toDate}")
                .resolveTemplate("fromDate", fromDate)
                .resolveTemplate("toDate", toDate);

        GenericType<List<Allergies>> alList = new GenericType<List<Allergies>>() {};
        List<Allergies> allergyList = clientTarget.request("application/json").get(alList);  
        ObservableList<XYChart.Series<String,Number>> barChartData = FXCollections.observableArrayList();
        
        BarChart.Series<String,Number> seriesBirk = new BarChart.Series<String,Number>();
        BarChart.Series<String,Number> seriesSage = new BarChart.Series<String,Number>();
        BarChart.Series<String,Number> seriesElm = new BarChart.Series<String,Number>();
        BarChart.Series<String,Number> seriesEl = new BarChart.Series<String,Number>();
        BarChart.Series<String,Number> seriesGrass = new BarChart.Series<String,Number>();
         
        for(Allergies a : allergyList){
            seriesBirk.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlBirkvalue()));
            seriesSage.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlSagebrushvalue()));
            seriesElm.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlElmvalue()));
            seriesEl.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlElvalue()));
            seriesGrass.getData().add(new XYChart.Data<String,Number>(a.getAlDate(),a.getAlGrassvalue()));
        }        
        barChartData.addAll(seriesBirk,seriesSage,seriesElm, seriesEl, seriesGrass);
        alChart.setData(barChartData);
         
        seriesBirk.setName("Birk");
        seriesSage.setName("Sagebrush");
        seriesElm.setName("Elm");
        seriesEl.setName("El");
        seriesGrass.setName("Grass");
        
          client.close();
    }
       
    //// Method to call all three chart serahc by date functions. FXML tag and bound to onaction in button in view. 
       
    @FXML
    public void handleSearchByDate(){
        getPFChartFromSearchDate();
        getHumidityChartFromSearchDate();
        getAllergiesChartFromSearchDate();
    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        getAllCharts();
      
        pfDatePicker.setConverter(stringConverter); 
        huDatePicker.setConverter(stringConverter);
        alDatePicker.setConverter(stringConverter);
        fromDatePicker.setConverter(stringConverter);
        toDatePicker.setConverter(stringConverter);
        
        pfDatePicker.setValue(LocalDate.now());
        huDatePicker.setValue(LocalDate.now());
        alDatePicker.setValue(LocalDate.now());
       
        pfDatePicker.setShowWeekNumbers(false);
        huDatePicker.setShowWeekNumbers(false);
        alDatePicker.setShowWeekNumbers(false);
        fromDatePicker.setShowWeekNumbers(false);
        toDatePicker.setShowWeekNumbers(false);
     
    }    
}



    
