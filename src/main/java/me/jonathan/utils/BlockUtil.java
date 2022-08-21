/**
 * 
 */
package me.jonathan.utils;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * @author jonah
 * 
 */
public class BlockUtil {

	
	public static void removeBlock(Location loc) {
		loc.getBlock().setType(Material.AIR);
	}
}
