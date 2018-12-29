package HttpMessageBodies;

import Model.Allergies;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASW
 */
public class AllergiesMessageBodyWriter implements MessageBodyWriter<Allergies> {
    
      @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return type == Allergies.class;
    }
    
    @Override
    public long getSize(Allergies al, Class<?> type, Type genericType, Annotation[] antns, MediaType mt) {
        return 0;
    }

    @Override
    public void writeTo(Allergies al, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> httpHeaders, OutputStream out) throws IOException, WebApplicationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        PrintWriter sw = new PrintWriter(out);
        sw.write("{");
        sw.write("\"alBirkvalue\": " + al.getAlBirkvalue() + ",");
        sw.write("\"alSagebrushvalue\": " + al.getAlSagebrushvalue() + ",");
        sw.write("\"alElmvalue\": " + al.getAlElmvalue() + ",");
        sw.write("\"alElvalue\": " + al.getAlElvalue() + ",");
        sw.write("\"alGrassvalue\": " + al.getAlGrassvalue() + ",");
        sw.write("\"alDate\": \"" + dateFormat.format(al.getAlDateObject()) + "\",");
        sw.write("\"alComment\": \"" + al.getAlComment() + "\"");
        sw.write("}");
        sw.flush();
        sw.close();
    }
    
}
