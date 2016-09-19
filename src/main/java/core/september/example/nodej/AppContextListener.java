package core.september.example.nodej;

import com.eclipsesource.v8.NodeJS;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * Created by christian on 15/09/16.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    private Wrapper wrapper;
    private NodeJS nodeJS;

    public void contextInitialized(ServletContextEvent servletContextEvent) {

        String url = this.getClass().getResource("/app/app.js").getPath();
        new File("http.sock").delete();
        File entryPoint = new File( url);

       wrapper = new Wrapper(entryPoint);
        new Thread(wrapper).start();

        System.out.println("App started with file located at: ".concat(url));




    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        System.out.println("Destroying context!!!");
        //
    }
}
