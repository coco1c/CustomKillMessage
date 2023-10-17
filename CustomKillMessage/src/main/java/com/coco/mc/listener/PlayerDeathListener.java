package com.coco.mc.listener;

import com.coco.mc.data.PlayerData;
import com.coco.mc.util.ColorUtil;
import com.coco.mc.util.ConfigUtil;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONObject;

import java.util.Objects;

public class    PlayerDeathListener implements Listener {

    @EventHandler
    public void onEntityKill(EntityDamageByEntityEvent event){

        if(event.getEntity() instanceof Player && !(event.getDamager() instanceof Player) && !(event.getDamager() instanceof EnderCrystal) && !(event.getDamager() instanceof TNTPrimed)){
            Player player = (Player) event.getEntity();
            if (player.getHealth() <= event.getDamage()) {
                PlayerData playerData = PlayerData.get(player.getUniqueId());
                sendMessage(format(playerData.getSlainByMob(), event, event.getCause()));
            }

        }

    }

    @EventHandler
    public void onCactusKill(EntityDamageByBlockEvent event){

        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (player.getHealth() <= event.getDamage() && event.getDamager() != null && (event.getDamager().getType() == Material.CACTUS || event.getDamager().getType() == Material.POTTED_CACTUS)) {
                PlayerData playerData = PlayerData.get(player.getUniqueId());
                sendMessage(format(playerData.getCactusDamage(), event, event.getCause()));
            }

        }

    }

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        event.setDeathMessage(null);
        if(event.getEntity().getKiller() != null && event.getEntity().getKiller().getUniqueId() != event.getEntity().getUniqueId()){
            if(event.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
                sendMessage(format(PlayerData.get(event.getEntity().getUniqueId()).getEndCrystal(), event, event.getEntity().getLastDamageCause().getCause()));
                return;
            }
            if(Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION){
                sendMessage(format(PlayerData.get(event.getEntity().getUniqueId()).getAnchor(), event, event.getEntity().getLastDamageCause().getCause()));
                return;
            }
            PlayerData playerData = PlayerData.get(event.getEntity().getKiller().getUniqueId());
                sendMessage(format(playerData.getKillMessage(), event, event.getEntity().getLastDamageCause().getCause()));
                return;
        }

        PlayerData playerData = PlayerData.get(event.getEntity().getUniqueId());
        if(Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION){
            sendMessage(format(playerData.getBlowUpKillMSG(), event, event.getEntity().getLastDamageCause().getCause()));
            return;
        }
        if(event.getEntity().getLastDamageCause() != null){
            EntityDamageEvent.DamageCause damageCause = event.getEntity().getLastDamageCause().getCause();
            switch (damageCause){
                case FALL:
                    sendMessage(format(playerData.getFallDamage(), event, damageCause));
                    break;
                case DROWNING:
                    sendMessage(format(playerData.getDrowning(), event, damageCause));
                    break;
                case SUFFOCATION:
                    sendMessage(format(playerData.getSuffocation(), event, damageCause));
                    break;
                case LAVA:
                case FIRE:
                    sendMessage(format(playerData.getLavaDamage(), event, damageCause));
                    break;
                case POISON:
                    sendMessage(format(playerData.getPoisoning(), event, damageCause));
                    break;
                case STARVATION:
                    sendMessage(format(playerData.getStarvation(), event, damageCause));
                    break;
                case FALLING_BLOCK:
                    sendMessage(format(playerData.getFallingBlock(), event, damageCause));
                    break;
                case MAGIC:
                    sendMessage(format(playerData.getMagic(), event, damageCause));
                    break;
                case WITHER:
                    sendMessage(format(playerData.getWither(), event, damageCause));
                    break;
                case FLY_INTO_WALL:
                    sendMessage(format(playerData.getHitWall(), event, damageCause));
                    break;
                case VOID:
                case CUSTOM:
                    sendMessage(format((playerData.getDefaultMsg()), event, damageCause));
                    break;
                default:
                    sendMessage("");
                    break;
            }
        }
    }


    public void sendMessage(String... s){
        if(s.length == 0) return;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(s);
        }
    }

    public void sendMessage(TextComponent... s){
        if(s.length == 0) return;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.spigot().sendMessage(s);
        }
    }
    public TextComponent format(String s, PlayerDeathEvent event, EntityDamageEvent.DamageCause damageCause) {
        TextComponent textComponent = new TextComponent();
        String weaponName = "";
        s = s.replace("{victim}", event.getEntity().getName());
        s = s.replace("{other_weapons}", damageCause.name().toLowerCase());
        if (event.getEntity().getKiller() != null && !event.getEntity().getKiller().getUniqueId().equals(event.getEntity().getUniqueId())) {
            s = s.replace("{killer}", event.getEntity().getKiller().getName());
            ItemMeta itemMeta = event.getEntity().getKiller().getItemInHand().getItemMeta();
            if(itemMeta != null){
                if(itemMeta.getDisplayName() != null && !itemMeta.getDisplayName().isEmpty()){
                    weaponName = itemMeta.getDisplayName();

                }else if(itemMeta.getDisplayName().isEmpty()){
                    weaponName = event.getEntity().getKiller().getItemInHand().getType().toString().toLowerCase().replace("_", " ");
                }
            }
            if (weaponName.isEmpty()){
                weaponName = ConfigUtil.getMessage("player_kill_with_hand");
            }
        }

        String[] parts = s.split("\\{weapon}");
        String partBeforeWeapon = parts[0];
        String partAfterWeapon = parts.length > 1 ? parts[1] : "";

        TextComponent partBeforeWeaponComponent = new TextComponent(ColorUtil.colored(partBeforeWeapon));
        TextComponent weaponComponent = new TextComponent(ColorUtil.colored(weaponName));
        if (!weaponName.isEmpty() && !weaponName.equals(ConfigUtil.getMessage("player_kill_with_hand"))) {
            ItemStack itemStack = event.getEntity().getKiller().getItemInHand();
            NBTItem nbtItem = new NBTItem(itemStack);
             weaponComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new Item(NBT.itemStackToNBT(itemStack).getString("id"), 1,
                    ItemTag.ofNbt(nbtItem.toString()))));
        }
        TextComponent partAfterWeaponComponent = new TextComponent(ColorUtil.colored(partAfterWeapon));

        textComponent.addExtra(partBeforeWeaponComponent);
        textComponent.addExtra(weaponComponent);
        textComponent.addExtra(partAfterWeaponComponent);

        return textComponent;
    }



    public String convertItemStackToJson(ItemStack itemStack) {
        JSONObject jsonObject = new JSONObject(itemStack.serialize());
        Bukkit.getLogger().info(jsonObject.toString());
        return jsonObject.toString();
    }

    public String format(String s, EntityDamageByEntityEvent event, EntityDamageEvent.DamageCause damageCause){
            s = s.replace("{killer}", event.getDamager().getName());
        s = s.replace("{other_weapons}", damageCause.name().toLowerCase());
        s = s.replace("{killer}", "").replace("{weapon}", "");


        return ColorUtil.colored(s.replace("{victim}", event.getEntity().getName()));
    }

    public String format(String s, EntityDamageByBlockEvent event, EntityDamageEvent.DamageCause damageCause){
        s = s.replace("{killer}", event.getDamager().getType().name().toLowerCase());
        s = s.replace("{other_weapons}", damageCause.name().toLowerCase());
        s = s.replace("{killer}", "").replace("{weapon}", "");


        return ColorUtil.colored(s.replace("{victim}", event.getEntity().getName()));
    }

}
