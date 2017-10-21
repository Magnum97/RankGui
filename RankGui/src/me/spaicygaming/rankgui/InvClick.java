package me.spaicygaming.rankgui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.milkbowl.vault.economy.EconomyResponse;

public class InvClick implements Listener{
	
	ConsoleCommandSender console = Bukkit.getConsoleSender();
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		String name = p.getName();
		String brname = "§6§l" + name + "§r";
		
		if (e.getInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("InventoryName")))){
			e.setCancelled(true);
			
			if(e.getCurrentItem() == null || e.getCurrentItem().getType()==Material.AIR || !e.getCurrentItem().hasItemMeta()) {
				p.getWorld().playSound(p.getLocation(), Sound.BAT_HURT, 0.2F, 0.2F);
				p.closeInventory();
				return;
			}
			
			p.getWorld().playSound(p.getLocation(), Sound.CLICK, 0.4F, 0.4F);
			
			String bought = Main.instance.prefix + ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messaggi.AcquistoEffettuato"));
			String nomoney = Main.instance.prefix + ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Messaggi.NoMoney"));
			
			switch (e.getSlot()){
			//Rank 1
			case 10:
				EconomyResponse buy1 = Main.econ.withdrawPlayer(p, Main.instance.getConfig().getInt("Ranks.rank1.costo"));
				if(buy1.transactionSuccess()) {
					p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
					p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 2, 2);
					Bukkit.dispatchCommand(console, "pex user " + name + " group add " + Main.instance.getConfig().getString("Ranks.rank1.nomepex"));
					Bukkit.broadcastMessage(Main.instance.prefix + brname + " ha acquistato il Rank " + ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Ranks.rank1.nome")) + ChatColor.RESET + "!");
					p.sendMessage(bought);
					p.closeInventory();					
				}else {
					p.sendMessage(nomoney);
				}
				break;
				
			//Rank 2
			case 12:
				EconomyResponse buy2 = Main.econ.withdrawPlayer(p, Main.instance.getConfig().getInt("Ranks.rank2.costo"));
				if(buy2.transactionSuccess()) {
					p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
					p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 2, 2);
					Bukkit.dispatchCommand(console, "pex user " + name + " group add " + Main.instance.getConfig().getString("Ranks.rank2.nomepex"));
					Bukkit.broadcastMessage(Main.instance.prefix + brname + " ha acquistato il Rank " + ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Ranks.rank2.nome")) + ChatColor.RESET + "!");
					p.sendMessage(bought);
					p.closeInventory();
				}else {
					p.sendMessage(nomoney);
				}
				break;
				
			//Rank 3
			case 14:
				EconomyResponse buy3 = Main.econ.withdrawPlayer(p, Main.instance.getConfig().getInt("Ranks.rank3.costo"));
				if(buy3.transactionSuccess()) {
					p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
					p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 2, 2);
					Bukkit.dispatchCommand(console, "pex user " + name + " group add " + Main.instance.getConfig().getString("Ranks.rank3.nomepex"));
					Bukkit.broadcastMessage(Main.instance.prefix + brname + " ha acquistato il Rank " + ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Ranks.rank3.nome")) + ChatColor.RESET + "!");
					p.sendMessage(bought);
					p.closeInventory();
				}else {
					p.sendMessage(nomoney);
				}
				break;
				
			//Rank 4
			case 16:
				EconomyResponse buy4 = Main.econ.withdrawPlayer(p, Main.instance.getConfig().getInt("Ranks.rank4.costo"));
				if(buy4.transactionSuccess()) {
					p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
					p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 2, 2);
					Bukkit.dispatchCommand(console, "pex user " + name + " group add " + Main.instance.getConfig().getString("Ranks.rank4.nomepex"));
					Bukkit.broadcastMessage(Main.instance.prefix + brname + " ha acquistato il Rank " + ChatColor.translateAlternateColorCodes('&', Main.instance.getConfig().getString("Ranks.rank4.nome")) + ChatColor.RESET + "!");
					p.sendMessage(bought);
					p.closeInventory();
				}else {
					p.sendMessage(nomoney);
				}
				break;
			}
			
		}
		
		
	}
	
}
