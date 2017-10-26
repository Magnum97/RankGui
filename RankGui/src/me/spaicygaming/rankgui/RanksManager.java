package me.spaicygaming.rankgui;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class RanksManager {
	
	private RankGui main = RankGui.getInstance();
	// HashMap contenete ogni slot associato al rispettivo rank
	private HashMap<Integer, String> slotNumber = new HashMap<>();
	// HashMap in cui viene aggiunto il player e l'item cliccato nella gui principale prima di accedere alla confirm gui 
	private HashMap<Player, String> clickedItem = new HashMap<>();
	
	public HashMap<Integer, String> getRanksSlot(){
		return slotNumber;
	}
	
	public HashMap<Player, String> getClickedRanks(){
		return clickedItem;
	}
	
	/**
	 * Ritorna il nome colorato del rank inserito
	 * @param itemNumber -> Il nome della sezione dell'item nel config.yml
	 * @return
	 */
	public String getColoredRankName(String itemNumber){
		return main.c("Items." + itemNumber + ".name");
	}
	
	/**
	 * Ritorna il pex corrispondente al rank inserito.
	 * @param itemNumber -> Il nome della sezione dell'item nel config.yml
	 * @return
	 */
	public String getPexName(String itemNumber){
		return main.getConfig().getString("Items." + itemNumber + ".pexName");
	}
	
	/**
	 * Ritorna il prezzo del rank inserito
	 * @param itemNumber -> Il nome della sezione dell'item nel config.yml
	 * @return
	 */
	public int getRankPrice(String itemNumber){
		return main.getConfig().getInt("Items." + itemNumber + ".cost");
	}
	
}
