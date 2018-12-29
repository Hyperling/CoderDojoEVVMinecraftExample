package com.hyperling.minecraft.coderdojoevv;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

// Remove all blocks around the player by the size they pass through
public class Excavate {
	public Excavate(JavaPlugin plugin, Player player, int numBlocks) {
		plugin.getLogger().info("Gathering all blocks with radius of " + numBlocks + " from " + player.getName() + ".");
		
		Location l = player.getLocation();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		
		World w = l.getWorld();

		ArrayList<ItemStack> items = new ArrayList<ItemStack>();	

		// Loop through all the blocks in the specified area around the user
		for (int deltaX = x - numBlocks; deltaX < x + numBlocks; deltaX++) {
			for (int deltaY = y - numBlocks; deltaY < y + numBlocks; deltaY++) {
				for (int deltaZ = z - numBlocks; deltaZ < z + numBlocks; deltaZ++) {
					Block b = w.getBlockAt(deltaX, deltaY, deltaZ);
					plugin.getLogger().info("Found "+b.getType().name()+" at ("+deltaX+","+deltaY+","+deltaZ+").");
					
					plugin.getLogger().info("Block is not bedrock: "+!b.getType().name().equals(Material.BEDROCK.name()));
					plugin.getLogger().info("Block is not air: "+!b.getType().name().equals(Material.AIR.name()));
					
					// Ignore bedrock and air
					if (!b.getType().name().equals(Material.BEDROCK.name()) 
							&& !b.getType().name().equals(Material.AIR.name())) {
						plugin.getLogger().info("Adding "+b.getType().name()+" to array.");

						// Add 1 of the block to the array for adding to the user's inventory
						ItemStack i = new ItemStack(b.getType());		
						for (ItemStack item : items) {
							if (item.getType().name().equals(i.getType().name())) {
								i.setAmount(item.getAmount() + 1);
								break;
							}
						}
						items.add(i);
						plugin.getLogger().info("Added a "+b.getType().name()+", quantity is "+i.getAmount()+".");
						
						// Remove the block
						b.setType(Material.AIR);
					}
				}
			}
		}
		
		// Put the items in the player's inventory
		final PlayerInventory pi = player.getInventory();
		for (ItemStack item : items) {
			plugin.getLogger().info("Adding "+item.getAmount()+" "+item.getType().name()+" to "+player.getName()+".");
			pi.addItem(item);
		}
		plugin.getLogger().info("Finished excavating.");
	}
}
