/**
 * 
 */
package me.jonathan.utils;

import me.jonathan.BlockLimiter;

/**
 * @author jonah
 * 
 */
public class ConfigUtil {

	public static String getString(String path) {
		return BlockLimiter.getInstance().getConfig().getString(path);
	}
	
	
}
