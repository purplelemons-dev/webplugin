package group.thebasement.plugins.webplugin.http.handlers;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpHandler;

import group.thebasement.plugins.webplugin.http.Server;

import com.sun.net.httpserver.HttpExchange;

public class PlayerInfoPage implements HttpHandler {

    // Sends the playerinfo.html file from the pages package to the client.

    private final Server server;

    public PlayerInfoPage(Server server) {
        this.server = server;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {

        String res = server.readFile("http/pages/playerinfo.html");
        ex.sendResponseHeaders(200, res.length());
        OutputStream os = ex.getResponseBody();
        os.write(res.getBytes());
        os.close();

    }
    
}
