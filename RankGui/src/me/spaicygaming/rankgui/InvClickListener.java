package me.spaicygaming.rankgui;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.milkbowl.vault.economy.Economy;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

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
			p.closeInventory();
			
			// Il nome della sezione (nel config.yml) in cui è contenuto l'item cliccato
			String clickedSectionName = manager.getRanksSlot().get(e.getSlot());
			
			// Controlla che il player non abbia già il rank
			PermissionUser user = PermissionsEx.getUser(p);
			List<String> playerGroups = user.getParentIdentifiers();
			
			if (playerGroups.contains(manager.getPexName(clickedSectionName))){
				p.sendMessage(main.getMessage("alreadyOwn"));
				return;
			}
			
			manager.getClickedRanks().put(p, clickedSectionName);
			// Apre la gui di conferma acquisto
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
	
	/**
	 * Cerca di acquistare il rank inserito ad il player dato.
	 * Controlla che il player abbia fondi sufficienti.
	 * @param p -> Il player
	 * @param sectionName -> Il nome della sezione nel config.yml in cui è impostato il rank
	 */
	private void tryTobuyRank(Player p, String sectionName){
		int pexCost = manager.getRankPrice(sectionName);
		
		if (econ().getBalance(p) < pexCost){
			p.sendMessage(main.getMessage("notEnoughtMoney"));
			return;
		}
		econ().withdrawPlayer(p, pexCost);
		// Dà il pex
		givePex(p, sectionName);
		
		p.getWorld().playEffect(p.getLocation(), Effect.NOTE, 2);
		
		p.sendMessage(main.getMessage("pexBought")
				.replace("{pex}", manager.getColoredRankName(sectionName))
				.replace("{cost}", String.valueOf(pexCost)));
	}
	
	/**
	 * Dà il pex al player inserito attraverso un comando
	 * @param p -> Il player
	 * @param sectionName -> La sezione nel config.yml in cui è impostato il pex da dare al player
	 */
	private void givePex(Player p, String sectionName){
		String pexName = manager.getPexName(sectionName);
		
		ConsoleCommandSender console = main.getServer().getConsoleSender();
		String command = main.getConfig().getString("dispatchedCommand");
		
		main.getServer().dispatchCommand(console, command
				.replace("{player}", p.getName())
				.replace("{pex_name}", pexName));
		
		// Console Log
		main.getServer().getLogger().info("[RankGui]  " + p.getName() + " ha acquistato il rank: " + pexName);
	}

	
	
	
}
