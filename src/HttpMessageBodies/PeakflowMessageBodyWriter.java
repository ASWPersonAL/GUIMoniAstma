/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpMessageBodies;

import Model.Peakflow;
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

/**
 *
 * @author ASW
 */
public class PeakflowMessageBodyWriter implements MessageBodyWriter<Peakflow> {

    @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return type == Peakflow.class;
    }
    
    @Override
    public long getSize(Peakflow pf, Class<?> type, Type genericType, Annotation[] antns, MediaType mt) {
        return 0;
    }

    @Override
    public void writeTo(Peakflow pf, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> httpHeaders, OutputStream out) throws IOException, WebApplicationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        PrintWriter sw = new PrintWriter(out);
        sw.write("{");
        sw.write("\"pfValue\": " + pf.getPfValue() + ",");
        sw.write("\"pfDate\": \"" + dateFormat.format(pf.getPfDate()) + "\",");
        sw.write("\"pfComment\": \"" + pf.getPfComment() + "\"");
        sw.write("}");
        sw.flush();
        sw.close();
    }
}
