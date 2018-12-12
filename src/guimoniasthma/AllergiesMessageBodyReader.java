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

public class AllergiesMessageBodyReader implements MessageBodyReader<List<Allergies>> {
    
    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation [] antns, MediaType mt){
        return true;
    }

    @Override
    public List<Allergies> readFrom(Class<List<Allergies>> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        if(mt.getType().equals("application") && mt.getSubtype().equals("json")){
            Allergies allergy = new Allergies();
            
            
            System.out.println(allergy);
            System.out.println("HALLO_ALlergies!");
            
            List<Allergies> allergiesList = new ArrayList();
            
  
            JsonParser parser = Json.createParser(in);
            while (parser.hasNext()){
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_OBJECT:
                        allergy = new Allergies();
                        System.out.println(allergy);
                        break;
                    case END_OBJECT:
                        allergiesList.add(allergy);
                        System.out.println(allergiesList);
                        break;
                    case KEY_NAME:
                      String key = parser.getString();
                      parser.next();
                      switch (key){
                          case "alId":
                            allergy.setAlId(parser.getInt());
                            break;
                          case "alBirkvalue":
                            allergy.setAlBirkvalue(parser.getInt());
                            break;
                          case "alSagebrushvalue":
                            allergy.setAlSagebrushvalue(parser.getInt());
                            break;
                          case "alElmvalue":
                            allergy.setAlElmvalue(parser.getInt());
                            break;
                          case "alElvalue":
                            allergy.setAlElvalue(parser.getInt());
                            break;  
                          case "alGrassvalue":
                            allergy.setAlGrassvalue(parser.getInt());
                            break;  
                          case "alDate":
                            allergy.setAlDate(new Date(parser.getLong()));
                            break;
                          case "alComment":
                            allergy.setAlComment(parser.getString());
                            break;
                          default:
                            break;  
                      }
                      break;
                    default:
                      break;  
                }
            }
            return allergiesList;
            
            
        }
        
        
        throw new UnsupportedOperationException("Not supported yet. " + mt); //To change body of generated methods, choose Tools | Templates.
    }
    
}
