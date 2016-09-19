package core.september.example.nodej.serializer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by christian on 16/09/16.
 */
public class RequestSerializer {

    private final HttpServletRequest request;

    public RequestSerializer(HttpServletRequest request) {
        this.request = request;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();

    }
}
