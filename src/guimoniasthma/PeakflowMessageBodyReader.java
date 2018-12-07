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

@Provider
@Consumes({"application/json"})
/**
 *
 * @author ASW
 */
public class PeakflowMessageBodyReader implements MessageBodyReader<List<Peakflow>> {
    
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns,
                                                MediaType mt){
        return true;
    }
    
    
    
    
    @Override
    public List<Peakflow> readFrom(Class<List<Peakflow>> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException{
        if(mt.getType().equals("application") && mt.getSubtype().equals("json")){
            Peakflow peakflow = new Peakflow();
            List<Peakflow> peakflows = new ArrayList();
            JsonParser parser = Json.createParser(in);
            while(parser.hasNext()){
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_OBJECT:
                        peakflow = new Peakflow();
                        break;
                    case END_OBJECT:
                        peakflows.add(peakflow);
                        break;
                    case KEY_NAME:
                        String key = parser.getString();
                        parser.next();
                        switch (key){
                            case "pfMeasureid":
                                peakflow.setPfMeasureid(parser.getInt());
                                break;
                            case "pfValue" :
                                peakflow.setPfValue(parser.getInt());
                                break;
                            case "pfDate" :
                                peakflow.setPfDate(new Date(parser.getLong()));
                                break;
                            case "pfComment":
                                peakflow.setPfComment(parser.getString());
                                break;
                            default:
                              break; 
                        }
                        break;
                    default:
                        break;
                                
                    }
                }
                return peakflows;
            }
        
        throw new UnsupportedOperationException("Not supported MediaType: " + mt);
    }
    
}
