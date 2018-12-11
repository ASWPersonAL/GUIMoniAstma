/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guimoniasthma;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import static javax.json.stream.JsonParser.Event.END_OBJECT;
import static javax.json.stream.JsonParser.Event.KEY_NAME;
import static javax.json.stream.JsonParser.Event.START_OBJECT;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;


@Provider
@Consumes({"application/json"})
/**
 *
 * @author ASW
 */
public class HumidityMessageBodyReader implements MessageBodyReader<List<Humidity>>{
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns,
                                                MediaType mt){
        return true;
    
}
    

@Override
    public List<Humidity> readFrom(Class<List<Humidity>> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException{
        if(mt.getType().equals("application") && mt.getSubtype().equals("json")){
            Humidity humidity = new Humidity();
            List<Humidity> humidities = new ArrayList();
            JsonParser parser = Json.createParser(in);
            while(parser.hasNext()){
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_OBJECT:
                        humidity = new Humidity();
                        break;
                    case END_OBJECT:
                        humidities.add(humidity);
                        break;
                    case KEY_NAME:
                        String key = parser.getString();
                        parser.next();
                        switch (key){
                            case "hId":
                                humidity.sethId(parser.getInt());
                                break;
                            case "hValue" :
                                humidity.sethValue(parser.getInt());
                                break;
                            case "hDate" :
                                humidity.sethDate(new Date(parser.getLong()));
                                break;
                            case "hComment":
                                humidity.sethComment(parser.getString());
                                break;
                            default:
                              break; 
                        }
                        break;
                    default:
                        break;
                                
                    }
                }
                return humidities;
            }
        
        throw new UnsupportedOperationException("Not supported MediaType: " + mt);
    }
    
}