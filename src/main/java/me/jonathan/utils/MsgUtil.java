/**
 * 
 */
package me.jonathan.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.jonathan.BlockLimiter;

/**
 * @author jonah
 * 
 */
public class MsgUtil {

	
	public static void wrongBlockMsg(Player player) {
		if (BlockLimiter.getInstance().getConfig().getBoolean("messages.player-wrong-block"))
		player.sendMessage(
				ChatColor
				.translateAlternateColorCodes(
						'&',BlockLimiter
						.getInstance()
						.getConfig()
						.getString("messages.wrong-block")
						.replace("%player-name%", player.getName())));
	}
	public static void wrongblockconsole(Player player, String namespace, String bunder) {
		String[] n = namespace.replace(":", " ").split("\\s+");	
		if (BlockLimiter.getInstance().getConfig().getBoolean("messages.wrong-block-console")) {
			Bukkit.getLogger().info("[BlockLimiter] "+
					player.getName()+
					" Was denied placement for a block, namespace - "+
					n[0]+
					" | Id - "+
					n[1]+
					" | block - "+
					bunder+
					" | "+
					"you can disable these messages in the config"
					);
		}
	}
}
