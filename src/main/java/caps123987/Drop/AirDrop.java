package caps123987.Drop;

import caps123987.airdropdungeon.AirDropDungeon;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.nio.Buffer;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class AirDrop {
    final int x;
    final int z;
    final Player player;
    final World world;
    int timer;
    int repeatTaskId;

    public AirDrop(Player p, int timeSec){
        this.timer = timeSec*20;
        this.player = p;
        this.world = p.getWorld();
        p.sendMessage(ChatColor.GRAY+"Air Drop spawned near you");

        int xOff = ThreadLocalRandom.current().nextInt(-30,31);
        int zOff = ThreadLocalRandom.current().nextInt(-30,31);

        x = xOff + (int) p.getLocation().getX();
        z = zOff + (int) p.getLocation().getZ();

        Bukkit.getScheduler().scheduleSyncDelayedTask(AirDropDungeon.getInstance(),this::run);
    }

    public void run(){
        player.sendMessage("X: "+x+" Z: "+z);

        ItemDisplay itemDisplay = (ItemDisplay) world.spawnEntity(new Location(world,x+0.5, world.getMaxHeight(),z+0.5), EntityType.ITEM_DISPLAY);
        itemDisplay.setItemStack(new ItemStack(Material.CHEST,1));

        itemDisplay.setGravity(true);

        repeatTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(AirDropDungeon.getInstance(),()->{

            if(itemDisplay.getLocation().getBlock().getType().equals(Material.AIR)){
                itemDisplay.teleport(itemDisplay.getLocation().clone().add(0,-1,0));
            }

        },100L,1L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(AirDropDungeon.getInstance(),()->{
            Bukkit.getScheduler().cancelTask(repeatTaskId);

            itemDisplay.remove();
        },timer);
    }
}
