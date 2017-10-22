package me.spaicygaming.rankgui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryGui {

	private static Main main = Main.getInstance();
	public static Inventory rankgui = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("InventoryName")));
	
	public static void Gui(){
		
		//KIT 1
		ItemStack kit1 = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta kit1m = kit1.getItemMeta();
		kit1m.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Ranks.rank1.nome")));
		ArrayList<String> kit1lores = new ArrayList<String>();
		kit1lores.add("");
		kit1lores.add(ChatColor.GREEN + "Costo: " + ChatColor.ITALIC + main.getConfig().getInt("Ranks.rank1.costo") + "$");
		kit1lores.add("");
		kit1m.setLore(kit1lores);	
		kit1.setItemMeta(kit1m);

		//KIT 2
		ItemStack kit2 = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta kit2m = kit2.getItemMeta();
		kit2m.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Ranks.rank2.nome")));
		ArrayList<String> kit2lores = new ArrayList<String>();
		kit2lores.add("");
		kit2lores.add(ChatColor.GREEN + "Costo: " + ChatColor.ITALIC + main.getConfig().getInt("Ranks.rank2.costo") + "$");
		kit2lores.add("");
		kit2m.setLore(kit2lores);
		kit2.setItemMeta(kit2m);
		
		//KIT3
		ItemStack kit3 = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta kit3m = kit3.getItemMeta();
		kit3m.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Ranks.rank3.nome")));
		ArrayList<String> kit3lores = new ArrayList<String>();
		kit3lores.add("");
		kit3lores.add(ChatColor.GREEN + "Costo: " + ChatColor.ITALIC + main.getConfig().getInt("Ranks.rank3.costo") + "$");
		kit3lores.add("");
		kit3m.setLore(kit3lores);
		kit3.setItemMeta(kit3m);
	
		//KIT4
		ItemStack kit4 = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta kit4m = kit4.getItemMeta();
		kit4m.setDisplayName(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("Ranks.rank4.nome")));
		ArrayList<String> kit4lores = new ArrayList<String>();
		kit4lores.add("");
		kit4lores.add(ChatColor.GREEN + "Costo: " + ChatColor.ITALIC + main.getConfig().getInt("Ranks.rank4.costo") + "$");
		kit4lores.add("");
		kit4m.setLore(kit4lores);
		kit4.setItemMeta(kit4m);
		
		/* + + + + + + + + +
		 * + x + x + x + x +
		 * + + + + + + + + +
		 * + + + + + + + + +
		 */
		
		rankgui.setItem(10, kit1);
		rankgui.setItem(12, kit2);
		rankgui.setItem(14, kit3);
		rankgui.setItem(16, kit4);
		
	}
	
}
