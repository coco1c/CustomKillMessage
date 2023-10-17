package com.coco.mc.data;

import com.coco.mc.LitemcKillMessage;
import com.coco.mc.util.ConfigUtil;

import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private String killMessage;
    private String blowUpKillMSG;
    private String fallDamage;
    private String drowning;
    private String suffocation;
    private String lavaDamage, cactusDamage, poisoning, starvation, fallingBlock, magic, wither, slainByMob, hitWall, defaultMsg, endCrystal, anchor;

    public PlayerData(UUID uuid, String killMessage, String blowUpKillMSG){
        this.uuid = uuid;
        if(killMessage == null){
            this.killMessage = ConfigUtil.getKillMessage("killed-by-other-player");
        }else {
            this.killMessage = killMessage;
        }
        this.blowUpKillMSG = ConfigUtil.getKillMessage("explosion");
        fallDamage = ConfigUtil.getKillMessage("fall-damage");
        drowning = ConfigUtil.getKillMessage("drowning");
        suffocation = ConfigUtil.getKillMessage("suffocation");
        lavaDamage = ConfigUtil.getKillMessage("lava-damage");
        cactusDamage = ConfigUtil.getKillMessage("cactus-damage");
        poisoning = ConfigUtil.getKillMessage("poisoning");
        starvation = ConfigUtil.getKillMessage("starvation");
        fallingBlock = ConfigUtil.getKillMessage("falling-block");
        magic = ConfigUtil.getKillMessage("magic");
        wither = ConfigUtil.getKillMessage("wither");
        slainByMob = ConfigUtil.getKillMessage("slain-by-mob");
        hitWall = ConfigUtil.getKillMessage("hit-wall");
        defaultMsg = ConfigUtil.getKillMessage("unknown");
        endCrystal = ConfigUtil.getKillMessage("end-crystal");
        anchor = ConfigUtil.getKillMessage("anchor");

    }

    public String getBlowUpKillMSG() {
        return blowUpKillMSG;
    }

    public String getKillMessage() {
        return killMessage;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setBlowUpKillMSG(String blowUpKillMSG) {
        this.blowUpKillMSG = blowUpKillMSG;
    }

    public void setKillMessage(String killMessage) {
        this.killMessage = killMessage;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDrowning() {
        return drowning;
    }

    public String getFallDamage() {
        return fallDamage;
    }

    public String getLavaDamage() {
        return lavaDamage;
    }

    public String getSuffocation() {
        return suffocation;
    }

    public String getCactusDamage() {
        return cactusDamage;
    }

    public String getFallingBlock() {
        return fallingBlock;
    }

    public String getMagic() {
        return magic;
    }

    public String getPoisoning() {
        return poisoning;
    }

    public String getSlainByMob() {
        return slainByMob;
    }

    public String getStarvation() {
        return starvation;
    }

    public String getWither() {
        return wither;
    }

    public String getHitWall() {
        return hitWall;
    }

    public String getDefaultMsg() {
        return defaultMsg;
    }

    public String getEndCrystal() {
        return endCrystal;
    }

    public void update(){
        this.blowUpKillMSG = ConfigUtil.getKillMessage("explosion");
        fallDamage = ConfigUtil.getKillMessage("fall-damage");
        drowning = ConfigUtil.getKillMessage("drowning");
        suffocation = ConfigUtil.getKillMessage("suffocation");
        lavaDamage = ConfigUtil.getKillMessage("lava-damage");
        cactusDamage = ConfigUtil.getKillMessage("cactus-damage");
        poisoning = ConfigUtil.getKillMessage("poisoning");
        starvation = ConfigUtil.getKillMessage("starvation");
        fallingBlock = ConfigUtil.getKillMessage("falling-block");
        magic = ConfigUtil.getKillMessage("magic");
        wither = ConfigUtil.getKillMessage("wither");
        slainByMob = ConfigUtil.getKillMessage("slain-by-mob");
        hitWall = ConfigUtil.getKillMessage("hit-wall");
        defaultMsg = ConfigUtil.getKillMessage("unknown");
        endCrystal = ConfigUtil.getKillMessage("end-crystal");
        anchor = ConfigUtil.getKillMessage("anchor");


    }

    public String getAnchor() {
        return anchor;
    }

    public static PlayerData get(UUID uuid){
        if(LitemcKillMessage.instance.getPlayerData().containsKey(uuid)){
            return LitemcKillMessage.instance.getPlayerData().get(uuid);
        }
        PlayerData playerData = new PlayerData(uuid, null, null);
        LitemcKillMessage.instance.getPlayerData().put(uuid, playerData);
        return playerData;
    }
}
