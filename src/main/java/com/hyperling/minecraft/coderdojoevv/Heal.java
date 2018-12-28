package com.hyperling.minecraft.coderdojoevv;

import org.bukkit.entity.Player;
import org.bukkit.attribute.Attribute;
import org.bukkit.plugin.java.JavaPlugin;

public class Heal {
	public Heal (JavaPlugin plugin, Player player) {
		double health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
		int food = (int) health;
		
		player.setHealth(health);
		player.setFireTicks(0);
		player.setFoodLevel(food);
		plugin.getLogger().info(player.getName() + " has healed successfully.");
	}
}
