package com.coco.mc.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String colored(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
