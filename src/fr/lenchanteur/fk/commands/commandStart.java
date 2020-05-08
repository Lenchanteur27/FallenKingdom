package fr.lenchanteur.fk.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lenchanteur.fk.FKStart;
import fr.lenchanteur.fk.Main;
import fr.lenchanteur.fk.enumeration.FKStates;

public class commandStart implements CommandExecutor {

	private Main main;
	public commandStart(Main main) { this.main = main; }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		
		if(label.equalsIgnoreCase("start")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("§cSeul un joueur peut executer cette commande !");
				return false;
			}
			Player p = (Player) sender;
			if(p.getName().equalsIgnoreCase("lenchanteur") || p.isOp()) {

				if(!main.isState(FKStates.PREGAME)) {
					main.setState(FKStates.PREGAME);
					Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6Lancement de la partie...");
					FKStart start = new FKStart(main);
					start.runTaskTimer(main, 20, 20);
				}
			} else {
				p.sendMessage("§cSeul l'administrateur peut lancer la partie.");
			}
		}
		
		
		if(label.equalsIgnoreCase("pause")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("§cSeul un joueur peut executer cette commande !");
				return false;
			}
			Player p = (Player) sender;
			if(p.getName().equalsIgnoreCase("lenchanteur") || p.isOp()) {

				if(main.isState(FKStates.PAUSE)) {
					
					main.setState(FKStates.GAME);
					Bukkit.broadcastMessage("§b§l+-------------------------------------------+");
					Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6La partie vient de reprendre !");
					Bukkit.broadcastMessage("§b§l+-------------------------------------------+");
					for(Player pl : Bukkit.getOnlinePlayers()) {
						pl.getWorld().playSound(pl.getLocation(), Sound.ANVIL_LAND, 20, 1);
					}
					
				} else if(main.isState(FKStates.GAME)) {
					
					main.setState(FKStates.PAUSE);
					Bukkit.broadcastMessage("§b§l+-------------------------------------------+");
					Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6La partie vient d'être mise en pause !");
					Bukkit.broadcastMessage("§b§l+-------------------------------------------+");
					for(Player pl : Bukkit.getOnlinePlayers()) {
						pl.getWorld().playSound(pl.getLocation(), Sound.LEVEL_UP, 20, 1);
					}
					
				} else {
					p.sendMessage("§b§l[§6FKI§b§l] §cCommande indisponible actuellement !");
				}
				
			} else {
				p.sendMessage("§cSeul l'administrateur peut mettre en pause la partie.");
			}
		}
		
		
		
		
		return true;
	}

}
