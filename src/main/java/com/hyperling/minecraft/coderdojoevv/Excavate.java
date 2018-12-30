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
// Arguments are a number and/or the values "up" or "down"
public class Excavate {
	final int maxBlocks = 128;
	
	public Excavate(JavaPlugin plugin, Player player, String[] args) {
		
		long startTime = System.currentTimeMillis();

		// Get the arguments
		int numBlocks = 0;
		int direction = 0;
		for (String arg : args) {
			try {
				numBlocks = Integer.parseInt(arg);
			}
			catch (Exception e) {
				if (numBlocks == 0) {
					plugin.getLogger().info("Failed to convert argument to size, defaulting to 8.");
					numBlocks = 8;
				}
				
				switch (arg.toLowerCase()) {
				case "up":
					direction = 1;
					break;
				case "down":
					direction = -1;
					break;
				}
			}
		}
		
		// Validate the number of blocks being excavated
		if (numBlocks < 0) {
			plugin.getLogger().info("Negative number was passed, not excavating.");
			return;
		}
		else if (numBlocks > maxBlocks) {
			plugin.getLogger().info("Maxumum allowed is currently "+maxBlocks);
			numBlocks = maxBlocks;
		}
		
		plugin.getLogger().info("Gathering all blocks with cube size of " + numBlocks + " for " + player.getName() + ".");
		player.sendMessage("Excavating "+numBlocks+" by "+numBlocks+" cube...");

		numBlocks /= 2;
		direction *= numBlocks;

		plugin.getLogger().info("Direction="+direction+", numBlocks="+numBlocks+".");
		
		Location l = player.getLocation();
		int x = l.getBlockX();
		int y = l.getBlockY();// + 1; // Ensures player does not fall when using "up" argument.
		int z = l.getBlockZ();

		plugin.getLogger().info("Player is at ("+x+","+y+","+z+").");
		
		World w = l.getWorld();

		ArrayList<ItemStack> items = new ArrayList<ItemStack>();	

		// Loop through all the blocks in the specified area around the user
		for (int deltaX = x - numBlocks; deltaX < x + numBlocks; deltaX++) {
			for (int deltaY = y - numBlocks + direction; deltaY < y + numBlocks + direction; deltaY++) {
				for (int deltaZ = z - numBlocks; deltaZ < z + numBlocks; deltaZ++) {
					Block b = w.getBlockAt(deltaX, deltaY, deltaZ);
					//plugin.getLogger().info("Found "+b.getType().name()+" at ("+deltaX+","+deltaY+","+deltaZ+").");
					
					//plugin.getLogger().info("Block is not bedrock: "+!b.getType().name().equals(Material.BEDROCK.name()));
					//plugin.getLogger().info("Block is not air: "+!b.getType().name().equals(Material.AIR.name()));
					
					// Ignore bedrock and air
					if (!b.getType().name().equals(Material.BEDROCK.name()) 
							&& !b.getType().name().equals(Material.AIR.name())) {
						//plugin.getLogger().info("Adding "+b.getType().name()+" to array.");

						// Add 1 of the block to the array for adding to the user's inventory
						ItemStack oldStack = new ItemStack(b.getType());
						ItemStack newStack = new ItemStack(b.getType());
						for (ItemStack item : items) {
							//plugin.getLogger().info("Stack name is "+item.getType().name()+", block name is "+i.getType().name()+".");
							if (item.getType().name().equals(newStack.getType().name())) {
								oldStack = item;
								newStack.setAmount(item.getAmount() + 1);
								break;
							}
						}
						items.remove(oldStack);
						items.add(newStack);
						//plugin.getLogger().info("Added a "+b.getType().name()+", quantity is "+i.getAmount()+".");
						
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
		
		long deltaTime = System.currentTimeMillis() - startTime;
		plugin.getLogger().info("Finished excavating in "+deltaTime+"ms.");
		player.sendMessage("Excavation finished. It took "+deltaTime/1000+" seconds.");
	}
}
