package fr.minekahest;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class DD_Listener implements Listener {

	@EventHandler
	// Cet event permet de g�rer la mort des joueurs
	public void onPlayerDie(PlayerDeathEvent event) {
		Player player = event.getEntity();
							
		// On recup�re la conf itemID dans le fichier config.yml
		int itemID = DeathDeceiver.instance.confItemID;
		// Si le joueur poss�de l'objet dans son inventaire, si la config est d�sactiv�e, ou qu'il a une permission
		if (player.getInventory().contains(itemID) || itemID == 0 || DeathDeceiver.perms.has(player, "deathdeceiver.noloot") ) {
			// On retire l'objet en question si un objet est configur�
			if (itemID != 0)
				player.getInventory().removeItem(new ItemStack(itemID, 1));
			// Sauvegarde de l'inventaire avec comme param�tre le joueur, c'est
			// le seul moyen de r�aliser
			// des op�rations sur le joueur sur certains EVENT, ici c'est facultatif.
			DeathDeceiver.im.savePlayerInventory(player);

			// Suppression de l'xp et des loots pour eviter la duplication ;)
			event.getDroppedExp();
			event.getDrops().clear();
		}
	}

	@EventHandler
	// Cet event permet de g�rer le respawn d'un joueur
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		// Recup�ration de l'inventaire/xp du joueur, ici passer le player en
		// param�tre
		// est OBLIGATOIRE, l'event playerRespawn est tr�s capricieux
		DeathDeceiver.im.loadPlayerInventory(event.getPlayer());
	}
}
