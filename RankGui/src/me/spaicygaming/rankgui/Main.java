package me.spaicygaming.rankgui;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;


public class Main extends JavaPlugin implements Listener{
	
	public static Main instance;

	String ver = getDescription().getVersion();
	String prefix = "§8[§c§lRankGui§r§8]§r ";
	static Economy econ = null;
	
	public void onEnable(){
		
		if(!new AdvancedLicense("#KEY", "#WEBSITE", this).register()) return;
		
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		instance = this;
		PluginManager pm = getServer().getPluginManager();
		Server server = getServer();
		ConsoleCommandSender console = server.getConsoleSender();
		
	    console.sendMessage(ChatColor.GREEN + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	    console.sendMessage(ChatColor.AQUA + "           Rank" + ChatColor.RED + "Gui " + ChatColor.GRAY + ver);
	    console.sendMessage(ChatColor.GRAY + "         Cliente:" + ChatColor.AQUA + "EcoptPvP" );
	    console.sendMessage(ChatColor.GOLD + "       Autore: SpaicyGaming");
	    console.sendMessage(ChatColor.GREEN + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
	    
	    pm.registerEvents(this, this);
	    pm.registerEvents(new InvClick(), this);
	    InventoryGui.Gui();
	    saveDefaultConfig();
	}
	
	public void onDisable(){
		getLogger().info("RankGui v" + ver + " correttamente disabilitato. Autore: SpaicyGaming");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if (alias.equalsIgnoreCase("special")) {
			if (sender instanceof Player){
				if (sender.hasPermission("rankgui.open")){
					((Player) sender).openInventory(InventoryGui.rankgui);
					
				}else{
					sender.sendMessage(prefix + ChatColor.RED + "Non hai i permessi per farlo!");
				}
				
			} else{
				getLogger().info("Questo comando può essere eseguito solo da un player!");
			}
		}
		if (alias.equalsIgnoreCase("specialreload")){
			if (sender.hasPermission("rankgui.reload")){
				reloadConfig();
				InventoryGui.Gui();
				if (sender instanceof Player){
					sender.sendMessage(prefix + "Config reloaded.");
				}else {
					getLogger().info("Config reloaded");
				}	
			}else{
				sender.sendMessage(prefix + ChatColor.RED + "Non hai i permessi per farlo!");
			}
						
		}
		return false;
	}
	
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
