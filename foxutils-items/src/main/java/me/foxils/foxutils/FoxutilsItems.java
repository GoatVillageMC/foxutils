package me.foxils.foxutils;

import me.foxils.foxutils.commands.GetRegisteredItems;
import me.foxils.foxutils.commands.ListRegisteredItems;
import me.foxils.foxutils.itemactions.HoldingItemAction;
import me.foxils.foxutils.itemactions.PassiveAction;
import me.foxils.foxutils.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class FoxutilsItems extends JavaPlugin {

    public static final List<Integer> taskIDs = new ArrayList<>();

    @Override
    public void onEnable() {
        scheduleTasks();
        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        cancelTasks();
    }

    private void scheduleTasks() {
        taskIDs.addAll(Arrays.asList(
                Bukkit.getScheduler().scheduleSyncRepeatingTask(this, PassiveAction::passiveCall, PassiveAction.passiveTaskInterval, PassiveAction.passiveTaskInterval),
                Bukkit.getScheduler().scheduleSyncRepeatingTask(this, HoldingItemAction::holdActionCall, HoldingItemAction.holdActionInterval, HoldingItemAction.holdActionInterval)));
    }

    private void registerCommands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("get")).setExecutor(new GetRegisteredItems(this));
        Objects.requireNonNull(Bukkit.getPluginCommand("listitems")).setExecutor(new ListRegisteredItems());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new AttackActionListener(), this);
        getServer().getPluginManager().registerEvents(new ClickActionsListener(), this);
        getServer().getPluginManager().registerEvents(new DoubleJumpListener(), this);
        getServer().getPluginManager().registerEvents(new DropActionListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickActionListener(this), this);
        getServer().getPluginManager().registerEvents(new KillActionListener(), this);
        getServer().getPluginManager().registerEvents(new MineActionListener(), this);
        getServer().getPluginManager().registerEvents(new ShootActionListener(), this);
        getServer().getPluginManager().registerEvents(new SwapHandsActionListener(), this);
        getServer().getPluginManager().registerEvents(new TakeDamageActionListener(), this);
    }

    private void cancelTasks() {
        for (int taskID : taskIDs) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }
}
