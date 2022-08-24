package group.thebasement.plugins.webplugin;

import org.bukkit.plugin.java.JavaPlugin;

import com.sun.net.httpserver.HttpServer;

import group.thebasement.plugins.webplugin.http.Server;
/**
 * This plugin hosts a web server on port 3000.
 * The web server allows you to do many things, such as:
 * - Send commands to the server
 * - View player's positions
 * - View and modify player's inventories
 */
public class WebPlugin extends JavaPlugin {
    HttpServer server = null;
    public WebPlugin plugin = this;

    @Override
    public void onEnable() {
        
        server = Server.create(this, 3000);
        server.start();
    }

    @Override
    public void onDisable() {
        server.stop(1);
    }
}
