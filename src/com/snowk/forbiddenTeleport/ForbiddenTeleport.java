package com.snowk.forbiddenTeleport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.snowk.forbiddenTeleport.listener.DropListener;
import com.snowk.forbiddenTeleport.listener.PlayerTeleport;
import com.snowk.forbiddenTeleport.config.Config;


public class ForbiddenTeleport extends JavaPlugin{
	
	public static ForbiddenTeleport snowkPlugin;
	
    @Override
    public void onEnable() {
    	snowkPlugin = this;
    	Config.loadConfig("config.yml");
    	getLogger().info("ForbiddenTeleport successfully enabled! - By:Bear");
    	registerListeners();
    }
    
    
    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerTeleport(), this);
        Bukkit.getPluginManager().registerEvents(new DropListener(), this);
    }

    @Override
    public void onDisable() {
    	getLogger().info("ForbiddenTeleport successfully disabled!");
    	
    }
    	
}
