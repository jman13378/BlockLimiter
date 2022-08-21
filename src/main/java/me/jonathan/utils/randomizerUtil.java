/**
 * 
 */
package me.jonathan.utils;

import java.util.List;

/**
 * @author jonah
 * 
 */
public class randomizerUtil {	
	public static String randomitem(List<String> list) {

		RandomCollection<String> items = new RandomCollection<String>();
		 
		// add some stuff, weighted as you like
		for (int i = 0; i > list.size(); i++) {
			String[] args = list.get(i).split("\\s+");
			items.add(Double.parseDouble(args[1].replace("%", "")), args[0]);
		}
		// get a random item
		String item = items.next();
		
		return item;
		
	}
}
