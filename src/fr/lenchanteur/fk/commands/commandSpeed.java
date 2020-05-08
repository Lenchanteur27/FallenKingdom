package fr.lenchanteur.fk.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.lenchanteur.fk.Main;
import fr.lenchanteur.fk.enumeration.FKStates;

public class commandSpeed implements CommandExecutor {

	private Main main;
	public commandSpeed(Main main) { this.main = main; }
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cSeul un joueur peut executer cette commande !");
			return false;
		}
		
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("speed")) {
			
			if(p.isOp() || p.getName().equals("lenchanteur") || (p.getGameMode().equals(GameMode.SPECTATOR) && main.isState(FKStates.GAME))) {
				if(args.length == 0) {
					p.removePotionEffect(PotionEffectType.SPEED);
					p.setFlySpeed(0);
					return false;
				}
				int vit = Integer.parseInt(args[0]);
				
				if(p.isOnGround()) {
					p.removePotionEffect(PotionEffectType.SPEED);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, vit));
					p.sendMessage("§aVitesse au sol fixée !");
				} else if(p.isFlying()) {
					if(vit > 10) {
						p.sendMessage("§cValeur trop grande !");
						return false;
					} else {
						vit = vit / 10;
					}
					p.setFlySpeed(vit);
					p.sendMessage("§aVitesse en vol fixée !");
				}
			} else {
				p.sendMessage("§cVous n'avez pas accez à cette commande !");
				return false;
			}
			
		}
		
		return true;
	}

}
