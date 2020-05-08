package fr.lenchanteur.fk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.lenchanteur.fk.commands.commandBuild;
import fr.lenchanteur.fk.commands.commandDidactitiel;
import fr.lenchanteur.fk.commands.commandGame;
import fr.lenchanteur.fk.commands.commandPB;
import fr.lenchanteur.fk.commands.commandPL;
import fr.lenchanteur.fk.commands.commandSpeed;
import fr.lenchanteur.fk.commands.commandStart;
import fr.lenchanteur.fk.enumeration.FKStates;
import fr.lenchanteur.fk.listeners.FKListeners;
import fr.lenchanteur.fk.team.Team;
import fr.lenchanteur.fk.titles.FKTitles;

public class Main extends JavaPlugin {

	private FKStates current;
	public List<Team> teams = new ArrayList<>();
	public FKTitles title = new FKTitles();
	public Map<Player, ScoreboardSign> boards = new HashMap<>();
	
	public boolean pvp = false; // Jour 3
	public boolean nether = false; // Jour 3
	public boolean end = false; // Jour 5
	public boolean assault = false; // Jour 6
	
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard s = manager.getNewScoreboard();
	public List<String> violet = new ArrayList<String>();
	public List<String> orange = new ArrayList<String>();
	public List<String> cyan = new ArrayList<String>();
	public List<String> spec = new ArrayList<String>();
	
	public Map<String, Integer> kill = new HashMap<String, Integer>();
	public Map<String, Integer> death = new HashMap<String, Integer>();
	
	public List<String> patteLapin = new ArrayList<String>();
	public List<String> peauLapin = new ArrayList<String>();
	
	public List<Player> buildModePlayers = new ArrayList<Player>();
	
	public boolean totemViolet = false;
	public boolean totemOrange = false;
	public boolean totemCyan = false;
	
	//https://pastebin.com/6Y03u8Fj
	
	public void onLoad() {
		Bukkit.getConsoleSender().sendMessage("§a---------------------------");
		Bukkit.getConsoleSender().sendMessage("§a     PLUGIN FK ON   ");
		Bukkit.getConsoleSender().sendMessage("§a---------------------------");
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§c---------------------------");
		Bukkit.getConsoleSender().sendMessage("§c     PLUGIN FK OFF   ");
		Bukkit.getConsoleSender().sendMessage("§c---------------------------");
	}
	
	public void onEnable() {
		current = FKStates.WAITING;
		getServer().getPluginManager().registerEvents(new FKListeners(this), this);
		
		addCraft();
		
		saveDefaultConfig();
		getCommand("start").setExecutor(new commandStart(this));
		getCommand("pause").setExecutor(new commandStart(this));
		
		getCommand("sc").setExecutor(new commandGame(this));
		getCommand("rules").setExecutor(new commandGame(this));
		
		getCommand("dida").setExecutor(new commandDidactitiel());
		
		getCommand("pb").setExecutor(new commandPB());
		getCommand("question").setExecutor(new commandPB());
		
		getCommand("gm").setExecutor(new commandPB());
		
		getCommand("build").setExecutor(new commandBuild(this));
		getCommand("speed").setExecutor(new commandSpeed(this));
		
		getCommand("pl").setExecutor(new commandPL());
		getCommand("plugins").setExecutor(new commandPL());
		
		ConfigurationSection section = getConfig().getConfigurationSection("teams");
		for(String team : section.getKeys(false)) {
			String name = section.getString(team + ".name");
			String tag = section.getString(team + ".color").replace("&", "§");
			byte data = (byte)section.getInt(team + ".data");
			teams.add(new Team(name, tag, data));
		}
		
	}
	
	public void blockNether() {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -36 90 97 barrier");
	}
	
