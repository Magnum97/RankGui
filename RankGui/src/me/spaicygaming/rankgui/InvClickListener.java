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
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		
		if (!e.getInventory().getTitle().equals(InventoryGui.getRankInventoryTitle())){
			return;
		}
		
		if (!(e.getWhoClicked() instanceof Player)){
			return;
		}
		Player p = (Player)e.getWhoClicked();
		
		p.closeInventory();
		
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() != Material.valueOf(main.getConfig().getString("Inventory.itemsMaterial"))){
			return;
		}
		
		// Il nome della sezione (nel config.yml) in cui è contenuto l'item cliccato
		String clickedPexName = InventoryGui.getRanksSlot().get(e.getSlot());
		
		// Cerca di acquistare il pex
		tryTobuyRank(p, clickedPexName);
	}

	private Economy econ(){
		return main.getEconomy();
	}
	
	private void tryTobuyRank(Player p, String pexName){
		int pexCost = InventoryGui.getRankPrice(pexName);
		
		if (econ().getBalance(p) < pexCost){
			p.sendMessage(main.getMessage("notEnoughtMoney"));
			return;
		}
		econ().withdrawPlayer(p, pexCost);
		// Dà il pex
		givePex(p, pexName);
		
		p.sendMessage(main.getMessage("pexBought")
				.replace("{pex}", InventoryGui.getColoredRankName(pexName))
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
