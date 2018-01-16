package me.spaicygaming.rankgui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;


public class RankGui extends JavaPlugin {
	
	private static RankGui instance;
	private Economy econ = null;
	private RanksManager ranksManager;
	
	private String prefix = c("Messages.prefix");
	
	/*
	 * [-] -> Done
	 * Todo:
	 * [-] check if the player already has that pex (using PermissionsEx's api)
	 * - Add the rank using pex api and not throw command
	 * - particles/sound effects
	 * - log file (not really needed)
	 * [-] confirm gui
	 */
	
	public void onEnable(){
		instance = this;
		
		// Dependencies
		if (!setupEconomy() || getServer().getPluginManager().getPlugin("PermissionsEx") == null) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault and/or PermissionsEx dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
	    saveDefaultConfig();
	    
	    ranksManager = new RanksManager();
	    new InventoryGui();
	    getServer().getPluginManager().registerEvents(new InvClickListener(), this);
	    
	    getLogger().info("RankGui has been enabled!");
	}
	
	public static RankGui getInstance(){
		return instance;
	}
	
	public RanksManager getRanksManager(){
		return ranksManager;
	}
	
	public void onDisable(){
		getLogger().info("RankGui has been disabled.");
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
	
	public Economy getEconomy(){
		return econ;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if (alias.equalsIgnoreCase("rankgui")) {
			if (!(sender instanceof Player)){
				sender.sendMessage("Questo comando può essere eseguito solo da un player");
				return false;
			}
			Player p = (Player)sender;
			
			// Opens Gui
			if (args.length == 0){
				if (!p.hasPermission("rankgui.open")){
					p.sendMessage(getMessage("noPerms"));
					return false;
				}
				p.openInventory(InventoryGui.getRankGuiInv());
			}
			// Reload
			else if (args.length == 1 && args[0].equalsIgnoreCase("reload")){
				if (!p.hasPermission("rankgui.reload")){
					p.sendMessage(getMessage("noPerms"));
					return false;
				}
				reloadConfig();
			}
			// Wrong args
			else{
				p.sendMessage(getMessage("wrongUsage"));
				return false;
			}
		}
		
		return false;
	}

/*
 * Chat Utils
 */
	
	public String getPrefix(){
		return prefix;
	}
	
	public String colorOnly(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public String c(String configPath){
    	return colorOnly(getConfig().getString(configPath));
    }
	
	public List<String> c(List<String> loresList){
		List<String> coloredLores = new ArrayList<>();

		for (String lore : loresList){
			coloredLores.add(colorOnly(lore));
		}
    	return coloredLores;
    }
    
    public String getMessage(String configPath){
    	return prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages." + configPath));
    }
    
    
    
}
