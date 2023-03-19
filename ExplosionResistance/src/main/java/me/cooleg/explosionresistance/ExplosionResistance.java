package me.cooleg.explosionresistance;

import me.cooleg.nms.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class ExplosionResistance extends JavaPlugin {

    private NMSInterface nms;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        switch (Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]) {
            case "v1_19_R3":
                nms = new v1_19_R3();
                break;
            case "v1_19_R2":
                nms = new v1_19_R2();
                break;
            case "v1_19_R1":
                nms = new v1_19_R1();
                break;
            case "v1_18_R2":
                nms = new v1_18_R2();
                break;
            case "v1_16_R3":
                nms = new v1_16_R3();
                break;
            default:
                Bukkit.getLogger().severe("Incompatible minecraft version!");
                Bukkit.getPluginManager().disablePlugin(this);
        }

        final ConfigurationSection section = getConfig().getConfigurationSection("config");
        if (section == null) {Bukkit.getLogger().severe(ChatColor.RED + "Your config is either broken or empty."); Bukkit.getPluginManager().disablePlugin(this);}
        for (String s : section.getKeys(false)) {
            nms.setResistance(s, (float) section.getDouble(s));
        }
    }
}
