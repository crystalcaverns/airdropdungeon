package caps123987.Drop;

import caps123987.airdropdungeon.AirDropDungeon;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("deprecation")
public class AirDrop {
    final int x;
    final int z;
    final Player player;
    final World world;
    int timer;
    int repeatTaskId;
    int waitTaskId;
    AirDropDungeon plugin;
    ArmorStand armorStand;

    public AirDrop(Player p, int timeSec, AirDropDungeon plugin){
        this.timer = timeSec*20;
        this.player = p;
        this.world = p.getWorld();
        this.plugin = plugin;
        p.sendMessage(ChatColor.GRAY+"Air Drop spawned near you");

        int xOff = ThreadLocalRandom.current().nextInt(-plugin.radius,plugin.radius+1);
        int zOff = ThreadLocalRandom.current().nextInt(-plugin.radius,plugin.radius+1);

        x = xOff + (int) p.getLocation().getX();
        z = zOff + (int) p.getLocation().getZ();

        Bukkit.getScheduler().scheduleSyncDelayedTask(AirDropDungeon.getInstance(),this::run);
    }

    public void run(){

        armorStand = (ArmorStand) world.spawnEntity(new Location(world,x+0.5, world.getMaxHeight(),z+0.5), EntityType.ARMOR_STAND);

        armorStand.setGravity(true);
        armorStand.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,-1,3));
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);

        ItemStack headItem = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta meta = headItem.getItemMeta();
        meta.setCustomModelData(301);
        headItem.setItemMeta(meta);

        armorStand.setItem(EquipmentSlot.HEAD,headItem);
        armorStand.addScoreboardTag("AirDrop");


        repeatTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(AirDropDungeon.getInstance(),()->{

            if(armorStand.isDead()){
                Bukkit.getScheduler().cancelTask(getRepeatTaskId());
                Bukkit.getScheduler().cancelTask(getWaitTaskId());
            }

            Location loc = armorStand.getLocation().clone().add(0,3,0);

            loc.getWorld().spawnParticle(Particle.REDSTONE,loc,2,0.1,.7,0.1,0.1,new Particle.DustOptions(Color.RED,2),true);

        },0,1);

        waitTaskId = Bukkit.getScheduler().scheduleSyncDelayedTask(AirDropDungeon.getInstance(),()->{
            Bukkit.getScheduler().cancelTask(getRepeatTaskId());
            armorStand.remove();
            plugin.taskHandler.removeAirDrop(this);
        },timer);
    }

    public int getRepeatTaskId() {
        return repeatTaskId;
    }

    public int getWaitTaskId() {
        return waitTaskId;
    }
    public void cancel(){
        Bukkit.getScheduler().cancelTask(getRepeatTaskId());
        Bukkit.getScheduler().cancelTask(getWaitTaskId());
        armorStand.remove();
    }
}
