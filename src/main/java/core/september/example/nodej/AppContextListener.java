package core.september.example.nodej;

import com.eclipsesource.v8.NodeJS;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * Created by christian on 15/09/16.
 */
public class AppContextListener implements ServletContextListener {

    private NodeJS nodeJS;
    private Wrapper wrapper;

    public void contextInitialized(ServletContextEvent servletContextEvent) {

        String url = this.getClass().getResource("/app/app.js").getPath();

        File entryPoint = new File( url);

        System.out.println("App starting with file located at: ".concat(url));
        nodeJS = NodeJS.createNodeJS(entryPoint);
        while(nodeJS.isRunning()) {
            nodeJS.handleMessage();
        }

        /*Wrapper wrapper = new Wrapper(entryPoint);
        */new Thread(wrapper).start();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Destroying context!!!");
        nodeJS.release();
    }
}
