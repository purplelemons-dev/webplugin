package group.thebasement.plugins.webplugin.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import group.thebasement.plugins.webplugin.WebPlugin;
import group.thebasement.plugins.webplugin.http.handlers.PlayerInfoPage;
import group.thebasement.plugins.webplugin.http.handlers.PlayersPos;
import group.thebasement.plugins.webplugin.http.handlers.Root;

/**
 * This class creates a server that listens on port 3000.
 * It also handles requests and responses to various endpoints.
 */
public class Server {

    public String readFile(String path) throws IOException {
        InputStream is = getClass().getResourceAsStream("/group/thebasement/plugins/webplugin/"+path);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        return new String(buffer);
    }

    public static HttpServer create(WebPlugin plugin, int port) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port),0);
        } catch (IOException e) {
            System.out.println("Failed to bind to port " + port + ", or otherwise failed attempt to create the HTTP server.");
            throw new RuntimeException(e);
        } finally {
            // Root endpoint
            server.createContext("/", new Root(new Server()));

            // Player endpoints
            server.createContext("/api/playerspos", new PlayersPos(new Server(), plugin));
            
            // Server endpoints

            // Page endpoints
            server.createContext("/page/playerinfo", new PlayerInfoPage(new Server()));
            // Script endpoints


            server.setExecutor(null);
        }
        return server;
    }
    
}
