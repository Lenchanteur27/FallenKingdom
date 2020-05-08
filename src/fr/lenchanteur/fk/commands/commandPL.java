package fr.lenchanteur.fk.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandPL implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		
		if(label.equalsIgnoreCase("pl") || label.equalsIgnoreCase("plugins")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.isOp()) {
					Bukkit.dispatchCommand(p, "bukkit:pl");
				} else {
					p.sendMessage("§cNop, t'a pas a voir ça :c");
					return false;
				}
			}
		}
		
		
		
		return false;
	}

}
