package fr.lenchanteur.fk.listeners;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import fr.lenchanteur.fk.Main;
import fr.lenchanteur.fk.ScoreboardSign;
import fr.lenchanteur.fk.enumeration.FKStates;
import fr.lenchanteur.fk.team.Team;

public class FKListeners implements Listener {
	
	private Main main;
	public FKListeners(Main main) { this.main = main; }
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player player = e.getPlayer();
		
		if(!main.isState(FKStates.WAITING) && !main.isState(FKStates.PREGAME)) {
			e.setJoinMessage("§b§l[§6FKI§b§l] §8[§2+§8] §b" + player.getName());
			actKD();
			if(main.violet.contains(player.getName())) {
				player.setGameMode(GameMode.SURVIVAL);
				main.title.sendTitle(player, "§7§oReconnexion...", "", 25);
				main.addPlayer(player, main.getTeams().get(0));
			} else if(main.orange.contains(player.getName())) {
				player.setGameMode(GameMode.SURVIVAL);
				main.title.sendTitle(player, "§7§oReconnexion...", "", 25);
				main.addPlayer(player, main.getTeams().get(1));
			} else if(main.cyan.contains(player.getName())) {
				player.setGameMode(GameMode.SURVIVAL);
				main.title.sendTitle(player, "§7§oReconnexion...", "", 25);
				main.addPlayer(player, main.getTeams().get(2));
			} else {
				player.setGameMode(GameMode.SPECTATOR);
				main.title.sendTitle(player, "§7§oVous êtes en mode", "§7§ospéctateur", 30);
			}
			return;
		}
		player.teleport(new Location(Bukkit.getWorld("FK6"), -35.5, 120, 89.5,180f, 2f ));
		main.blockNether();
		main.blockEnd();
		player.setLevel(0);
		main.title.sendTitle(player, "§bFallenKingdom", "§eBienvenue sur le serveur", 25);
		//setup player
		player.getInventory().clear();
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		e.setJoinMessage("§b§l[§6FKI§b§l] §6Un combatant vient de rejoindre l'aventure, il s'agît de §b" + player.getName());
		int i = 3;
		for(Team team : main.getTeams()) {
			player.getInventory().setItem(i, team.getIcon());
			i++;
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack it = e.getItem();
		Action action = e.getAction();
		
		if(main.isState(FKStates.WAITING)) {
			if(action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
				if(it != null && it.getType() == Material.WOOL) {
					e.setCancelled(true);
					return;
				}
			}
		}
		
		if(main.isState(FKStates.WAITING)) {
			if(it != null && it.getType() == Material.WOOL) {
				for(Team team : main.getTeams()) {
					if(team.getWoolData() == it.getData().getData()) {
						main.addPlayer(player, team);
						continue;
					}
					if(team.getPlayers().contains(player)) {
						team.getPlayers().remove(player);
					}
				}
			}
		}
	}
	
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			e.setCancelled(!main.pvp);
		}
	}
	
	@EventHandler
	public void onKDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(p.isDead()) {
			p.getKiller();
			if(p.getKiller() instanceof Player) {
				String killer = p.getKiller().getName();
				String killerT = "", killedT = "";
				String killed = p.getName();
				switch (main.getTeam(p)) {
				case 0:
					killedT = "§5";
					break;
				case 1:
					killedT = "§6";
					break;
				case 2:
					killedT = "§3";
					break;
				case 3:
					killedT = "§7";
					break;
				default:
					break;
				}
				switch (main.getTeam(p.getKiller())) {
				case 0:
					killerT = "§5";
					break;
				case 1:
					killerT = "§6";
					break;
				case 2:
					killerT = "§3";
					break;
				case 3:
					killerT = "§7";
					break;
				default:
					break;
				}
				e.setDeathMessage(killedT + killed + " §ea été tué par " + killerT + killer);
				int nbKillKiller = main.getKills(p.getKiller()) + 1;
				main.kill.put(p.getKiller().getName(), nbKillKiller);
				int nbDeathKilled = main.getDeath(p) + 1;
				main.death.put(p.getName(), nbDeathKilled);
				actKD();
				for(Player pl : Bukkit.getOnlinePlayers()) {
					pl.getWorld().playSound(pl.getLocation(), Sound.CAT_HIT, 20, 1);
				}
				return;
			}
			int nbDeathKilled = main.getDeath(p) + 1;
			main.death.put(p.getName(), nbDeathKilled);
			actKD();
			String killedT = "";
			switch (main.getTeam(p)) {
			case 0:
				killedT = "§5";
				break;
			case 1:
				killedT = "§6";
				break;
			case 2:
				killedT = "§3";
				break;
			case 3:
				killedT = "§7";
				break;
			default:
				break;
			}
			e.setDeathMessage(killedT + p.getName() + " §eest mort");
			for(Player pl : Bukkit.getOnlinePlayers()) {
				pl.getWorld().playSound(pl.getLocation(), Sound.BAT_DEATH, 20, 1);
			}
		}
	}
	
	
	@EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if(main.isState(FKStates.WAITING)) {
        	e.setCancelled(true);
        }
    }
	
	
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		e.setCancelled(true);
		if(p.getName().equalsIgnoreCase("lenchanteur")) {
			Bukkit.broadcastMessage("§c[Organisateur] lenchanteur : " + e.getMessage());
			return;
		}
		
		if(main.isState(FKStates.WAITING)) {
			for(Team team : main.teams) {
				if(team.getPlayers().contains(p)) {
					Bukkit.broadcastMessage(team.getTag() + team.getName() + " " + p.getName() + " : " + e.getMessage());
					return;
				} else {
					Bukkit.broadcastMessage("§7§o[Spectateur] " + p.getName() + " : " + e.getMessage());
					return;
				}
			}
		} else {
			if(e.getMessage().startsWith("!")) {
				String pref = "";
				int a = main.getTeam(p);
				switch (a) {
				case 0:
					pref = main.getConfig().getString("teams.violet.color").replace("&", "§") + main.getConfig().getString("teams.violet.name");
					break;
				case 1:
					pref = main.getConfig().getString("teams.orange.color").replace("&", "§") + main.getConfig().getString("teams.orange.name");
					break;
				case 2:
					pref = main.getConfig().getString("teams.cyan.color").replace("&", "§") +  main.getConfig().getString("teams.cyan.name");
					break;
				case 3:
					break;
				default:
					break;
				}
				for(Player pl : Bukkit.getOnlinePlayers()) {
					pl.sendMessage("§8§l[§6GlobalChat§8§l] " + pref + " " + p.getName() + " : " + e.getMessage().substring(1));
				}
			} else {
				if(main.getTeam(p) == 3) {
					for(Player pl : main.getPlayerInTeam(p)) {
						pl.sendMessage("§8§l[§7Spectateur§8§l] §7" + p.getName() + " : " + e.getMessage());
					}
				} else {
					for(Player pl : main.getPlayerInTeam(p)) {
						pl.sendMessage("§8§l[§6TeamChat§8§l] §7" + p.getName() + " : " + e.getMessage());
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		if(main.buildModePlayers.contains(e.getPlayer())) {
			e.setCancelled(false);
		}
		
		if(e.getPlayer().getName().equalsIgnoreCase("lenchanteur")) {
			return;
		}
		
		if(e.getBlock().getType() == Material.BEACON) {
			if(!e.getItemInHand().hasItemMeta()) {
				e.setCancelled(true);
				return;
			}
			if(!e.getItemInHand().getItemMeta().hasDisplayName()) {
				e.setCancelled(true);
				return;
			}
			if(e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6§k!!§r  §d§lTête de Totem  §6§k!!")) {
				if(e.getBlock().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.FENCE) && e.getBlock().getLocation().subtract(0, 2, 0).getBlock().getType().equals(Material.WOOL) && e.getBlock().getLocation().subtract(0, 3, 0).getBlock().getType().equals(Material.BEACON)) {
					Block blockLaine = e.getBlock().getLocation().subtract(0, 2, 0).getBlock();
					int team = main.getTeam(e.getPlayer());
					switch (team) {
					case 0:
						if(blockLaine.getData() == (byte)10) {
							e.setCancelled(false);
							main.totemValid(e.getPlayer());
							return;
						} else {
							e.setCancelled(true);
							e.getPlayer().sendMessage("§cVous ne pouvez poser votre totem que sur votre emplacement !");
						}
						break;
					case 1:
						if(blockLaine.getData() == (byte)1) {
							e.setCancelled(false);
							main.totemValid(e.getPlayer());
							return;
						} else {
							e.setCancelled(true);
							e.getPlayer().sendMessage("§cVous ne pouvez poser votre totem que sur votre emplacement !");
						}
						break;
					case 2:
						if(blockLaine.getData() == (byte)9) {
							e.setCancelled(false);
							main.totemValid(e.getPlayer());
							return;
						} else {
							e.setCancelled(true);
							e.getPlayer().sendMessage("§cVous ne pouvez poser votre totem que sur votre emplacement !");
						}
						break;
					default:
						break;
					}
				}
				return;
			}
		}
		
		if(main.isState(FKStates.WAITING) || main.isState(FKStates.PREGAME)) {
			e.setCancelled(true);
			return;
		}
		
		if(e.getBlock().getType() == Material.TNT) {
			if(main.assault == true) {
				int team = main.getTeam(e.getPlayer());
				
				if(!main.patteLapin.contains(e.getPlayer().getName())) {
					e.getPlayer().sendMessage("§cVous n'avez pas récupérer de patte de lapin !");
					e.setCancelled(true);
					return;
				}
				
				if(!main.peauLapin.contains(e.getPlayer().getName())) {
					e.getPlayer().sendMessage("§cVous n'avez pas récupérer de patte de lapin !");
					e.setCancelled(true);
					return;
				}
				
				switch (team) {
				case 0:
					if(main.totemViolet) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
						e.getPlayer().sendMessage("§cVous n'avez pas complété votre totem !");
						return;
					}
					break;
				case 1:
					if(main.totemOrange) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
						e.getPlayer().sendMessage("§cVous n'avez pas complété votre totem !");
						return;
					}
					break;
				case 2:
					if(main.totemCyan) {
						e.setCancelled(false);
					} else {
						e.setCancelled(true);
						e.getPlayer().sendMessage("§cVous n'avez pas complété votre totem !");
						return;
					}
					break;
				default:
					break;
				}
				return;
			} else {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§cAttendez la phase des assauts pour poser de la TNT");
				return;
			}
		}
		
		Player p = e.getPlayer();		
		
		int team = main.getTeam(p);
		
		int x = e.getBlock().getX();
		int z = e.getBlock().getZ();
		int xMin = 0, xMax = 0, zMin = 0, zMax = 0;
		switch (team) {
		case 0:
			xMin = main.getConfig().getInt("teams.violet.xMin");
			xMax = main.getConfig().getInt("teams.violet.xMax");
			zMin = main.getConfig().getInt("teams.violet.zMin");
			zMax = main.getConfig().getInt("teams.violet.zMax");
			break;
		case 1:
			xMin = main.getConfig().getInt("teams.orange.xMin");
			xMax = main.getConfig().getInt("teams.orange.xMax");
			zMin = main.getConfig().getInt("teams.orange.zMin");
			zMax = main.getConfig().getInt("teams.orange.zMax");
			break;
		case 2:
			xMin = main.getConfig().getInt("teams.cyan.xMin");
			xMax = main.getConfig().getInt("teams.cyan.xMax");
			zMin = main.getConfig().getInt("teams.cyan.zMin");
			zMax = main.getConfig().getInt("teams.cyan.zMax");
			break;
		case 3:
			e.setCancelled(true);
			break;
		default:
			break;
		}
		if((xMin <= x && x <= xMax) && (zMin <= z && z <= zMax)) {	
			e.setCancelled(false);
		} else {
			p.sendMessage("§cVous ne pouvez pas poser de block en dehors de votre base !");
			e.setCancelled(true);
		}
		
		
	}
	
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		if(main.buildModePlayers.contains(e.getPlayer())) {
			e.setCancelled(false);
		}
		
		if(e.getPlayer().getName().equalsIgnoreCase("lenchanteur")) {
			return;
		}
		
		if(e.getBlock().getType() == Material.BEACON) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cVous ne pouvez pas miner ce block");
			return;
		}

		if(main.isState(FKStates.WAITING) || main.isState(FKStates.PREGAME)) {
			e.setCancelled(true);
			return;
		}
		Player p = e.getPlayer();
		
		if(e.getBlock().getType() == Material.TNT) {
			e.setCancelled(false);
			return;
		}
		
		int team = main.getTeam(p);
		
		int x = e.getBlock().getX();
		int z = e.getBlock().getZ();
		int xMin, xMax, zMin, zMax;

		switch (team) {
		case 0:
			xMin = main.getConfig().getInt("teams.orange.xMin");
			xMax = main.getConfig().getInt("teams.orange.xMax");
			zMin = main.getConfig().getInt("teams.orange.zMin");
			zMax = main.getConfig().getInt("teams.orange.zMax");
			if((xMin <= x && x <= xMax) && (zMin <= z && z <= zMax)) {	
				p.sendMessage("§cVous ne pouvez pas casser de block dans la base adverse !");
				e.setCancelled(true);
				break;
			}
			xMin = main.getConfig().getInt("teams.cyan.xMin");
			xMax = main.getConfig().getInt("teams.cyan.xMax");
			zMin = main.getConfig().getInt("teams.cyan.zMin");
			zMax = main.getConfig().getInt("teams.cyan.zMax");
			
			if((xMin <= x && x <= xMax) && (zMin <= z && z <= zMax)) {	
				p.sendMessage("§cVous ne pouvez pas casser de block dans la base adverse !");
				e.setCancelled(true);
				break;
			}
			break;
		case 1:
			xMin = main.getConfig().getInt("teams.violet.xMin");
			xMax = main.getConfig().getInt("teams.violet.xMax");
			zMin = main.getConfig().getInt("teams.violet.zMin");
			zMax = main.getConfig().getInt("teams.violet.zMax");
			if((xMin <= x && x <= xMax) && (zMin <= z && z <= zMax)) {	
				p.sendMessage("§cVous ne pouvez pas casser de block dans la base adverse !");
				e.setCancelled(true);
				break;
			}
			xMin = main.getConfig().getInt("teams.cyan.xMin");
			xMax = main.getConfig().getInt("teams.cyan.xMax");
			zMin = main.getConfig().getInt("teams.cyan.zMin");
			zMax = main.getConfig().getInt("teams.cyan.zMax");
			
			if((xMin <= x && x <= xMax) && (zMin <= z && z <= zMax)) {	
				p.sendMessage("§cVous ne pouvez pas casser de block dans la base adverse !");
				e.setCancelled(true);
				break;
			}
			break;
		case 2:
			xMin = main.getConfig().getInt("teams.violet.xMin");
			xMax = main.getConfig().getInt("teams.violet.xMax");
			zMin = main.getConfig().getInt("teams.violet.zMin");
			zMax = main.getConfig().getInt("teams.violet.zMax");
			if((xMin <= x && x <= xMax) && (zMin <= z && z <= zMax)) {	
				p.sendMessage("§cVous ne pouvez pas casser de block dans la base adverse !");
				e.setCancelled(true);
				break;
			}
			xMin = main.getConfig().getInt("teams.orange.xMin");
			xMax = main.getConfig().getInt("teams.orange.xMax");
			zMin = main.getConfig().getInt("teams.orange.zMin");
			zMax = main.getConfig().getInt("teams.orange.zMax");
			
			if((xMin <= x && x <= xMax) && (zMin <= z && z <= zMax)) {	
				p.sendMessage("§cVous ne pouvez pas casser de block dans la base adverse !");
				e.setCancelled(true);
				break;
			}
			break;
		case 3:
			e.setCancelled(true);
			break;
		default:
			break;
		}
	}
	




	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		
		if(main.buildModePlayers.contains(e.getPlayer())) {
			e.setCancelled(false);
		}
		
		if(main.isState(FKStates.WAITING) || main.isState(FKStates.PREGAME)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if(main.isState(FKStates.WAITING) || main.isState(FKStates.PREGAME)) {
			e.setCancelled(true);
		}
		
		if(e.getItem().getItemStack().getType() == Material.RABBIT_HIDE) {
			if(!main.peauLapin.contains(e.getPlayer().getName())) {
				main.peauLapin.add(e.getPlayer().getName());
				Bukkit.broadcastMessage(e.getPlayer().getName() + " vient d'obtenir le succès §a[Peau de Lapin]");
				e.setCancelled(true);
				e.getItem().remove();
			}
		}
		
		if(e.getItem().getItemStack().getType() == Material.RABBIT_FOOT) {
			if(!main.patteLapin.contains(e.getPlayer().getName())) {
				main.patteLapin.add(e.getPlayer().getName());
				Bukkit.broadcastMessage(e.getPlayer().getName() + " vient d'obtenir le succès §a[Patte de Lapin]");
				e.setCancelled(true);
				e.getItem().remove();
			}
		}
		
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		//Player player = e.getPlayer();
		//main.removePlayer(player);
		e.setQuitMessage("§b§l[§6FKI§b§l] §8[§4-§8] §b" + e.getPlayer().getName());
	}
	
	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent e) {

	}
	
	
	
	public void actKD() {
		for(Entry<Player, ScoreboardSign> boards : main.boards.entrySet()) {
			boards.getValue().setLine(9, "§6Kill : §b" + main.getKills(boards.getKey()));
		}
		for(Entry<Player, ScoreboardSign> boards : main.boards.entrySet()) {
			boards.getValue().setLine(10, "§6Death : §b" + main.getDeath(boards.getKey()));
		}
	}
	
}
