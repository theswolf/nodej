package core.september.example.nodej;

import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.InputStreamEntity;


import javax.jws.soap.InitParam;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.util.Enumeration;
import java.util.Scanner;

//import jnr.unixsocket.UnixSocketAddress;
//import jnr.unixsocket.UnixSocketChannel;

/**
 * Created by christian on 15/09/16.
 */
@WebServlet(name="socket",
        urlPatterns={"/socket/*"})
public class SocketServlet extends HttpServlet{



    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(getServletContext().toString());
        //super.service(req, resp);
        File path = new File("http.sock");

        printRequest(req);

        //write request to socket
        UnixSocketAddress address = new UnixSocketAddress(path);
        UnixSocketChannel channel = UnixSocketChannel.open(address);
        System.out.println("connected to " + channel.getRemoteSocketAddress());
        PrintWriter w = new PrintWriter(Channels.newOutputStream(channel));
        //System.out.println(bytes);
        //w.print(req.toString());
        w.print("GET / HTTP/1.1\r\n");
        w.print("Host: stackoverflow.com\r\n\r\n");
        w.flush();

        //read socket operation
        InputStreamReader r = new InputStreamReader(Channels.newInputStream(channel));
        CharBuffer result = CharBuffer.allocate(1024);
        r.read(result);
        result.flip();

        resp.getOutputStream().write(result.toString().getBytes());
        resp.getOutputStream().flush();

    }

    private void printRequest(HttpServletRequest httpRequest) {
        System.out.println("receive " + httpRequest.getMethod() +" notification for "+ httpRequest.getRequestURI());


        System.out.println(" \n\n Headers");

        Enumeration headerNames = httpRequest.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            System.out.println(headerName + " = " + httpRequest.getHeader(headerName));
        }

        System.out.println("\n\nParameters");

        Enumeration params = httpRequest.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String)params.nextElement();
            System.out.println(paramName + " = " + httpRequest.getParameter(paramName));
        }

        System.out.println("\n\n Row data");
        System.out.println(extractPostRequestBody(httpRequest));
    }

    static String extractPostRequestBody(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = null;
            try {
                s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s.hasNext() ? s.next() : "";
        }
        return "";
    }

}

