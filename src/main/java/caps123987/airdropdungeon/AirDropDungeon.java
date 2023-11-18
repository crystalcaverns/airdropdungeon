package caps123987.airdropdungeon;

import caps123987.Handlers.TaskHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AirDropDungeon extends JavaPlugin {

    public TaskHandler taskHandler;
    public Logger logger;

    @Override
    public void onEnable() {
        logger = super.getLogger();
        taskHandler = new TaskHandler(1,2,25, this);
        taskHandler.start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
