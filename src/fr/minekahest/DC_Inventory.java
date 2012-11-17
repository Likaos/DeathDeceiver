package fr.minekahest;

import org.bukkit.inventory.ItemStack;

public class DC_Inventory {

	// Variable de notre classe
	private ItemStack[] armor;
	private ItemStack[] content;
	private float exp;
	private int level;

	// Contruscteur
	public DC_Inventory(ItemStack[] content, ItemStack[] armor, float exp,
			int level) {
		this.content = content;
		this.armor = armor;
		this.exp = exp;
		this.level = level;
	}

	// Getter
	public ItemStack[] getContents() {
		return this.content;
	}

	public ItemStack[] getArmorContents() {
		return armor;
	}

	public float getExp() {
		return exp;
	}

	public int getLevel() {
		return level;
	}
}
