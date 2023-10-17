package com.coco.mc.command;


import com.hakan.core.HCore;
import com.hakan.core.command.executors.basecommand.BaseCommand;
import com.hakan.core.command.executors.subcommand.SubCommand;
import com.coco.mc.LitemcKillMessage;
import com.coco.mc.data.PlayerData;
import com.coco.mc.util.ColorUtil;
import com.coco.mc.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@BaseCommand(
        name = "killmsg",
        aliases = "killmessage",
        description = "Main command for kill message plugin"
)
public class MainCommand {

    @SubCommand(
    )
    public void playerCommand(CommandSender sender, String[] args){
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(LitemcKillMessage.instance.getPlayerCommand().containsKey(player.getUniqueId())){
                player.sendMessage(ConfigUtil.getMessage("cooldown"));
                return;
            }
            if(player.hasPermission(ConfigUtil.getPermission("player-command"))){
                if(!(args.length > 0)){
                    player.sendMessage(ConfigUtil.getMessage("invalid-command"));
                }else{
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        if(i == 0){
                            stringBuilder.append(args[i]);
                        }else{
                            stringBuilder.append(" ").append(args[i]);
                        }
                    }

                    for (String s : LitemcKillMessage.instance.getConfig().getStringList("banned-words")) {
                        if(stringBuilder.toString().toLowerCase().contains(s)){
                            sender.sendMessage(ConfigUtil.getMessage("banned-words"));
                            return;
                        }
                    }
                    if(LitemcKillMessage.instance.getConfig().getInt("max-msg-length") > 0 && stringBuilder.length() > LitemcKillMessage.instance.getConfig().getInt("max-msg-length")){
                        sender.sendMessage(ConfigUtil.getMessage("too-big-text"));
                        return;
                    }

                    PlayerData playerData = PlayerData.get(player.getUniqueId());
                    playerData.setKillMessage(stringBuilder.toString());
                    player.sendMessage(ConfigUtil.getMessage("command-success"));
                    LitemcKillMessage.instance.getPlayerCommand().put(player.getUniqueId(), true);
                    HCore.asyncScheduler().after(LitemcKillMessage.instance.getConfig().getInt("cooldown.player-command"), TimeUnit.SECONDS).run( () -> LitemcKillMessage.instance.getPlayerCommand().remove(player.getUniqueId()));
                }

            }else{
                sender.sendMessage(ConfigUtil.getMessage("no-permission"));
            }
        }else{
            sender.sendMessage(ConfigUtil.getMessage("player-needed"));
        }
    }


    @SubCommand(
            args = "reload"
    )
    public void reloadCommand(CommandSender sender, String[] args){
        if(sender.hasPermission(ConfigUtil.getPermission("reload"))) {
            LitemcKillMessage.instance.reloadConfig();
            for (PlayerData value : LitemcKillMessage.instance.getPlayerData().values()) {
                value.update();
            }
            sender.sendMessage(ConfigUtil.getMessage("command-success"));
        }else{
            sender.sendMessage(ConfigUtil.getMessage("no-permission"));
        }
    }

    @SubCommand(
            args = "help"
    )
    public void helpCommand(CommandSender sender, String[] args){
        if(sender.hasPermission(ConfigUtil.getPermission("help"))) {
            String[] strings = ConfigUtil.getMessage("help").split("/n");
            for (String string : strings) {
                sender.sendMessage(ColorUtil.colored(string));
            }
        }else{
            sender.sendMessage(ConfigUtil.getMessage("no-permission"));
        }
    }

    @SubCommand(
            args = "set"
    )
    public void otherCommand(CommandSender sender, String[] args){
            if(sender.hasPermission(ConfigUtil.getPermission("kill-msg-others"))){

                if(!(args.length > 2)){
                    sender.sendMessage(ConfigUtil.getMessage("invalid-command"));
                }else{
                    String playerName = args[1];
                    Player player = Bukkit.getPlayer(playerName);
                    if(player == null){
                        sender.sendMessage(ConfigUtil.getMessage("player-not-found"));
                        return;
                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        if(i == 2){
                            stringBuilder.append(args[i]);
                        }else{
                            stringBuilder.append(" ").append(args[i]);
                        }
                    }

                    for (String s : LitemcKillMessage.instance.getConfig().getStringList("banned-words")) {
                        if(stringBuilder.toString().toLowerCase().contains(s)){
                            sender.sendMessage(ConfigUtil.getMessage("banned-words"));
                            return;
                        }
                    }
                    PlayerData playerData = PlayerData.get(player.getUniqueId());
                    playerData.setKillMessage(stringBuilder.toString());
                    sender.sendMessage(ConfigUtil.getMessage("command-success"));
                }

            }else{
                sender.sendMessage(ConfigUtil.getMessage("no-permission"));
            }
    }
}
