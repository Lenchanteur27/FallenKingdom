package fr.lenchanteur.fk.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandPB implements CommandExecutor {

	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(label.equalsIgnoreCase("pb")) {
				for(Player pls : Bukkit.getOnlinePlayers()) {
					if(pls.isOp()) {
						pls.sendMessage("§c§l⚠    - Le joueur : §d" + p.getName() + " §cà un problème !");
						p.sendMessage("§d§l⚠  - Un organisateur va s'occuper de vous !");
					}
				}
			}
			if(label.equalsIgnoreCase("question")) {
				if(args.length == 0) return false;
				String msg = "";
				for(int i = 0; i < args.length; i++) {
					msg = msg + " " + args[i];
				}
				for(Player pls : Bukkit.getOnlinePlayers()) {
					if(pls.isOp()) {
						pls.sendMessage("§c§l⚠    - Le joueur : §d" + p.getName() + " §cdemande : §d" + msg);
						p.sendMessage("§d§l⚠  - Un organisateur a reçu votre demande !");
					}
				}
			}
			
			
			if(label.equalsIgnoreCase("gm")) {
				if(!p.isOp()) {
					p.sendMessage("§cVous n'avez pas accès à cette commande !");
					return false;
				}
				if(args.length != 0) {
					if(args[0].equals("1")) {
						p.setGameMode(GameMode.CREATIVE);
					}
					if(args[0].equals("2")) {
						p.setGameMode(GameMode.ADVENTURE);
					}
					if(args[0].equals("3")) {
						p.setGameMode(GameMode.SPECTATOR);
					}
					if(args[0].equals("0")) {
						p.setGameMode(GameMode.SURVIVAL);
					}
				}
			}
			
		}
		
		return false;
	}

}
