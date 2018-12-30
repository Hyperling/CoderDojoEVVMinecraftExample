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
		
		Invincible i = new Invincible(this);
		getServer().getPluginManager().registerEvents(i, this);
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("onDisable has been accessed.");
		super.onDisable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
	
			getLogger().info(player.getName() + " issued command with label " + label + ".");
			
			switch (label.toLowerCase()) {
			case "heal":
				new Heal(this, player);
				break;
			case "excavate":
				new Excavate(this, player, args);
				break;
			case "invincible":
				new Invincible(this, player, args);
				break;
			}
		}
		
		getLogger().info("Finished onCommand().");
		return super.onCommand(sender, command, label, args);
	}
}
