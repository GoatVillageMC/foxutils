package me.foxils.foxutils;

import me.foxils.foxutils.commands.DebugHud;
import me.foxils.foxutils.commands.GetHuds;
import me.foxils.foxutils.listeners.PlayerJoinListener;
import me.foxils.foxutils.registry.HudRegistry;
import me.foxils.foxutils.utilities.HudConfigsContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FoxutilsHud extends JavaPlugin {

    private HudConfigsContainer hudConfigsContainer;

    @Override
    public void onEnable() {
        // Plugin startup logic
        hudConfigsContainer = HudRegistry.registerPluginHuds(this);

        registerCommands();
        registerEvents();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("debughud")).setExecutor(new DebugHud(this));
        Objects.requireNonNull(getCommand("gethuds")).setExecutor(new GetHuds());
    }

    @Override
    public void onDisable() {

    }
}
