package me.spaicygaming.rankgui;

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
	
	private String prefix = c("Messages.prefix");
	
	public void onEnable(){
		instance = this;
		
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
	    saveDefaultConfig();
	    
	    new InventoryGui();
	    getServer().getPluginManager().registerEvents(new InvClickListener(), this);
	    
	    getLogger().info("RankGui has been enabled!");
	}
	
	public static RankGui getInstance(){
		return instance;
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
	
    public String c(String configPath){
    	return ChatColor.translateAlternateColorCodes('&', getConfig().getString(configPath));
    }
    
    public String getMessage(String configPath){
    	return prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("Messages.") + configPath);
    }
    
    
}
