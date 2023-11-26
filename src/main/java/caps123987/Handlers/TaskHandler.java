package caps123987.Handlers;

import caps123987.Drop.AirDrop;
import caps123987.airdropdungeon.AirDropDungeon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("deprecation")
public class TaskHandler {

    public final int minDelay;
    public final int maxDelay;
    public final int playerPercent;
    public final AirDropDungeon plugin;
    public final Logger logger;
    private BukkitTask bukkitTask;
    public TaskHandler(int minDelay, int maxDelay, int playerPercent, AirDropDungeon plugin){
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.playerPercent = playerPercent;
        this.plugin = plugin;
        this.logger = plugin.logger;
    }

    private void run(){
        Bukkit.broadcastMessage(ChatColor.RED+"Air Drop incoming");
        Collection<? extends Player> playersTemp = Bukkit.getServer().getOnlinePlayers();


        List<Player> players = new ArrayList<Player>(playersTemp);
        int playerCount = (int) Math.ceil(players.size()*(playerPercent/100.0));

        Bukkit.broadcastMessage("Player change: "+playerCount);

        List<Player> selectedPlayers = new ArrayList<Player>();

        for(int i = 0;i<playerCount;i++){
            int listIdx = ThreadLocalRandom.current().nextInt(0, players.size());

            selectedPlayers.add(players.get(listIdx));
            players.remove(listIdx);
        }
        for(Player p:selectedPlayers){
            new AirDrop(p,plugin.despawnTimeSec, plugin);
        }

    }

    private BukkitTask setUpTask(){
        int delay = ThreadLocalRandom.current().nextInt(minDelay*10, (maxDelay*10) + 1);

        logger.log(Level.INFO,"delay: "+delay);
        Bukkit.broadcastMessage("delay: "+delay);

        return Bukkit.getScheduler().runTaskLater(plugin, ()->{
            run();
            bukkitTask = setUpTask();
        }, (long) ((delay/10.0) *20.0*60.0));
    }


    public void start(){
        logger.log(Level.INFO,"Setting up");
        bukkitTask = setUpTask();
        setRepairTask(30);
    }

    public void setRepairTask(int seconds){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()->{

            BukkitTask currentTask = getCurrentTask();

            if(!Bukkit.getScheduler().isQueued(currentTask.getTaskId())){
                logger.log(Level.SEVERE,"Task is not running, Repairing");
                bukkitTask.cancel();
                setUpTask();
            }

        },20L,seconds * 20L);
    }

    public BukkitTask getCurrentTask(){
        return bukkitTask;
    }
}
