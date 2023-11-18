package caps123987.Listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class ClaimListener implements Listener {

    @EventHandler
    public void onEntityClick(PlayerInteractAtEntityEvent e){

        Bukkit.broadcastMessage(e.getRightClicked().getName()+" "+e.getRightClicked().getScoreboardTags().toString());

        if(!e.getRightClicked().getScoreboardTags().contains("AirDrop"))return;

        e.getRightClicked().remove();

        Component name = Component.text("Dungeon Run Entry Key")
                .color(TextColor.color(15885614))
                .decoration(TextDecoration.BOLD,true)
                .decoration(TextDecoration.ITALIC,false);

        Component lore1 = Component.text("Your one-way ticket to a Dungeon Run!")
                .color(TextColor.color(13027014))
                .decoration(TextDecoration.BOLD,false)
                .decoration(TextDecoration.ITALIC,false);

        Component lore2 = Component.text("Can be both-way if you're good enough.")
                .color(TextColor.color(13027014))
                .decoration(TextDecoration.BOLD,false)
                .decoration(TextDecoration.ITALIC,false);

        List<Component> lore = new ArrayList<>();
        lore.add(lore1);
        lore.add(lore2);

        ItemStack item = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name);
        meta.lore(lore);
        meta.setCustomModelData(302);
        item.setItemMeta(meta);

        e.getPlayer().getInventory().addItem(item);
    }
}