	public void blockEnd() {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -50 81 99 barrier");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -50 81 87 barrier");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -21 81 87 barrier");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock -21 81 99 barrier");
	}
	
	
	public void setState(FKStates state) {
		current = state;
	}
	
	public boolean isState(FKStates state) {
		return current == state;
	}
	
	
	public void addPlayer(Player player, Team team) {
		String tag = team.getTag() + team.getName();
		if(team.getPlayers().contains(player)) {
			player.sendMessage("§7§oVous êtes déjà dans l'équipe " + tag);
			return;
		}
		if(team.getSize() >= 3) {
			player.sendMessage("§7§oL'équipe " + tag + " §7§o est pleine !");
			return;
		}
		team.addPlayer(player);
		player.setPlayerListName(tag + " " + player.getName());
		player.sendMessage("§7§oVous rejoignez l'équipe " + tag);
		player.setPlayerListName(tag + " " + player.getName());
	}
	
	private void addCraft() {
		ItemStack headFK = new ItemStack(Material.BEACON);
		ItemMeta headMetaFK = headFK.getItemMeta();
		headMetaFK.setDisplayName("§6§k!!§r  §d§lTête de Totem  §6§k!!");
		headFK.setItemMeta(headMetaFK);
		
		ShapedRecipe creeperHeadFK = new ShapedRecipe(new ItemStack(headFK));
		creeperHeadFK.shape(new String[] {"ABC","DEF","GHI"});
		creeperHeadFK.setIngredient('A', Material.EYE_OF_ENDER);
		creeperHeadFK.setIngredient('B', Material.TRIPWIRE_HOOK);
		creeperHeadFK.setIngredient('C', Material.PUMPKIN_PIE);
		creeperHeadFK.setIngredient('D', Material.BLAZE_POWDER);
		creeperHeadFK.setIngredient('E', Material.ENDER_STONE);
		creeperHeadFK.setIngredient('F', Material.NETHER_BRICK);
		creeperHeadFK.setIngredient('G', Material.CAKE);
		creeperHeadFK.setIngredient('H', Material.HAY_BLOCK);
		creeperHeadFK.setIngredient('I', Material.DIAMOND_HOE);
		getServer().addRecipe(creeperHeadFK);
	}
	
	public void reAddTeam(Player player, Team team) {
		String tag = team.getTag() + team.getName();
		player.setPlayerListName(tag + " " + player.getName());
	}
	
	public void removePlayer(Player player) {
		for(Team team : teams) {
			if(team.getPlayers().contains(player)) {
				team.removePlayer(player);
			}
		}
	}
	
	public void actTablist(Player p) {
		if(violet.contains(p.getName())) {
			String tag = getConfig().getString("teams.violet.color").replace("&", "§") + getConfig().getString("teams.violet.name");
			p.setPlayerListName(tag + " " + p.getName());
			return;
		}
		if(orange.contains(p.getName())) {
			String tag = getConfig().getString("teams.orange.color").replace("&", "§") + getConfig().getString("teams.orange.name");
			p.setPlayerListName(tag + " " + p.getName());
			return;
		}
		if(cyan.contains(p.getName())) {
			String tag = getConfig().getString("teams.cyan.color").replace("&", "§") + getConfig().getString("teams.cyan.name");
			p.setPlayerListName(tag + " " + p.getName());
			return;
		} else {
			p.setPlayerListName("§7§o[Spectateur] " + p.getName());
			for(Player pls : Bukkit.getOnlinePlayers()) {
				pls.hidePlayer(p);
			}
		}
		
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public int getTeam(Player p) {
		int a = 0;
		for(int i = 0 ; i < 3; i++) {
			Team tm = getTeams().get(i);
			if(tm.getPlayers().contains(p)) {
				a = i;
				return a;
			}
		}
		return 3;
	}
	
	public Integer getKills(Player p) {
		return kill.get(p.getName());
	}
	
	public Integer getDeath(Player p) {
		return death.get(p.getName());
	}

	public List<Player> getPlayerInTeam(Player p) {
		List<Player> teamPlayer = new ArrayList<Player>();
		int team = getTeam(p);
		switch (team) {
		case 0:
			for(int i = 0; i < violet.size(); i++) {
				teamPlayer.add(Bukkit.getPlayer(violet.get(i)));
			}
			break;
		case 1:
			for(int i = 0; i < orange.size(); i++) {
				teamPlayer.add(Bukkit.getPlayer(orange.get(i)));
			}
			break;
		case 2:
			for(int i = 0; i < cyan.size(); i++) {
				teamPlayer.add(Bukkit.getPlayer(cyan.get(i)));
			}
			break;
		case 3:
			for(int i = 0; i < spec.size(); i++) {
				teamPlayer.add(Bukkit.getPlayer(spec.get(i)));
			}
			break;
		default:
			break;
		}
		return teamPlayer;
	}

	public void totemValid(Player player) {
		int team = getTeam(player);
		switch (team) {
		case 0:
			totemViolet = true;
			Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6L'équipe §5" + getConfig().getString("teams.violet.name") + " §6vient de compléter son totem !");
			break;
		case 1:
			totemOrange = true;
			Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6L'équipe §6" + getConfig().getString("teams.orange.name") + " §6vient de compléter son totem !");
			break;
		case 2:
			totemCyan = true;
			Bukkit.broadcastMessage("§b§l[§6FKI§b§l] §6L'équipe §3" + getConfig().getString("teams.cyan.name") + " §6vient de compléter son totem !");
			break;
		default:
			break;
		}
	}
	
}
