package fr.minekahest;

import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathDeceiver extends JavaPlugin {
	
	public static DeathDeceiver instance;
	public static DD_InventoryManager im;
	public static Logger log;	
	public int confItemID;
	public String confMessage;
	
	//Déclaration pour Vault
	public static Permission perms = null;

	// ACTIVATION DU PLUGIN
	@Override
	public void onEnable() {

		// Creation de l'inventory manager
		im = new DD_InventoryManager();
		//Racourcis
		instance = this;
		log = this.getLogger();

		// Cree un fichier de config si inexistant
		saveDefaultConfig();

		confItemID = getConfig().getInt("itemID");
		confMessage = getConfig().getString("revive-message");		

		// Enregistrement du listener
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new DD_Listener(), this);
		
		// Check Vaull si présent sur le serveur
		if (pm.getPlugin("Vault") != null) {
			setupPermissions();
			log.info("Vault detecte prit en charge");
		} else { log.info("Vault non prit en charge"); }	
	}
		
	// Methode de vault pour en registrer son hook
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		        perms = rsp.getProvider();
		        return perms != null;
		    }

	// ARRET DU PLUGIN
	@Override
	public void onDisable() {
	}
}
