package group.thebasement.plugins.webplugin.http.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.bukkit.entity.Player;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import group.thebasement.plugins.webplugin.WebPlugin;
import group.thebasement.plugins.webplugin.http.Server;

public class PlayersPos implements HttpHandler {

    private final WebPlugin plugin;
    private final Server server;

    public PlayersPos(Server server, WebPlugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    private final String formatCoordinates(Player player) {
        return String.format("\"%s\":{\"X\":\"%.2f\",\"Y\":\"%.2f\",\"Z\":\"%.2f\"}", player.getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        // Check if the request specifies a player in the query string
        String playerName = null;
        String res;

        if (ex.getRequestURI().getQuery() != null) {
            String[] playerQuery = ex.getRequestURI().getQuery().split("player=");
            if (playerQuery.length > 1) {
                playerName = playerQuery[1];
            }
        }
        
        OutputStream os = ex.getResponseBody();
        ex.getResponseHeaders().add("Content-Type", "application/json");

        if (playerName == null) {
            // If not, return a list of all players' positions
            res = "{\"players\": {";
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                res += formatCoordinates(player) + ",";
            }
            res = res.substring(0, res.length() - 1);
            res += "}}";
            ex.sendResponseHeaders(200, res.length());

        } else if (plugin.getServer().getPlayer(playerName) == null) {
            // If so, return the player's position
            res = "{\"error\": \"Player not found\"}";
            ex.sendResponseHeaders(404, res.length());

        } else {
            Player player = plugin.getServer().getPlayer(playerName);
            res = "{\"players\": {" + formatCoordinates(player) + "}}";
            ex.sendResponseHeaders(200, res.length());
        }

        os.write(res.getBytes());
        os.close();
        
    }

}
