package me.foxils.foxutils;

import me.foxils.foxutils.itemactions.HoldingItemAction;
import me.foxils.foxutils.itemactions.PassiveAction;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Items extends JavaPlugin {

    public static final List<Integer> taskIDs = new ArrayList<>();

    private static Items instance;

    @Override
    public void onEnable() {
        instance = this;

        scheduleTasks();
        registerEvents();
    }

    @Override
    public void onDisable() {
        cancelTasks();
    }

    private static void scheduleTasks() {
        taskIDs.addAll(Arrays.asList(
                Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, PassiveAction::passiveCall, PassiveAction.passiveTaskInterval, PassiveAction.passiveTaskInterval),
                Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, HoldingItemAction::holdActionCall, HoldingItemAction.holdActionInterval, HoldingItemAction.holdActionInterval)));
    }

    private static void registerEvents() {
        // Bulk of this is done in the ItemRegistry.registerItem() method for items that implement interfaces that extend listeners
    }

    private static void cancelTasks() {
        for (int taskID : taskIDs) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }

    public static Items getInstance() {
        return instance;
    }
}
