package me.foxils.foxutils;

import me.foxils.foxutils.itemactions.HoldingItemAction;
import me.foxils.foxutils.itemactions.PassiveAction;
import me.foxils.foxutils.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Items extends JavaPlugin {

    public static final List<Integer> taskIDs = new ArrayList<>();

    @Override
    public void onEnable() {
        scheduleTasks();
        registerEvents();
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

    private void registerEvents() {
        // Bulk of this is done in the ItemRegistry.registerItem() method for items that implement interfaces that extend listeners

        // Above was idiotic and ruined a lot of the weapons' interactions, now all will be fixed though, at least I learned
        getServer().getPluginManager().registerEvents(new AttackActionListener(), this);
        getServer().getPluginManager().registerEvents(new ClickActionsListener(), this);
        getServer().getPluginManager().registerEvents(new DoubleJumpListener(), this);
        getServer().getPluginManager().registerEvents(new DropActionListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickActionListener(), this);
        getServer().getPluginManager().registerEvents(new KillActionListener(), this);
        getServer().getPluginManager().registerEvents(new MineActionListener(), this);
        getServer().getPluginManager().registerEvents(new TakeDamageActionListener(), this);
    }

    private void cancelTasks() {
        for (int taskID : taskIDs) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }
}
