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
	private RanksManager ranksManager = main.getRanksManager();
	
	public InventoryGui() {
		rankGuiInv = Bukkit.createInventory(null, main.getConfig().getInt("Inventory.size"), getRankInventoryTitle());
		
		for (String item : main.getConfig().getConfigurationSection("Items").getKeys(false)){
			int itemPosition = getItemPosition(item);
			// Associa ogni config section al rispettivo slot
			ranksManager.getRanksSlot().put(itemPosition, item);
			// Imposta l'item nell'inventario
			rankGuiInv.setItem(itemPosition, itemBuilder(item));
		}

	}
	
	public static String getRankInventoryTitle(){
		return main.c("Inventory.name");
	}
	
	public static Inventory getRankGuiInv(){
		return rankGuiInv;
	}
	
	private ItemStack itemBuilder(String itemNumber){
		ItemStack item = new ItemStack(Material.valueOf(main.getConfig().getString("Inventory.itemsMaterial")), 1);
		ItemMeta itemMeta = item.getItemMeta();
		
		String coloredRankName = ranksManager.getColoredRankName(itemNumber);
		itemMeta.setDisplayName(coloredRankName);
		
		List<String> itemLore = new ArrayList<>();
		for (String lore : main.getConfig().getStringList("ItemsLores")){
			itemLore.add(ChatColor.translateAlternateColorCodes('&', lore)
					.replace("{price}", String.valueOf(ranksManager.getRankPrice(itemNumber)))
					.replace("{name}", coloredRankName)
					.replace("{pex_name}", ranksManager.getPexName(itemNumber)));
		}
		itemMeta.setLore(itemLore);
		
		item.setItemMeta(itemMeta);
		return item;
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
