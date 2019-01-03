/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TypeConverter;

import Model.Humidity;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author ASW
 */

@Provider
@Consumes({"application/json"})

public class HumidityMessageBodyReader implements MessageBodyReader<List<Humidity>> {
    
    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation [] antns, MediaType mt){
        return true;
    }

    @Override
    public List<Humidity> readFrom(Class<List<Humidity>> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        if(mt.getType().equals("application") && mt.getSubtype().equals("json")){
            Humidity humidity = new Humidity();            
            List<Humidity> humidities = new ArrayList();
  
            JsonParser parser = Json.createParser(in);
            while (parser.hasNext()){
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_OBJECT:
                        humidity = new Humidity();
                        //System.out.println(humidity);
                        break;
                    case END_OBJECT:
                        humidities.add(humidity);
                        //System.out.println(humidities);
                        break;
                    case KEY_NAME:
                      String key = parser.getString();
                      parser.next();
                      switch (key){
                          case "huId":
                            humidity.setHuId(parser.getInt());
                            break;
                          case "huValue":
                            humidity.setHuValue(parser.getInt());
                            break;
                          case "huDate":
                            humidity.setHuDate(new Date(parser.getLong()));
                            break;
                          case "huComment":
                            humidity.setHuComment(parser.getString());
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
        throw new UnsupportedOperationException("Not supported yet. " + mt); //To change body of generated methods, choose Tools | Templates.
    }
    
}
