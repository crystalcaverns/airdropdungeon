package caps123987.Handlers;

import caps123987.airdropdungeon.AirDropDungeon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskHandler {

    public final int minDelay;
    public final int maxDelay;
    public final int playerPercent;
    public final AirDropDungeon plugin;
    public final Logger logger;
    public TaskHandler(int minDelay, int maxDelay, int playerPercent, AirDropDungeon plugin){
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.playerPercent = playerPercent;
        this.plugin = plugin;
        this.logger = plugin.logger;
    }
    @SuppressWarnings("deprecation")
    private void run(){
        Bukkit.broadcastMessage(ChatColor.RED+"Air Drop incoming");
        Collection<? extends Player> playersTemp = Bukkit.getServer().getOnlinePlayers();

        //tadyNevimtadyNevimtadyNevimtadyNevimtadyNevimtadyNevimtadyNevimtadyNevimtadyNevimtadyNevimtadyNevimtadyNevimtadyNevimtadyNevim
        List<Player> players = new ArrayList<Player>(playersTemp);



    }

    private void setUpTask(){
        int delay = ThreadLocalRandom.current().nextInt(minDelay, maxDelay + 1);

        Bukkit.getScheduler().runTaskLater(plugin, this::run,delay);
    }


    public void start(){
        logger.log(Level.INFO,"Setting up");
        setUpTask();
    }
}
