package me.spaicygaming.rankgui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryGui {

	private static RankGui main = RankGui.getInstance();
	private static Inventory rankGuiInv;
	
	public InventoryGui() {
		rankGuiInv = Bukkit.createInventory(null, main.getConfig().getInt("Inventory.size"), getRankInventoryName());
		
		System.out.println("fatto");
		
		for (String item : main.getConfig().getConfigurationSection("Items").getKeys(false)){
			rankGuiInv.setItem(getItemPosition(item), itemBuilder(item));
			System.out.println("ciclo");
		}

	}
	
	public static String getRankInventoryName(){
		return main.c("Inventory.name");
	}
	
	public static Inventory getRankGuiInv(){
		return rankGuiInv;
	}
	
	private ItemStack itemBuilder(String itemNumber){
		ItemStack item = new ItemStack(Material.valueOf(main.getConfig().getString("Inventory.itemsMaterial")), 1);
		ItemMeta itemMeta = item.getItemMeta();
		
		String coloredRankName = getColoredRankName(itemNumber);
		itemMeta.setDisplayName(coloredRankName);
		
		List<String> itemLore = new ArrayList<>();
		for (String lore : main.getConfig().getStringList("ItemsLores")){
			itemLore.add(ChatColor.translateAlternateColorCodes('&', lore)
					.replace("{price}", String.valueOf(getRankPrice(itemNumber)))
					.replace("{name}", coloredRankName)
					.replace("{pex_name}", getPexName(itemNumber)));
		}
		itemMeta.setLore(itemLore);
		
		item.setItemMeta(itemMeta);
		return item;
	}
	
	/**
	 * Ritorna il nome colorato del rank inserito
	 * @param itemNumber -> Il nome della sezione dell'item nel config.yml
	 * @return
	 */
	public static String getColoredRankName(String itemNumber){
		return main.c("Items." + itemNumber + ".name");
	}
	
	/**
	 * Ritorna il pex corrispondente al rank inserito.
	 * @param itemNumber -> Il nome della sezione dell'item nel config.yml
	 * @return
	 */
	public static String getPexName(String itemNumber){
		return main.getConfig().getString("Items." + itemNumber + ".pexName");
	}
	
	/**
	 * Ritorna il prezzo del rank inserito
	 * @param itemNumber -> Il nome della sezione dell'item nel config.yml
	 * @return
	 */
	public static int getRankPrice(String itemNumber){
		return main.getConfig().getInt("Items." + itemNumber + ".cost");
	}
	
	
	/**
	 * Ritorna la posizione impostata nel config dell'item inserito.
	 * @param itemNumber -> Il nome della sezione dell'item nel config.yml
	 * @return
	 */
	private int getItemPosition(String itemNumber){
		return main.getConfig().getInt("Items." + itemNumber + ".invPosition");
	}
	
	
	
}
