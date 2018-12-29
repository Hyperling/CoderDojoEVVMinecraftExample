package com.hyperling.minecraft.coderdojoevv;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoderDojoEVV extends JavaPlugin implements Listener {
    
	@Override
	public void onEnable() {
		getLogger().info("onEnable has been accessed.");
		
		DiamondItems di = new DiamondItems(this);
		getServer().getPluginManager().registerEvents(di, this);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("onDisable has been accessed.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;

		getLogger().info(player.getName() + " issued command with label " + label + ".");
		
		switch (label) {
		case "heal":
			new Heal(this, player);
			break;
		case "excavate":
			int size;
			try {
				size = Integer.parseInt(args[0]);
			}
			catch (Exception e) {
				getLogger().info("Failed to convert argument to size, defaulting to 8.");
				size = 8;
			}
			new Excavate(this, player, size);
		}
		
		getLogger().info("Finished onCommand().");
		return super.onCommand(sender, command, label, args);
	}
}
