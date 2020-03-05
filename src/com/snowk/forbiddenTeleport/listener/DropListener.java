package com.snowk.forbiddenTeleport.listener;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import com.snowk.forbiddenTeleport.config.Config;

public class DropListener implements Listener{
	
	String prefix = Config.getString("Prefix");
	boolean enableBFix = Config.getBoolean("enableBladeBugFixer");
	boolean enableDropListener = false;
	boolean enableUseListener = false;
	boolean lock = false;
	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent pEvent) {
		if (enableDropListener == true) {
			pEvent.setCancelled(true);
			Player p = pEvent.getPlayer();
			p.sendMessage(prefix + Config.getString("MessageLock1"));
			p.sendMessage(prefix + Config.getString("MessageLock2"));
			lock = true;
		} else {
	    	enableUseListener = false;
	    	lock = false;
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent e){
		if (enableBFix) {
			List<Integer> bannedIdList = Config.getIntegerList("bugItemId");
			int id = e.getPlayer().getItemInHand().getTypeId();
			if(bannedIdList.contains(id)){
				enableDropListener = true;
		    } else {
		    	enableUseListener = false;
		    	enableDropListener = false;
		    	lock = false;
		    }
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemSwap(PlayerItemHeldEvent e) {
		if (enableBFix) {
			List<Integer> bannedIdList = Config.getIntegerList("bugItemId");
			int id = e.getPlayer().getItemInHand().getTypeId();
			if (lock) {
				Player p = e.getPlayer();
				p.sendMessage(prefix + Config.getString("MessageUnlock"));
				enableUseListener = false;
		    	enableDropListener = false;
		    	lock = false;
			}
			if(bannedIdList.contains(id)){
		    	enableUseListener = true;
		    } else {
		    	enableUseListener = false;
		    	enableDropListener = false;
		    	lock = false;
		    }
		}
	}
}
