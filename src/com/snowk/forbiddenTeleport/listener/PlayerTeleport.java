package com.snowk.forbiddenTeleport.listener;

import java.util.List;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import com.snowk.forbiddenTeleport.ForbiddenTeleport;
import com.snowk.forbiddenTeleport.config.Config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerTeleport implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	// 或用 PlayerChangedWorldEvent
	public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
		
		String prefix = Config.getString("Prefix");
		List<String> bannedWorld = Config.getStringList("BannedWorlds");
		List<Integer> bannedIdList = Config.getIntegerList("bannedItemId");
		World spawnW = Bukkit.getServer().getWorld(ForbiddenTeleport.snowkPlugin.getConfig().getString("spawnWorld"));
		double x = Config.getDouble("spawnPosX");
		double y = Config.getDouble("spawnPosY");
		double z = Config.getDouble("spawnPosZ");
		Location spawnL = new Location(spawnW,x,y,z);
		boolean enableTL = Config.getBoolean("enableTeleportListener");
		
		if (enableTL) {
			Player p = event.getPlayer();
			String w = event.getTo().getWorld().getName();
		    for (String i : bannedWorld) {
			    if (w.equals(i)) {
			    	int banItemCount = 0;	
				    ItemStack[] inv = p.getInventory().getContents();
				    for (ItemStack j : inv) {
				    	if (j != null) {
					    	int idItem = j.getTypeId();
					    	if (bannedIdList.contains(idItem)) {
					    			banItemCount++;
					    			p.sendMessage(prefix + Config.getString("MessageRejectItem") + idItem);
					    	}
				    	}
				    }
			    	if (banItemCount != 0) {
			    		event.setCancelled(true);
			    		p.sendMessage(prefix + Config.getString("MessageTeleportBack").replaceAll("&", "§"));
		    			p.teleport(spawnL);  // 防止玩家卡在触发器上无限触发和取消操作
		    			banItemCount = 0;
			    	}
			    }
		    }  
		} else {
			ForbiddenTeleport.snowkPlugin.getLogger().info("PlayerTeleportEventListener is now disabled.");
		} 
	}
}
