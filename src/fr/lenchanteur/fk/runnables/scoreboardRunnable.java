package fr.lenchanteur.fk.runnables;

import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lenchanteur.fk.Main;
import fr.lenchanteur.fk.ScoreboardSign;
import fr.lenchanteur.fk.enumeration.FKStates;

public class scoreboardRunnable extends BukkitRunnable {

	private Main main;
	public scoreboardRunnable(Main main) { this.main = main; }
	
	int day = 1;
    int minute = 0;
    int secondes = 0;
	
	@Override
	public void run() {
		
		if(main.isState(FKStates.FINISH)) { cancel(); }
		
		if(main.isState(FKStates.PAUSE)) { 
			pauseTimer(); 
		} else {
			secondes++;
			if(secondes == 60) {
				minute++;
				secondes = 0;
			}
			if(minute == 20) {
				minute = 0;
				day++;
			}
			scoreboardAct(day, minute, secondes);
			if(secondes%2 == 0) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					main.actTablist(player);
				}
			}
			//if(day == 4 && minute == 0 && secondes == 1) {
			if(day == 1 && minute == 1 && secondes == 0) {
				activePvp();
				for(Player p : Bukkit.getOnlinePlayers()) {
					main.actTablist(p);
				}
			}
			//if(day == 5 && minute == 0 && secondes == 1) {
			if(day == 1 && minute == 1 && secondes == 30) {
				activeNether();
				for(Player p : Bukkit.getOnlinePlayers()) {
					main.actTablist(p);
				}
			}
			//if(day == 7 && minute == 0 && secondes == 1) {
			if(day == 1 && minute == 1 && secondes == 45) {
				activeEnd();
				for(Player p : Bukkit.getOnlinePlayers()) {
					main.actTablist(p);
				}
			}
			//if(day == 9 && minute == 0 && secondes == 1) {
			if(day == 1 && minute == 0 && secondes == 55) {
				activeAssault();
				for(Player p : Bukkit.getOnlinePlayers()) {
					main.actTablist(p);
				}
			}
			
			
			//if(day == 2 && minute == 10 && secondes == 0) {
			if(day == 1 && minute == 0 && secondes == 30) {
				eventChest();
			}
			
