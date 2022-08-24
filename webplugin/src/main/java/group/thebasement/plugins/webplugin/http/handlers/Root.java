package group.thebasement.plugins.webplugin.http.handlers;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import group.thebasement.plugins.webplugin.http.Server;

/**
 * Lists all the endpoints that are available.
 */
public class Root implements HttpHandler {

    private final Server server;

    public Root(Server server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        String res = "Endpoints:";


        ex.sendResponseHeaders(200, res.length());
        OutputStream os = ex.getResponseBody();
        os.write(res.getBytes());
        os.close();
        
    }
    
}
