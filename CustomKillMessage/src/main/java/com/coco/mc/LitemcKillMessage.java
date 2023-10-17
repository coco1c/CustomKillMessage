package com.coco.mc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hakan.core.HCore;
import com.coco.mc.command.MainCommand;
import com.coco.mc.data.PlayerData;
import com.coco.mc.listener.PlayerDeathListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class LitemcKillMessage extends JavaPlugin {


    public static LitemcKillMessage instance;
    HashMap<UUID, PlayerData> playerData;
    HashMap<UUID, Boolean> playerCommand;
    HashMap<UUID, Boolean> othersCommand;
    HashMap<UUID, Boolean> defaultCommand;
    @Override
    public void onEnable() {
        instance = this;
        HCore.initialize(this);
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        playerData = new HashMap<>();
        playerCommand = new HashMap<>();
        othersCommand = new HashMap<>();
        defaultCommand = new HashMap<>();
        HCore.registerListeners(new PlayerDeathListener());
        HCore.registerCommands(new MainCommand());
        if(getConfig().contains("plugin-data-do-not-edit") && !getConfig().getString("plugin-data-do-not-edit").isEmpty()){
            TypeToken<HashMap<UUID, PlayerData>> type = new TypeToken<HashMap<UUID, PlayerData>>() {};
            playerData = new Gson().fromJson(getConfig().getString("plugin-data-do-not-edit"), type);
        }
        if(playerData != null) {
            playerData.values().forEach(PlayerData::update);
        }
        getLogger().info("Loaded " + getName() + " " + getDescription().getVersion() + " made for " + getDescription().getAPIVersion());
    }



    @Override
    public void onDisable() {
        getConfig().set("plugin-data-do-not-edit", new Gson().toJson(getPlayerData()));
        saveConfig();
    }

    public HashMap<UUID, PlayerData> getPlayerData() {
        return playerData;
    }

    public HashMap<UUID, Boolean> getDefaultCommand() {
        return defaultCommand;
    }

    public HashMap<UUID, Boolean> getOthersCommand() {
        return othersCommand;
    }

    public HashMap<UUID, Boolean> getPlayerCommand() {
        return playerCommand;
    }
}
