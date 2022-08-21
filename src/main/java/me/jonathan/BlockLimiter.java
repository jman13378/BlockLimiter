/**
 * 
 */
package me.jonathan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.jonathan.commands.Edit;
import me.jonathan.commands.Reload;
import me.jonathan.events.Events;

/**
 * @author jonah
 * 
 */
public class BlockLimiter extends JavaPlugin {
	
	private static BlockLimiter instance;
	
	@Override
	public void onLoad() {
		instance = this;
	}
	@Override
	public void onEnable() {
		saveDefaultConfig();
		if (getServer().getPluginManager().getPlugin("ItemsAdder") == null) {
			for (int i = 0; i < 20; i++) {
				Bukkit.getLogger().severe("ItemsAdder not found disabling BlockLimiter");
			}
			getServer().getPluginManager().disablePlugin(this);
		}
		getServer().getPluginManager().registerEvents(new Events(), this);
		getCommand("blreload").setExecutor(new Reload());
		getCommand("bledit").setExecutor(new Edit());
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[BlockLimiter] Plugin Has Been Enabled!");
	}
	
	@Override
	public void onDisable() {
		saveDefaultConfig();
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "[BlockLimiter] Plugin Has Been Disabled!");
	}
	public static BlockLimiter getInstance() {
		return instance;
	}
}

