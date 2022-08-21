/**
 * 
 */
package me.jonathan.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import me.jonathan.BlockLimiter;

/**
 * @author jonah
 * 
 */
public class AdvancedUtil {
	private static FileConfiguration config = BlockLimiter.getInstance().getConfig();
	private static BlockLimiter plugin = BlockLimiter.getInstance();
	
	private static double xCord(String path) {
		return config.getDouble(path + ".advanced.x-cord");
	}
	private static double yCord(String path) {
		return config.getDouble(path + ".advanced.y-cord");
	}
	private static double zCord(String path) {
		return config.getDouble(path + ".advanced.z-cord");
	}
	public static void advancedOptions(String path, World world, String action, Location loc) {
		if (config.getBoolean(path + ".advanced-options")) {
			location(path, world, action, loc);
		}
	}
	private static Location location(String path, World world, String action, Location loc) {
		Double x = xCord(path);
		Double y = yCord(path);
		Double z = zCord(path);
		if (action == "add") return loc.add(x,y,z);
		if (action == "sub") return loc.subtract(x,y,z);
		return loc;
	}
}
