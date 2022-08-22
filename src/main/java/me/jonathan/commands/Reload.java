/**
 * 
 */
package me.jonathan.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.jonathan.BlockLimiter;

/**
 * @author jonah
 * 
 */
public class Reload implements CommandExecutor {

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("blreload")) {
			BlockLimiter.getInstance().reloadConfig();
			
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Reload complete!"));
		}
		return true;
	}
}
