/**
 * 
 */
package me.jonathan.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;

/**
 * @author jonah
 * 
 */
public class DropUtil {

	public static void normal(CustomBlock item, Location loc) {
		item.drop(loc);
	}
	public static void material(String mat, Location loc, World world) {
		ItemStack material = new ItemStack(Material.getMaterial(mat));
		world.dropItem(loc, material);
	}
	public static void custommat(String namespace, Location loc) {
		CustomStack stack = CustomStack.getInstance(namespace);
		if(stack != null) {
			stack.drop(loc);
		}
		else {
			//no custom item found with that id
		}
	}
}
