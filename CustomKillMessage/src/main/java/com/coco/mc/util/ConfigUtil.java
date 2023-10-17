package com.coco.mc.util;

import com.coco.mc.LitemcKillMessage;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {

    public static String getPermission(String path){
        String permission = "";
        FileConfiguration config = LitemcKillMessage.instance.getConfig();
        if(config.contains("permissions." + path)){
            permission = config.getString("permissions." + path);
        }

        return permission;
    }

    public static String getMessage(String path){
        String message = "";
        FileConfiguration config = LitemcKillMessage.instance.getConfig();
        if(config.contains("messages." + path)){
            message = config.getString("messages." + path);
        }

        return ColorUtil.colored(message);
    }

    public static String getKillMessage(String path){
        String msg = "";
        FileConfiguration config = LitemcKillMessage.instance.getConfig();
        if(config.contains("default-kill-msg." + path)){
            msg = config.getString("default-kill-msg." + path);
        }

        return msg;
    }
}
