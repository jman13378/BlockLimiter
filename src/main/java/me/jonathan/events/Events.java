package me.jonathan.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import me.jonathan.BlockLimiter;
import me.jonathan.utils.DropUtil;
import me.jonathan.utils.MsgUtil;
import me.jonathan.utils.randomizerUtil;


public class Events implements Listener {
	private static FileConfiguration config = BlockLimiter.getInstance().getConfig();
	
	@EventHandler
	public static void onBlockPlace(CustomBlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		String namespace = event.getNamespacedID();
		Location under = event.getBlock().getLocation().subtract(0.00, 1, 0.00);
		
		// if the use has the bypass perm
		Material bunder = under.getBlock().getBlockData().getMaterial();
		if (player.hasPermission("iaddon.bypass")) { 
			Bukkit.getLogger().info(player.getName() + " Has bypassed placement for a block");
			return;
		}
		
		
		
		CustomBlock custom = CustomBlock.byAlreadyPlaced(under.getBlock());
		if (custom != null) {
			
		}
		
		

		
		
		
		// if the items initializer has the items
		if (config.getStringList("items").contains(namespace)) {
			// if the location to place contains water
			if (
					config.getBoolean(namespace.replace(":", "") + ".water-collision")&&
					event.getBlock().getLocation().getBlock().isLiquid()
					) {
				event.setCancelled(true);
				MsgUtil.wrongblockconsole(player, namespace, bunder.toString());
				MsgUtil.wrongBlockMsg(player);
				return;
			}
			
			
			
			// if the block should be whitelisted or blacklisted
			if (config.getBoolean(namespace.replace(":", "") + ".whitelist")) 
				// if the block under the placement is in the blocks list
				if (config.getStringList(namespace.replace(":", "") +  ".blocks").contains(bunder.toString())
						|| config.getStringList(namespace.replace(":", "") +  ".custom-blocks").contains(custom.getNamespacedID())) {
					return;
				} else {
					event.setCancelled(true);
					MsgUtil.wrongblockconsole(player, namespace, bunder.toString());
					MsgUtil.wrongBlockMsg(player);
					return;
					
				}
			
			
			
			
			
			// if the block is blacklisted
			if (!config.getBoolean(namespace.replace(":", "") + ".whitelist")) 
				// if the block under the placement is not in the blocks list
				if (!config.getStringList(namespace.replace(":", "") +  ".blocks").contains(bunder.toString())
						|| !config.getStringList(namespace.replace(":", "") +  ".custom-blocks").contains(custom.getNamespacedID())) {
					
					return;
				} else {
					event.setCancelled(true);
					MsgUtil.wrongblockconsole(player, namespace, bunder.toString());
					MsgUtil.wrongBlockMsg(player);
					return;
					
				}
		} else return;
	}
	@EventHandler
	public static void onBlockBreak(BlockBreakEvent event){
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

}
