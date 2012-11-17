package fr.minekahest;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DD_InventoryManager {

	// Cette map fait le lien entre les joueurs et les inventaires sauvegard�s
	private HashMap<String, DD_Inventory> savedInventoriesMap = new HashMap<String, DD_Inventory>();

	public void savePlayerInventory(Player player) {

		// R�cup�ration de l'inventaire des armures et de l'xp
		ItemStack[] content = player.getInventory().getContents();
		ItemStack[] armor = player.getInventory().getArmorContents();
		float exp = player.getExp();
		int level = player.getLevel();

		// Creation d'un nouvel objet qui contient inventaire, armure et xp
		DD_Inventory inventory = new DD_Inventory(content, armor, exp, level);

		// Rajout dans la Map, reli�e au nom du joueur
		savedInventoriesMap.put(player.getName(), inventory);
	}

	public void loadPlayerInventory(Player player) {

		// Verifie que le joueur ait bien une sauvegarde d'inventaire
		if (savedInventoriesMap.get(player.getName()) != null) {
			// Recup�ration des objets sauv�s de la mort
			DD_Inventory inventory = savedInventoriesMap.get(player.getName());

			// D�claration des variable pour manipuler plus facilement
			ItemStack[] content = inventory.getContents();
			ItemStack[] armor = inventory.getArmorContents();
			float exp = inventory.getExp();
			int level = inventory.getLevel();

			// Attribution de l'inventaire et de l'armure
			player.getInventory().setContents(content);
			player.getInventory().setArmorContents(armor);

			// Fix sur l'event PlayerRespawnEvent avec l'xp Il n'est pas encore
			// possible de toucher l'xp sur un respawn event, le seul moyent est
			// de cr�er une t�che envoy�e au scheduler :/, comme quoi cet event
			// est capricieux !

			// Declaration des final utilis�s pour la t�che
			final Player fPlayer = player;
			final float fExp = exp;
			final int fLevel = level;

			// La t�che
			Runnable expTask = new Runnable() {
				@Override
				public void run() {

					fPlayer.setLevel(fLevel);
					fPlayer.setExp(fExp);
				}
			};

			// On la lance imm�diatement vu que c'est un bugfix.
			Bukkit.getScheduler().runTask(DeathDeceive.instance, expTask);

			// La restoration est arriv�e � son terme, on supprime le joueur et
			// son inventaire de la hashmap.
			savedInventoriesMap.remove(player.getName());

			// Si un message personnalis� existe, on l'envoit au joueur
			if (!(DeathDeceive.instance.confMessage.trim().equals("0")))
				player.sendMessage(DeathDeceive.instance.confMessage);
		}
	}
}
