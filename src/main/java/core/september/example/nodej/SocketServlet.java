package core.september.example.nodej;

import com.sun.corba.se.impl.ior.ByteBuffer;
import core.september.example.nodej.serializer.RequestSerializer;
import core.september.example.nodej.serializer.ResponseSerializer;
import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
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
        //System.out.println(getServletContext().toString());
        //super.service(req, resp);
        File path = new File("http.sock");

        //write request to socket
        UnixSocketAddress address = new UnixSocketAddress(path);
        UnixSocketChannel channel = UnixSocketChannel.open(address);
        PrintWriter w = new PrintWriter(Channels.newOutputStream(channel));
        //System.out.println(bytes);
        //w.print(req.toString());
        String serialized = new RequestSerializer(req).serialize();
        System.out.println(serialized);

        w.print(serialized);
        w.flush();


        //read socket operation
        InputStreamReader r = new InputStreamReader(Channels.newInputStream(channel));


        CharBuffer result = CharBuffer.allocate(1024);
        r.read(result);
        r.read(result);

        result.flip();
        w.close();
        r.close();
        channel.close();

        new ResponseSerializer(resp,result.toString()).serialize();
        resp.getOutputStream().flush();
        resp.getOutputStream().close();

        //channel.close();
    }


}

