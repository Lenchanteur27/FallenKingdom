package fr.lenchanteur.fk.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lenchanteur.fk.Main;

public class commandDidactitiel implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player)) {
			sender.sendMessage("§cSeul un joueur peut executer cette commande !");
			return false;
		}
		
		Player pls = (Player) sender;
		
		if(label.equalsIgnoreCase("dida")) {
					BukkitRunnable task = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.setGameMode(GameMode.SPECTATOR);
				    		pls.teleport(new Location(Bukkit.getWorld("FK6"), 30,95,95));
				    		pls.sendMessage("§d§k!!§r  §b§lBienvenue dans cette avanture, Fallen Kingdoms !  §d§k!!");
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task.runTaskLater(Main.getPlugin(Main.class), 0);
				    
				    
				    BukkitRunnable task1 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	for(int i = 0 ; i< 20;i++) {
								pls.sendMessage("");
							}
							pls.teleport(new Location(Bukkit.getWorld("FK6"), 296, 75, 75));
							pls.sendMessage("§bVous avez découvert le temple du spawn, voici vos bases !");
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task1.runTaskLater(Main.getPlugin(Main.class), 20 * 3);
					
				    BukkitRunnable task2 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -186, 73, -178));
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task2.runTaskLater(Main.getPlugin(Main.class), 20 * 7);
				    
				    BukkitRunnable task3 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -198, 76, 290));
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task3.runTaskLater(Main.getPlugin(Main.class), 20 * 10);
				    
				    BukkitRunnable task4 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -70, 86, 100));
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task4.runTaskLater(Main.getPlugin(Main.class), 20 * 13);
					
				    BukkitRunnable task5 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -60, 82, 94));
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task5.runTaskLater(Main.getPlugin(Main.class), 20 * 14);
				    
				    BukkitRunnable task6 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -49, 82, 93));
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task6.runTaskLater(Main.getPlugin(Main.class), 20 * 15);
					
				    BukkitRunnable task7 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -41, 86, 91));
				    		for(int i = 0 ; i< 20;i++) {
				    			pls.sendMessage("");
				    		}
				    		pls.sendMessage("§bVous voici ici devant le portail du nether ! Celui n'est pas activable tant que le Nether n'est pas activé !");
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task7.runTaskLater(Main.getPlugin(Main.class), 20 * 17);
					
				    BukkitRunnable task8 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -41, 86, 91));
				    		for(int i = 0 ; i< 20;i++) {
				    			pls.sendMessage("");
				    		}
				    		pls.sendMessage("§bVous voici ici devant le portail du nether ! Celui n'est pas activable tant que le Nether n'est pas activé !");
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task8.runTaskLater(Main.getPlugin(Main.class), 20 * 20);


					BukkitRunnable task9 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	for(int i = 0 ; i< 20;i++) {
				    			pls.sendMessage("");
				    		}
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -43, 84, 86, 43.5f, 11.9f));
							pls.sendMessage("§bVous pouvez apercevoir ici l'une des quatre entrées pour accéder au portail de l'End !");
							pls.sendMessage("§bCelles-ci sont également bloquées tant que l'End ne sera pas activé !!");
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task9.runTaskLater(Main.getPlugin(Main.class), 20 * 23);
					
					
				    BukkitRunnable task10 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	for(int i = 0 ; i< 20;i++) {
				    			pls.sendMessage("");
				    		}
				    		pls.teleport(new Location(Bukkit.getWorld("FK6"), -35, 86, 83, 176.5f, 48.3f));
				    		pls.sendMessage("§bVous aurez sans doûte remarqué ces étranges marquages...");
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task10.runTaskLater(Main.getPlugin(Main.class), 20 * 28);
					
					
				    BukkitRunnable task11 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -35, 86, 58));
				    		pls.sendMessage("§bSuivez les pour trouver votre totem !");
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task11.runTaskLater(Main.getPlugin(Main.class), 20 * 31);
					
				    
				    BukkitRunnable task12 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	pls.teleport(new Location(Bukkit.getWorld("FK6"), -35, 86, 54));
				    		pls.sendMessage("§bVous trouverez le craft, ainsi que l'endroit ou poser votre totem !");
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task12.runTaskLater(Main.getPlugin(Main.class), 20 * 34);
				    
				    
				    BukkitRunnable task13 = new BukkitRunnable() {
				        @Override
				        public void run() {
				        	for(int i = 0 ; i< 20;i++) {
				    			pls.sendMessage("");
				    		}
				    		pls.setGameMode(GameMode.SURVIVAL);
				    		pls.teleport(new Location(Bukkit.getWorld("FK6"), -34, 121, 90));
				    		pls.sendMessage("§bJe ne vous en dit pas plus ! Bonne chance à tous !");
				        }
				    };
				    // Run the task on this plugin in 3 seconds (60 ticks)
				    task13.runTaskLater(Main.getPlugin(Main.class), 20 * 38);
					
					return true;
		}
		
		return true;
	}

}
