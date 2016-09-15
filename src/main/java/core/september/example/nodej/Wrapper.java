package core.september.example.nodej;

import com.eclipsesource.v8.NodeJS;

import java.io.File;

/**
 * Created by christian on 15/09/16.
 */
public class Wrapper implements Runnable {

    private  NodeJS nodeJS;
    private File file;

    public Wrapper(File entryPoint) {
        this.file = entryPoint;
    }

    public void run() {
        System.out.println("App starting with file located at: ".concat(file.toString()));
        nodeJS = NodeJS.createNodeJS(file);
        while(nodeJS.isRunning()) {
            nodeJS.handleMessage();
        }
    }

    public void release() {
        System.out.println("Realeasing");
        nodeJS.release();
    }
}
