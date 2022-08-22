package me.jonathan.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import me.jonathan.BlockLimiter;
import me.jonathan.utils.BlockUtil;
import me.jonathan.utils.DropUtil;
import me.jonathan.utils.randomizerUtil;
import net.md_5.bungee.api.ChatColor;
import updater.UpdateChecker;


public class Events implements Listener {
	
	@EventHandler
	public static void onBlockPlace(CustomBlockPlaceEvent event) {
		FileConfiguration config = BlockLimiter.getInstance().getConfig();
		
		
		String namespace1 = event.getNamespacedID();
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Location placed = block.getLocation();
		Location under = placed.subtract(0, 1, 0);
		Block bunder = under.getBlock();
		String namespace2 = namespace1.replace(":", "");
		String wrongmsg = config.getString("messages.wrong-block");
		if (config.getStringList("items").contains(namespace1)) {
			
			if (player.hasPermission("blocklimiter.bypass")) return;
			if (BlockUtil.isWater(placed) && config.getBoolean(namespace2 + ".water-collision")) player.sendMessage(ChatColor.translateAlternateColorCodes('&', wrongmsg)); event.setCancelled(true);
			
			CustomBlock stack = CustomBlock.byAlreadyPlaced(bunder);
			if (stack != null) {
				if (config.getBoolean(namespace2 + ".whitelist")) {
					
					if (config.getStringList(namespace2 + ".custom-blocks").contains(stack.getNamespacedID())) {
						return;
					} else player.sendMessage(ChatColor.translateAlternateColorCodes('&', wrongmsg)); event.setCancelled(true);
				} else { // blacklisted 
					if (!config.getStringList(namespace2 + ".custom-blocks").contains(stack.getNamespacedID())) {
						return;
					} else player.sendMessage(ChatColor.translateAlternateColorCodes('&', wrongmsg)); event.setCancelled(true);
				}
			} else {
				if (config.getBoolean(namespace2 + ".whitelist")) {
					
					if (config.getStringList(namespace2 + ".blocks").contains(bunder.getType().toString())) {
						return;
					} else player.sendMessage(ChatColor.translateAlternateColorCodes('&', wrongmsg)); event.setCancelled(true);
				} else { // blacklisted 
					if (!config.getStringList(namespace2 + ".blocks").contains(bunder.getType().toString())) {
						return;
					} else player.sendMessage(ChatColor.translateAlternateColorCodes('&', wrongmsg)); event.setCancelled(true);
				}
			}
		} else return;
	}
	@EventHandler
	public static void onBlockBreak(BlockBreakEvent event){
		FileConfiguration config = BlockLimiter.getInstance().getConfig();
		Block block = event.getBlock();
		Player player = event.getPlayer();
		Location above = block.getLocation().add(0,1,0);
		CustomBlock custom = CustomBlock.byAlreadyPlaced(above.getBlock());
		if (custom != null) {
			String namespace = custom.getNamespacedID();
			if (config.getStringList("items").contains(namespace)) {
				if (player.hasPermission("iaddon.bypass")) { 
					Bukkit.getLogger().info(player.getName() + " Has bypassed the removal for a block");
					return;
				}
				
				
				
				
				// if the block CAN FLOAT
				
				
				if (config.getBoolean(namespace.replace(":", "") +".can-float")) {
					return;
					
				}
				
				
				
				
				
				
				
				// to drop custom items
				List<String> list = config.getStringList(namespace.replace(":", ""));
				if (list != null) {
					if (list.contains("%")) {
						String itemtodrop = randomizerUtil.randomitem(list);
						CustomStack stack = CustomStack.getInstance(itemtodrop);
						if(stack != null) {
							DropUtil.custommat(itemtodrop, above);
						} else {
							DropUtil.material(itemtodrop, above, block.getWorld());
						}
						return;
					} else {
						for (int i = 0; i > list.size(); i++) {
							CustomStack stack = CustomStack.getInstance(list.get(i).toString());
							if(stack != null) {
								DropUtil.custommat(list.get(i).toString(), above);
							} else {
								DropUtil.material(list.get(i).toString(), above, block.getWorld());
							}
						}
					}
					
					
					
					
					
					
					
				} else {
					CustomBlock.remove(above);
					DropUtil.normal(custom, above);
					return;
				}
				
			} else return;
			
		} else return;
		
	}
	@EventHandler
	public static void onJoin(PlayerJoinEvent event) {
		JavaPlugin plugin = BlockLimiter.getInstance();
		Player player = event.getPlayer();
		if (player.hasPermission("blocklimiter.updater.notification")) {
	        new UpdateChecker(plugin, 103716).getVersion(version -> {
	            if (plugin.getDescription().getVersion().equals(version)) {
	                player.sendMessage("[BlockLimiter] There is not a new update available.");
	            } else {
	            	player.sendMessage("[BlockLimiter] There is a new update available.");
	            }
	        });
		}
	}
}
