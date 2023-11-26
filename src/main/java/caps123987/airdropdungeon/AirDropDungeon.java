package caps123987.airdropdungeon;

import caps123987.Handlers.TaskHandler;
import caps123987.Listeners.ClaimListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AirDropDungeon extends JavaPlugin {

    public TaskHandler taskHandler;
    public Logger logger;
    public File config;
    public static AirDropDungeon instance;
    public int minDelay;
    public int maxDelay;
    public int playerPercent;
    public int despawnTimeSec;
    public int radius;

    @Override
    public void onEnable() {

        /*if(!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }*/

        this.saveDefaultConfig();

        loadCfg();

        config = new File(this.getDataFolder(), "config.config.yml");

        instance = this;
        logger = super.getLogger();
        taskHandler = new TaskHandler(minDelay,maxDelay,playerPercent, this);
        taskHandler.start();

        Bukkit.getServer().getPluginManager().registerEvents(new ClaimListener(),this);



    }

    public void loadCfg(){
        FileConfiguration config = this.getConfig();
        minDelay = config.getInt("minDelay");
        maxDelay = config.getInt("maxDelay");
        playerPercent = config.getInt("playerPercent");
        despawnTimeSec = config.getInt("despawnTimeSec");
        radius = config.getInt("radius");
    }
    @Override
    public void onDisable() {
        for(Entity e: Bukkit.getServer().getWorld("world").getEntities()){
            if(e.getScoreboardTags().contains("AirDrop")){
                e.remove();
            }
        }
        // Plugin shutdown logic
    }

    public static AirDropDungeon getInstance(){
        return instance;
    }
}
