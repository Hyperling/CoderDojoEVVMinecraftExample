package com.hyperling.minecraft.coderdojoevv;

import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class Invincible implements Listener{
	final JavaPlugin plugin;
	final String keyValue = "INVINCIBLE";
	
	// Used when registering events.
	public Invincible (JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	// Set player's metadata for invincible trait.
	// Used in JavaPlugin class's onCommand()
	public Invincible (JavaPlugin plugin, Player player, String[] args) {
		this.plugin = plugin;
		boolean invincible;
		
		// Get default toggle value
		try {
			invincible = !player.hasMetadata(keyValue);
		} catch (Exception e) {
			plugin.getLogger().info("Getting metadata for "+keyValue+" returned "+e.getMessage());
			plugin.getLogger().info("Defaulting to true.");
			invincible = true;
		}

		// Check for arguments
		for (String arg : args) {
			switch (arg.toLowerCase()) {
			case "on":
				invincible = true;
				break;
			case "off":
				invincible = false;
				break;
			default:
				break;
			}
		}
		
		// Set the metadata
		if (invincible) {
			player.setMetadata(keyValue, new FixedMetadataValue(plugin, invincible));
			plugin.getLogger().info("Player is now invincible.");
			player.sendMessage("You are now invincible.");
		}
		else {
			player.removeMetadata(keyValue, plugin);
			plugin.getLogger().info("Player is no longer invincible.");
			player.sendMessage("You are no longer invincible.");
		}
	}
	
	// Cancel damage done to invincible players.
	@EventHandler
	public void onDamage (EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (player.hasMetadata(keyValue)) {
				e.setCancelled(true);
				player.setFireTicks(0);
			}
		}
	}
	
}
