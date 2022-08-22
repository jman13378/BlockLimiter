/**
 * 
 */
package me.jonathan;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.tchristofferson.configupdater.ConfigUpdater;

import me.jonathan.commands.Edit;
import me.jonathan.commands.Reload;
import me.jonathan.events.Events;
import updater.UpdateChecker;

/**
 * @author jonah
 * 
 */
public class BlockLimiter extends JavaPlugin {
	
	String configver = "1.1.5";
    private File oldConfigFile;
    private FileConfiguration oldConfig;

	private static BlockLimiter instance;
	
	@Override
	public void onLoad() {
		saveDefaultConfig();
		instance = this;
	}
	@Override
	public void onEnable() {
        new UpdateChecker(this, 103716).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available.");
            }
        });
        
		if (!getConfig().getString("config-version").equals(configver)) {
			
			createOldConfig();
			try {
				
				ConfigUpdater.update(this, "config.yml", oldConfigFile, Arrays.asList());
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveConfig();
		}
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
	public FileConfiguration getOldConfig() {
        return this.oldConfig;
    }
	private void createOldConfig() {
		oldConfigFile = new File(getDataFolder(), "OldConfig.yml");
        if (!oldConfigFile.exists()) {
        	oldConfigFile.getParentFile().mkdirs();
            saveResource("OldConfig.yml", false);
        }
        
        oldConfig = new YamlConfiguration();
        try {
        	oldConfig.load(oldConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        
        
	}

}