			//if(day == 5 && minute == 10 && secondes == 0) {
			if(day == 1 && minute == 0 && secondes == 45) {
				eventBOBOSS();
			}
		}
	}
	
	
	private void eventChest() {

		// coord chest : -83, 63, 90
		Random r = new Random();
		int x = -83;
		int y = 63;
		int z = 91;
		Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6Un coffre vient d'apparaitre ! En : X : §b" + x + "§6 / Y §b: " + y + "§6 / Z : §b" + z);
		Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6Allez-y vite ! Le PvP n'est pas encore activé !");
		
		Location locChest = new Location(Bukkit.getWorld("FK6"),x,y,z);
		locChest.getBlock().setType(Material.CHEST);
		
		Chest chest = (Chest) locChest.getBlock().getState();
		Inventory inv = chest.getInventory();
		
		inv.setItem(r.nextInt(inv.getSize()), new ItemStack(Material.DIAMOND, 2));
		inv.setItem(r.nextInt(inv.getSize()), new ItemStack(Material.IRON_SWORD, 1));
		inv.setItem(r.nextInt(inv.getSize()), new ItemStack(Material.BLAZE_POWDER, 3));
		inv.setItem(r.nextInt(inv.getSize()), new ItemStack(Material.LOG, 9));
		inv.setItem(r.nextInt(inv.getSize()), new ItemStack(Material.WOOL, 2));
		inv.setItem(r.nextInt(inv.getSize()), new ItemStack(Material.APPLE, 3));
		inv.setItem(r.nextInt(inv.getSize()), new ItemStack(Material.BREAD, 7));
		inv.setItem(r.nextInt(inv.getSize()), new ItemStack(Material.GOLD_BLOCK, 1));
		
	}

	
	private void eventBOBOSS() {
		int x = -36;
		int y = 95;
		int z = 138;
		Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §4§lBOBOSS §6vient d'apparaître ! En : X : §b" + x + "§6 / Y §b: " + y + "§6 / Z : §b" + z);
		Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6Attention à vous ! Le PvP est activé !");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "summon Giant " + x + " " + y + " " + z + " {CustomName:BOBOSS,CustomNameVisible:1,NoAI:1b,PersistenceRequired:1b,CanPickUpLoot:0b,HealF:50,Attributes:[{Name:\"generic.maxHealth\",Base:50},{Name:\"generic.followRange\",Base:39},{Name:\"generic.attackDamage\",Base:3}],Equipment:[{id:\"minecraft:diamond_sword\",tag:{display:{Name:\"Epée du BOBOSS\",Lore:[\"Vous avez récupérer cette épée\",\"en tuant le BOBOSS\"]},ench:[{id:19,lvl:2},{id:20,lvl:2}]},Count:1},{},{id:\"minecraft:golden_chestplate\",tag:{display:{Name:\"Plastron du BOBOSS\",Lore:[\"Vous avez récupérer cet Item\",\"en tuant le BOBOSS\"]},ench:[{id:3,lvl:4},{id:34,lvl:3}]},Count:1},{},{}],DropChances:[2F,0F,2F,0F,0F]}");
	}
	
	

	private void pauseTimer() {
		for(Entry<Player, ScoreboardSign> boards : main.boards.entrySet()) {
			boards.getValue().setLine(1, "§6Jour : §4§lPARTIE EN");
			boards.getValue().setLine(2, "§6> §4§lPAUSE");
		}
	}


	private void activePvp() {
		main.pvp = true;
		Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6Le pvp vient d'être activé !");
		for(Entry<Player, ScoreboardSign> boards : main.boards.entrySet()) {
			boards.getValue().setLine(4, "§6PVP : §a✔");
		}
		for(Player pl : Bukkit.getOnlinePlayers()) {
			pl.getWorld().playSound(pl.getLocation(), Sound.VILLAGER_DEATH, 20, 1);
		}
	}
	
	
	private void activeNether() {
		Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6Le nether vient d'être activé !");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -36 90 97 air");
		main.nether = true;
		for(Entry<Player, ScoreboardSign> boards : main.boards.entrySet()) {
			boards.getValue().setLine(5, "§6Nether : §a✔");
		}
		for(Player pl : Bukkit.getOnlinePlayers()) {
			pl.getWorld().playSound(pl.getLocation(), Sound.WITHER_DEATH, 20, 1);
		}
	}
	
	private void activeEnd() {
		main.end = true;
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -50 81 99 air");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -50 81 87 air");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -21 81 87 air");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -21 81 99 air");
		Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6L'End vient d'être activé !");
		for(Entry<Player, ScoreboardSign> boards : main.boards.entrySet()) {
			boards.getValue().setLine(6, "§6End : §a✔");
		}
		for(Player pl : Bukkit.getOnlinePlayers()) {
			pl.getWorld().playSound(pl.getLocation(), Sound.ENDERDRAGON_GROWL, 20, 1);
		}
	}
	
	private void activeAssault() {
		main.assault = true;
		Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6Les assaults viennent d'être activés !");
		for(Entry<Player, ScoreboardSign> boards : main.boards.entrySet()) {
			boards.getValue().setLine(7, "§6Assauts : §a✔");
		}
		for(Player pl : Bukkit.getOnlinePlayers()) {
			pl.getWorld().playSound(pl.getLocation(), Sound.ANVIL_USE, 20, 1);
		}
	}
	
	public void scoreboardAct(int day, int minute, int seconde) {
		for(Player pl : Bukkit.getOnlinePlayers()) {
			if(!main.boards.containsKey(pl)) { 
				ScoreboardSign sbc = new ScoreboardSign(pl, "§6Fallen Kingdoms I");
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
				main.boards.put(pl, sbc);
			}
			
			String min = "";
			String sec = "";
			
			if(minute < 10) {
				min = "0";
			} else {
				min = "";
			}
			
			if(seconde < 10) {
				sec = "0";
			} else {
				sec = "";
			}
				
			for(Entry<Player, ScoreboardSign> boards : main.boards.entrySet()) {
				boards.getValue().setLine(1, "§6Jour : §b" + day);
				boards.getValue().setLine(2, "§6> §b" + min + minute + ":" + sec + seconde);
			}
		}
	}
	
	
	
}
