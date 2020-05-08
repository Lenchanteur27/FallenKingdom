package fr.lenchanteur.fk.titles;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class FKTitles {

	public void sendTitle(Player p, String title, String subtitle, int ticks) {
		IChatBaseComponent basetitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
		IChatBaseComponent basesubtitle = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, basetitle);
		PacketPlayOutTitle subtitlepacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, basesubtitle);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(titlepacket);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(subtitlepacket);
		sendTime(p, ticks);
	}
	
	
	public void sendTime(Player p, int ticks) {
		PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, ticks, 20);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(titlepacket);
	}
	
	
	public void sendActionBar(Player p, String message) {
		IChatBaseComponent basetitle = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(basetitle, (byte)2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
}
