package me.spaicygaming.rankgui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmGui {

	private static RankGui main = RankGui.getInstance();
	private RanksManager manager = main.getRanksManager();

	public ConfirmGui(Player p){
		Inventory confirmGui = Bukkit.createInventory(null, 27, getConfirmGuiTitle());
		
		confirmGui.setItem(10, clayBuilder("cancel"));
		confirmGui.setItem(16, clayBuilder("confirm"));

		confirmGui.setItem(13, signBuilder(p));
		
		p.openInventory(confirmGui);
	}
	
	public static String getConfirmGuiTitle(){
		return main.c("ConfirmGui.title");
	}
	
	/*
	 * Builders
	 */
	private ItemStack clayBuilder(String action) {
		byte color = 0;

		switch (action) {
		case "confirm":
			color = 5;
			break;
		case "cancel":
			color = 14;
			break;
		}
		ItemStack item = new ItemStack(Material.STAINED_CLAY, 1, color);
		ItemMeta itemMeta = item.getItemMeta();

		itemMeta.setDisplayName(main.c("ConfirmGui." + action + ".name"));
		itemMeta.setLore(main.c(main.getConfig().getStringList("ConfirmGui." + action + ".lore")));

		item.setItemMeta(itemMeta);
		return item;
	}

	private ItemStack signBuilder(Player p) {
		ItemStack item = new ItemStack(Material.SIGN, 1);
		ItemMeta itemMeta = item.getItemMeta();
		
		String clickedRank = manager.getClickedRanks().get(p);
		
		itemMeta.setDisplayName(main.c("ConfirmGui.sign.name"));
		
		List<String> coloredLore = new ArrayList<>();
		for (String lore : main.getConfig().getStringList("ConfirmGui.sign.lore")){
			coloredLore.add(main.colorOnly(lore)
					.replace("{pex}", manager.getColoredRankName(clickedRank))
					.replace("{cost}", String.valueOf(manager.getRankPrice(clickedRank)))
					);
		}
		itemMeta.setLore(coloredLore);
		
		item.setItemMeta(itemMeta);
		return item;
	}

}
