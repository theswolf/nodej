package core.september.example.nodej;

import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by christian on 16/09/16.
 */
@WebServlet(name="proxy",
        urlPatterns={"/proxy"},
        initParams={
                //@WebInitParam(name="targetUri", value="http://localhost:3000/"),
                @WebInitParam(name="targetUri", value="http://localhost:3000/"),
                @WebInitParam(name="log", value="true") })
public class CustomProxyServlet extends ProxyServlet {

    public CustomProxyServlet() {
        System.out.println("***************servlet init********************+");
    }
}
