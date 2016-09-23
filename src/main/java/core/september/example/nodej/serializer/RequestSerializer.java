package core.september.example.nodej.serializer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by christian on 16/09/16.
 */
public class RequestSerializer {

    private final HttpServletRequest request;

    public RequestSerializer(HttpServletRequest request) {
        this.request = request;

    }

    private static final String CRLF = "\r\n";


    public String serialize() throws IOException {
        StringBuffer sb = new StringBuffer();
        getRequestLine(request,sb);
        //getHeader(request,sb);
        getBody(request,sb);
        sb.append(CRLF);
        return sb.toString();
    }

    private void getRequestLine(HttpServletRequest request,StringBuffer buffer) {

        String[] root = request.getRequestURI().split(request.getServletPath());
        String path = root.length > 1 ? root[1] : "/";

        buffer.append(String.format("%s %s HTTP/1.1%s",request.getMethod(),
                path.concat(request.getQueryString() != null ? "?" + request.getQueryString() : "" ),
                CRLF));
    }

    private void getHeader(HttpServletRequest request,StringBuffer buffer) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            buffer.append(String.format("%s: %s%s",name,request.getHeader(name),CRLF));
        }
    }

    private void getBody(HttpServletRequest request,StringBuffer buffer) throws IOException {
        BufferedReader reader = request.getReader();
        String line;
        boolean clrf = true;

        while ((line = reader.readLine()) != null) {
            buffer.append(clrf? String.format("%s%s",CRLF,line):line);
            clrf = false;
        }
        //String data = buffer.toString()
    }
}
