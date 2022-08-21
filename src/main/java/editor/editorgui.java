/**
 * 
 */
package editor;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.jonathan.BlockLimiter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

/**
 * @author jonah
 * 
 */
public class editorgui {
	/*
	 * Creating a Typed GUI
	 * Gui gui = Gui.gui()
     *   .title(Component.text("GUI Title!"))
     *   .type(GuiType.DISPENSER)
     *   .create();
	 * 
	 * 
	 * Current supported GUI types
	 * CHEST - Default - 0 to 53 slots
	 * WORKBENCH - 0 to 9 slots
	 * HOPPER - 0 to 4 slots
     * DISPENSER - 0 to 8 slots
  	 * BREWING - 0 to 4 slots
  	 * 
  	 * Creating a GUI item
  	 * // Alternatively you can use the ItemBuilder
	 * GuiItem guiItem = ItemBuilder.from(Material.STONE).asGuiItem();
	 * GuiItem guiItem = ItemBuilder.from(Material.STONE).asGuiItem(event -> {
     * // Handle your click action here
	 * });
	 * 
	 * Adding the item to the GUI
	 * // Using a slot number from 0 to 53
	 *  gui.setItem(slot, guiItem);
     *
	 * // Using rows and columns
     * gui.setItem(row, col, guiItem);
     *
     * // Using add item to add to next free slot
     * gui.addItem(guiItem);
	 */
	private static int rows() {
		int rows = 1; // 9
		List<String> list = BlockLimiter.getInstance().getConfig().getStringList("items");
		double result = list.size() / 9;
		switch (String.valueOf(Math.round(result))) {
		case "0":
			rows = 3;
			break;
		case "1":
			rows = 4;
			break;
		case "2":
			rows = 5;
			break;
		case "3":
			rows = 6;
			break;
		default:
			rows = 1;
			break;
		}
		return rows;
	}

	public static void openEditor(Player player) {
		FileConfiguration config = BlockLimiter.getInstance().getConfig();
		if (!config.getBoolean("in-game-editor.enabled")) return;
		// 18 >= 18
		List<String> list = BlockLimiter.getInstance().getConfig().getStringList("items");
		Gui gui = Gui.gui()
				.title(Component.translatable("BlockLimiter - Editor",TextColor.fromHexString("#000C3A")))
				.rows(rows())
				.disableAllInteractions()
				.create();
		
		
		Material nextpagemat = Material.getMaterial( config.getString("in-game-editor.nextpage"));
		Material prevpagemat = Material.getMaterial( config.getString("in-game-editor.prevpage"));
		Material exitmat = Material.getMaterial( config.getString("in-game-editor.exit"));
		Material blockermat = Material.getMaterial( config.getString("in-game-editor.blockers"));
		Material undefinedmat = Material.getMaterial( config.getString("in-game-editor.undefined"));
		
		
		
		
		
		
		List<Integer> nextpage = Arrays.asList();
		List<Integer> blockers = Arrays.asList();
		List<Integer> prevpage = Arrays.asList();
		List<Integer> exit = Arrays.asList();
		List<Integer> undefined = Arrays.asList();
		switch (rows()) {
		case 3:
			blockers = Arrays.asList(9,10,11,12,13,14,15,16,17);
			prevpage = Arrays.asList(18);
			nextpage = Arrays.asList(26);
			exit =     Arrays.asList(22);
			undefined =Arrays.asList(23);
			break;
		case 4:
			blockers = Arrays.asList(18,19,20,21,22,23,24,25,26);
			prevpage = Arrays.asList(27);
			nextpage = Arrays.asList(35);
			exit =     Arrays.asList(31);
			undefined =Arrays.asList(32);
			break;
		case 5:
			blockers = Arrays.asList(27,28,29,30,31,32,33,34,35);
			prevpage = Arrays.asList(36);
			nextpage = Arrays.asList(44);
			exit =     Arrays.asList(40);
			undefined =Arrays.asList(41);
			break;
		case 6:
			blockers = Arrays.asList(36,37,38,39,40,41,42,43,44);
			prevpage = Arrays.asList(45);
			nextpage = Arrays.asList(53);
			exit =     Arrays.asList(49);
			undefined =Arrays.asList(50);
			break;
		default:
			break;
		}

		// next page button
		GuiItem nextpageb = ItemBuilder.from(nextpagemat).name(Component.text(" ")).asGuiItem(event -> {
			
		});
		gui.setItem(nextpage, nextpageb);
		
		// prev page button
		GuiItem prevpageb = ItemBuilder.from(prevpagemat).name(Component.text(" ")).asGuiItem(event -> {
			
		});
		gui.setItem(prevpage, prevpageb);
		
		// the pistons
		GuiItem blockersb = ItemBuilder.from(blockermat).name(Component.text(" ")).asGuiItem();
		gui.setItem(blockers, blockersb);
		
		// exit button
		GuiItem exitb = ItemBuilder.from(exitmat).name(Component.text(" ")).asGuiItem(event -> {
			
		});
		gui.setItem(exit, exitb);
		
		// undefined items button
		GuiItem undefinedb = ItemBuilder.from(undefinedmat).name(Component.text("")).asGuiItem(event -> {
			
		});
		gui.setItem(undefined, undefinedb);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		for (int i = 0; i > list.size(); i++) {
			Bukkit.getLogger().warning(list.get(i));
			GuiItem guiItem = ItemBuilder.from(Material.STONE)
					.name(Component.text(list.get(i)))
					
					.asGuiItem(event -> {
						event.getWhoClicked().sendMessage("you clicked which means this is working");
						event.getClick().isLeftClick();
					});
			gui.addItem(guiItem);
		}
		if (gui.getGuiItems().size() >= 1) {
			gui.open(player);
		} else { player.sendMessage("There are no config items :(");}
	}
	
}
