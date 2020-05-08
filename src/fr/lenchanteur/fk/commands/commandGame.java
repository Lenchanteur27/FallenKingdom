package fr.lenchanteur.fk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lenchanteur.fk.Main;

public class commandGame implements CommandExecutor {

	private Main main;
	public commandGame(Main main) { this.main = main; }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cSeul un joueur peut executer cette commande");
			return false;
		}
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("rules")) {
			p.sendMessage("§b|----     §6Fallen Kingdom I     §b----");
			p.sendMessage("§b|    §6Règles :");
			p.sendMessage("§b|    §cJour :");
			p.sendMessage("§b|  §c-PVP : §dJour 4");
			p.sendMessage("§b|  §c-Nether : §dJour 5");
			p.sendMessage("§b|  §c-End : §dJour 7");
			p.sendMessage("§b|  §c-Assaults : §dJour 9");
			p.sendMessage("§b|----     §6Fallen Kingdom I     §b----");
			p.sendMessage("§b|    §cAssaults :");
			p.sendMessage("§b|  §cPour débloquer la phase des assaults (possibilité de poser des tnts), il vous faudra completer votre totem dans le temple.");
			p.sendMessage("§b|  §cAfin de conpléter votre totem, vous devez réaliser un nouveau craft, vous pouvez le voir dans le temple à côté de vos totem.");
			p.sendMessage("§b|  §cIl vous faudrat aussi trouvé une peau de lapin ainsi qu'une patte de lapin (chaque joueur)");
			p.sendMessage("§b|----     §6Fallen Kingdom I     §b----");
			p.sendMessage("§b|  §cA sa mort (en cas de kill), chaque joueur dropperas une pomme d'or");
			p.sendMessage("§b|  §cVous pouvez parler avec votre équipe, ou parler à tous les joueurs en commancant vos message par §b\"!\"");
			p.sendMessage("§b|  §cVous pouvez partager vos coordonées avec vos coéquipier grâce à la commande §b\"/sc\"");
			p.sendMessage("§b|----     §6Fallen Kingdom I     §b----");
			p.sendMessage("§b|  §aDes évènements aléatoires se rédoulerons, vous reverez un message dans le tchat, un holograme, et un son. Soyez les premiers !");
			p.sendMessage("§b|----     §6Fallen Kingdom I     §b----");
			p.sendMessage("§b|  §cIl est interdit de bloquer les portails (nether et end), de les détruire ou de les pièger.");
			p.sendMessage("§b|  §cLes \"gentlemans-rules\" doivent être appliquée (ex: laisser de la nether wart dans le nether...).");
			p.sendMessage("§b|  §cVous devez adopter un langage exemplaire dans le tchat.");
			p.sendMessage("§b|  §cIl est interdit de casser des blocs dans le temple.");
			p.sendMessage("§b|  §cIl est §aautorisé §cde piller les coffres énemis !");
			p.sendMessage("§b|  §cIl est interdit de use-bug. Si vous rencontrez un problème, utilisez la commande §d\"/pb\".");
			p.sendMessage("§b|  §cSi vous avez la moindre question, utilisez la commande §d\"/question <votre question>\".");
			p.sendMessage("§b|----     §6Fallen Kingdom I     §b----");
			p.sendMessage("§b|  §4§lSi vous enfreignez le règlement, vous vous exposez à des sanctions. Par défaut, chaque manquement donnera une pomme d'or à tous les joueurs des autres équipes.");
			p.sendMessage("§b|----     §6Fallen Kingdom I     §b----");
		}
		
		
		
		if(label.equalsIgnoreCase("sc")) {
			double x = (double) (Math.round(p.getLocation().getX() * 10) / 10);
			double y = (double) (Math.round(p.getLocation().getY() * 10) / 10);
			double z = (double) (Math.round(p.getLocation().getZ() * 10) / 10);
			for(Player pl : main.getPlayerInTeam(p)) {
				pl.sendMessage("§b§l[§6FKI§b§l] §e" + p.getName() + " : §b§lX :§e§l " + x + " / §b§lY :§e§l " + y + " / §b§lZ :§e§l " + z);
			}
		}
		
		
		
		return false;
	}

}
