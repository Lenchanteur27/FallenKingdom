package fr.lenchanteur.fk;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lenchanteur.fk.enumeration.FKStates;
import fr.lenchanteur.fk.runnables.scoreboardRunnable;

public class FKStart extends BukkitRunnable {

	private Main main;
	private int timer = 15;
	
	public FKStart(Main main) { this.main = main; }

	@Override
	public void run() {
		
		for(Player pls : Bukkit.getOnlinePlayers()) {
			pls.setLevel(timer);
			pls.getInventory().clear();
		}
		
		if(Bukkit.getOnlinePlayers().size() < 1) {
			Bukkit.broadcastMessage("§cLancement annulé : nombre de joueur inssufisant");
			main.setState(FKStates.WAITING);
			cancel();
		}
		
		if(timer == 15 || timer == 10 || timer == 5 || timer == 3 || timer == 2 || timer == 1 ) {
			for(Player pls : Bukkit.getOnlinePlayers()) {
				main.title.sendTitle(pls, "§bLancement de la partie", "§7Dans : " + timer + " seconde(s)", 20);
			}
			for(Player pl : Bukkit.getOnlinePlayers()) {
				pl.getWorld().playSound(pl.getLocation(), Sound.ORB_PICKUP, 20, 1);
			}
		}
		
		if(timer == 0) {
			cancel();
			for(Player pls : Bukkit.getOnlinePlayers()) {
				main.title.sendTitle(pls, "§bLancement de la partie", "§7§lTéléportation...", 30);
			}
			for(Player pl : Bukkit.getOnlinePlayers()) {
				pl.getWorld().playSound(pl.getLocation(), Sound.ORB_PICKUP, 20, 1);
			}
			lauchGame();
		}
		
		timer--;
	}

	
	
	private void lauchGame() {
		
		for(Player pls : Bukkit.getOnlinePlayers()) {
			int team = main.getTeam(pls);
			
			pls.getInventory().clear();
			pls.setGameMode(GameMode.SURVIVAL);
			pls.setFoodLevel(20);
			pls.setHealth(pls.getMaxHealth());
			pls.setLevel(0);
			
			main.kill.put(pls.getName(), 0);
			main.death.put(pls.getName(), 0);
			
			int x,y,z;
			switch (team) {
			case 0:
				x = main.getConfig().getInt("teams.violet.x");
				y = main.getConfig().getInt("teams.violet.y");
				z = main.getConfig().getInt("teams.violet.z");
				pls.teleport(new Location(Bukkit.getWorld("FK6"), x, y, z));
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams join violet " + pls.getName());
				main.violet.add(pls.getName());
				break;
			case 1:
				x = main.getConfig().getInt("teams.orange.x");
				y = main.getConfig().getInt("teams.orange.y");
				z = main.getConfig().getInt("teams.orange.z");
				pls.teleport(new Location(Bukkit.getWorld("FK6"), x, y, z));
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams join orange " + pls.getName());
				main.orange.add(pls.getName());
				break;
			case 2:
				x = main.getConfig().getInt("teams.cyan.x");
				y = main.getConfig().getInt("teams.cyan.y");
				z = main.getConfig().getInt("teams.cyan.z");
				pls.teleport(new Location(Bukkit.getWorld("FK6"), x, y, z));
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams join cyan " + pls.getName());
				main.cyan.add(pls.getName());
				break;
				
			case 3:
				main.spec.add(pls.getName());
				pls.setGameMode(GameMode.SPECTATOR);
				break;
				
			default:
				break;
			}
			main.actTablist(pls);
		}
		main.boards.clear();
		scoreboardIni();
		main.setState(FKStates.GAME);
		scoreboardRunnable start = new scoreboardRunnable(main);
		start.runTaskTimer(main, 20, 20);
		
	}
	
	private void scoreboardIni() {
		
		for(Player pls : Bukkit.getOnlinePlayers()) {
			ScoreboardSign sbc = new ScoreboardSign(pls, "§6Fallen Kingdoms I");
			sbc.create();
			sbc.setLine(0, "§e");
			sbc.setLine(1, "§6Jour : §b" + 1);
			sbc.setLine(2, "§6> §b" + 00 + ":" + 00);
			sbc.setLine(3, "§a§b§l---------");
			sbc.setLine(4, "§6PVP : §cX");
			sbc.setLine(5, "§6Nether : §cX");
			sbc.setLine(6, "§6End : §cX");
			sbc.setLine(7, "§6Assauts : §cX");
			sbc.setLine(8, "§b§l---------");
			sbc.setLine(9, "§6Kill : §b" + 0);
			sbc.setLine(10, "§6Death : §b" + 0);
			main.boards.put(pls, sbc);
		}
		
	}
	

}



/*			int xMin = main.getConfig().getInt("teams." + team + ".xMin");
			int xMax = main.getConfig().getInt("teams." + team + ".xMax");
			int zMin = main.getConfig().getInt("teams." + team + ".zMin");
			int zMax = main.getConfig().getInt("teams." + team + ".zMax");*/
