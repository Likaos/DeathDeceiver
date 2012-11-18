package fr.minekahest;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class DD_Listener implements Listener {

	@EventHandler
	// Cet event permet de gérer la mort des joueurs
	public void onPlayerDie(PlayerDeathEvent event) {
		Player player = event.getEntity();
							
		// On recupère la conf itemID dans le fichier config.yml
		int itemID = DeathDeceiver.instance.confItemID;
		// Si le joueur possède l'objet dans son inventaire, si la config est désactivée, ou qu'il a une permission
		if (player.getInventory().contains(itemID) || itemID == 0 || DeathDeceiver.perms.has(player, "deathdeceiver.noloot") ) {
			// On retire l'objet en question si un objet est configuré
			if (itemID != 0)
				player.getInventory().removeItem(new ItemStack(itemID, 1));
			// Sauvegarde de l'inventaire avec comme paramètre le joueur, c'est
			// le seul moyen de réaliser
			// des opérations sur le joueur sur certains EVENT, ici c'est facultatif.
			DeathDeceiver.im.savePlayerInventory(player);

			// Suppression de l'xp et des loots pour eviter la duplication ;)
			event.getDroppedExp();
			event.getDrops().clear();
		}
	}

	@EventHandler
	// Cet event permet de gérer le respawn d'un joueur
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		// Recupération de l'inventaire/xp du joueur, ici passer le player en
		// paramètre
		// est OBLIGATOIRE, l'event playerRespawn est très capricieux
		DeathDeceiver.im.loadPlayerInventory(event.getPlayer());
	}
}
