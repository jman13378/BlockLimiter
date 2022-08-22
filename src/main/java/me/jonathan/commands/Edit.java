/**
 * 
 */
package me.jonathan.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import editor.editorgui;

/**
 * @author jonah
 * 
 */
public class Edit implements CommandExecutor {

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("bledit")) {
			if (!(sender instanceof Player)) return true;
			Player player = (Player)sender;
			editorgui.openEditor(player);
		}
		
		return true;
	}

}
