package fr.minekahest;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathDeceive extends JavaPlugin {

	public static DeathDeceive instance;
	public static DC_InventoryManager im;
	public static Logger log;
	
	public int confItemID;
	public String confMessage;

	// CHARGEMENT DU PLUGIN
	@Override
	public void onEnable() {

		// Creation de l'inventory manager
		im = new DC_InventoryManager();
		//Racourcis
		instance = this;
		log = this.getLogger();

		// Cree un fichier de config si inexistant
		saveDefaultConfig();

		confItemID = getConfig().getInt("itemID");
		confMessage = getConfig().getString("revive-message");		

		// Enregistrement du listener
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new DC_Listener(), this);
	}

	// ARRET DU PLUGIN
	@Override
	public void onDisable() {
	}
}
