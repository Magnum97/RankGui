package me.spaicygaming.rankgui;

import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.milkbowl.vault.economy.Economy;

public class InvClickListener implements Listener{
	
	private RankGui main = RankGui.getInstance();
	private RanksManager manager = main.getRanksManager();
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		String invTitle = e.getInventory().getTitle();
		String rankGuiTitle = InventoryGui.getRankInventoryTitle();
		
		if (!invTitle.equals(rankGuiTitle) && !invTitle.equals(ConfirmGui.getConfirmGuiTitle())){
			return;
		}
		e.setCancelled(true);
		
		if (!(e.getWhoClicked() instanceof Player)){
			return;
		}
		Player p = (Player)e.getWhoClicked();
		
		if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()){
			p.closeInventory();
			return;
		}
		
		/*
		 * Main Gui
		 */
		if (invTitle.equals(rankGuiTitle)){
			if (e.getCurrentItem().getType() != Material.valueOf(main.getConfig().getString("Inventory.itemsMaterial"))){
				return;
			}
			// Il nome della sezione (nel config.yml) in cui è contenuto l'item cliccato
			String clickedPexName = manager.getRanksSlot().get(e.getSlot());

			manager.getClickedRanks().put(p, clickedPexName);
			new ConfirmGui(p);

			return;
		}
		/*
		 * Confirm Gui
		 */
		if (e.getCurrentItem().getType() != Material.STAINED_CLAY){
			return;
		}
		p.closeInventory();
		
		// confirm item
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals(main.c("ConfirmGui.confirm.name"))){
			// Cerca di acquistare il pex
			tryTobuyRank(p, manager.getClickedRanks().get(p));
		}
		// cancel item
		else{
			p.sendMessage(main.getMessage("actionCancelled"));
		
		}
		// Toglie il player dall'HashMap con il nome del pex cliccato
		manager.getClickedRanks().remove(p);
	}

	private Economy econ(){
		return main.getEconomy();
	}
	
	private void tryTobuyRank(Player p, String pexName){
		int pexCost = manager.getRankPrice(pexName);
		
		if (econ().getBalance(p) < pexCost){
			p.sendMessage(main.getMessage("notEnoughtMoney"));
			return;
		}
		econ().withdrawPlayer(p, pexCost);
		// Dà il pex
		givePex(p, pexName);
		
		p.sendMessage(main.getMessage("pexBought")
				.replace("{pex}", manager.getColoredRankName(pexName))
				.replace("{cost}", String.valueOf(pexCost)));
	}
	
	private void givePex(Player p, String pexName){
		ConsoleCommandSender console = main.getServer().getConsoleSender();
		String command = main.getConfig().getString("dispatchedCommand");
		
		main.getServer().dispatchCommand(console, command
				.replace("{player}", p.getName())
				.replace("{pex_name}", pexName));
		
		// Console Log
		main.getServer().getLogger().info("[RankGui]  " + p.getName() + " ha acquistato il rank: " + pexName);
	}

	
	
	
}
