package com.hyperling.minecraft.coderdojoevv;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class DiamondItems implements Listener {
	final JavaPlugin plugin;
	
	public DiamondItems (JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		plugin.getLogger().info("Running onRespawn.");
		Player player = event.getPlayer();
		final PlayerInventory inv = player.getInventory();

		if (plugin.getServer().getDefaultGameMode() != GameMode.CREATIVE) {
			giveDiamondItems(inv);			
			player.sendMessage("Diamond items have been returned.");
		}
		plugin.getLogger().info("Finished onRespawn().");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		plugin.getLogger().info("Running onJoin.");
		Player player = event.getPlayer();
		
		player.sendMessage("Welcome to the server, " + player.getDisplayName() + "!");

		final PlayerInventory inv = player.getInventory();

		// Give diamond tools and food if not in creative
		if (plugin.getServer().getDefaultGameMode() != GameMode.CREATIVE) {
			giveDiamondItems(inv);
			player.sendMessage("Enjoy your items!");
		}
		else {
			inv.clear();
			plugin.getLogger().info("Inventory has been cleared for Creative mode.");
		}

		plugin.getLogger().info("Finished onJoin().");
	}
	
	public void giveDiamondItems(PlayerInventory inv) {
		ItemStack item;
		item = new ItemStack(Material.DIAMOND_SWORD);
		inv.remove(Material.DIAMOND_SWORD);
		inv.addItem(item);
		item = new ItemStack(Material.DIAMOND_AXE);
		inv.remove(Material.DIAMOND_AXE);
		inv.addItem(item);
		item = new ItemStack(Material.DIAMOND_PICKAXE);
		inv.remove(Material.DIAMOND_PICKAXE);
		inv.addItem(item);
		item = new ItemStack(Material.DIAMOND_SPADE);
		inv.remove(Material.DIAMOND_SPADE);
		/* For 1.13 API
		item = new ItemStack(Material.DIAMOND_SHOVEL);
		inv.remove(Material.DIAMOND_SHOVEL);
		*/
		inv.addItem(item);
		item = new ItemStack(Material.DIAMOND_HOE);
		inv.remove(Material.DIAMOND_HOE);
		inv.addItem(item);
		
		// Give food
		item = new ItemStack(Material.GOLDEN_APPLE);
		inv.remove(Material.GOLDEN_APPLE);
		item.setAmount(64);
		inv.addItem(item);
		
		// These work
		item = new ItemStack(Material.DIAMOND_BOOTS);
		inv.setBoots(item);		
		item = new ItemStack(Material.DIAMOND_LEGGINGS);
		inv.setLeggings(item);		
		item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		inv.setChestplate(item);		
		item = new ItemStack(Material.DIAMOND_HELMET);
		inv.setHelmet(item);
	}
	
}
