package caps123987.Drop;

import caps123987.airdropdungeon.AirDropDungeon;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        ArmorStand armorStand = (ArmorStand) world.spawnEntity(new Location(world,x+0.5, world.getMaxHeight(),z+0.5), EntityType.ARMOR_STAND);

        armorStand.setGravity(true);

        ItemStack headItem = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta meta = headItem.getItemMeta();
        meta.setCustomModelData(301);
        headItem.setItemMeta(meta);

        armorStand.setItem(EquipmentSlot.HEAD,headItem);
        armorStand.addScoreboardTag("AirDrop");

        Bukkit.getScheduler().scheduleSyncDelayedTask(AirDropDungeon.getInstance(),()->{
            armorStand.remove();
        },timer);
    }
}
