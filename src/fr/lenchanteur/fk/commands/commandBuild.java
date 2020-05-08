package fr.lenchanteur.fk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lenchanteur.fk.Main;

public class commandBuild implements CommandExecutor {

	private Main main;
	public commandBuild(Main main) { this.main = main; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player)) {
			sender.sendMessage("§cSeul un joueur !");
			return false;
		}
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("build")) {
			if(p.isOp() || p.getName().equalsIgnoreCase("GoKeXx_")) {
				
				if(main.buildModePlayers.contains(p)) {
					main.buildModePlayers.remove(p);
					p.sendMessage("§cSortie du mode build");
				} else {
					main.buildModePlayers.add(p);
					p.sendMessage("§aPassage en mode build");
				}
				

			}
		}
		
		
		
		return false;
	}

}
