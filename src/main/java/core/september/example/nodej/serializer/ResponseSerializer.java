package core.september.example.nodej.serializer;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by christian on 22/09/16.
 */
public class ResponseSerializer {

    private final HttpServletResponse response;
    private final String raw;

    private static final String CRLF = "\r\n";
    private final BufferedReader reader;
    private final static List<String> excludedHeader = Arrays.asList(new String[]{
    "X-Powered-By",
    "Content-Type",
    "Content-Length",
    "ETag",
    "Date",
    "Connection"});

    public ResponseSerializer(HttpServletResponse response, String raw) {
        this.response = response;
        this.raw = raw;
        this.reader = new BufferedReader(new StringReader(raw));
    }

    public void serialize() throws IOException {
        String line;
        boolean firstLine = true;
        boolean body = false;
        while((line = reader.readLine())!= null) {
            if(firstLine) {
                statusCode(line);
                firstLine = false;
            }
            else {
                if(line.equalsIgnoreCase("") || body) {
                    body = true;
                    body(line);
                }
                else {
                    header(line);
                }
            }
        }

        //response.getOutputStream().flush();


    }

    public void statusCode(String line) {
        response.setStatus(Integer.valueOf(line.split(" ")[1]));
    }

    public void header(String line) {
        String[] header = line.split(":");
        if(!excludedHeader.contains(header[0]))
           response.setHeader(header[0],header[1].trim());
    }

    public void body(String line) throws IOException {
        //String[] header = line.split(":");
        response.getOutputStream().write(line.getBytes());
    }


}
