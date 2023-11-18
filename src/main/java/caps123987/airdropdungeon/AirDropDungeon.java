package caps123987.airdropdungeon;

import caps123987.Handlers.TaskHandler;
import caps123987.Listeners.ClaimListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AirDropDungeon extends JavaPlugin {

    public TaskHandler taskHandler;
    public Logger logger;

    public static AirDropDungeon instance;

    @Override
    public void onEnable() {
        instance = this;
        logger = super.getLogger();
        taskHandler = new TaskHandler(1,2,25, this);
        taskHandler.start();

        Bukkit.getServer().getPluginManager().registerEvents(new ClaimListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AirDropDungeon getInstance(){
        return instance;
    }
}
