package me.foxils.foxutils;

import me.foxils.foxutils.commands.GetRegisteredItem;
import me.foxils.foxutils.commands.ListRegisteredItems;
import me.foxils.foxutils.itemactions.HoldingItemAction;
import me.foxils.foxutils.itemactions.PassiveAction;
import me.foxils.foxutils.items.ThyTestItem;
import me.foxils.foxutils.listeners.AttackActionListener;
import me.foxils.foxutils.listeners.ClickActionsListener;
import me.foxils.foxutils.listeners.CraftItemActionListener;
import me.foxils.foxutils.listeners.DoubleJumpListener;
import me.foxils.foxutils.listeners.DropActionListener;
import me.foxils.foxutils.listeners.InventoryClickActionListener;
import me.foxils.foxutils.listeners.SelectItemActionListener;
import me.foxils.foxutils.listeners.KillActionListener;
import me.foxils.foxutils.listeners.MineBlockActionListener;
import me.foxils.foxutils.listeners.ProjectileHitActionListener;
import me.foxils.foxutils.listeners.ProjectileLaunchActionListener;
import me.foxils.foxutils.listeners.SwapOffHandActionListener;
import me.foxils.foxutils.listeners.TakeDamageActionListener;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utilities.ActionType;
import me.foxils.foxutils.utilities.ItemAbility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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

        ItemRegistry.registerItem(new ThyTestItem(this, Material.TRIDENT, "Thy Test Item", 43290423,
                List.of(
                        new ItemAbility("Test Shit",
                                List.of("They see me testin'",
                                        ChatColor.ITALIC + "They hatin'"
                                ), ActionType.NONE)
                ),
                List.of(
                        new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER),
                        new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER),
                        new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER)
                ), false));

        this.getLogger().info("foxutils-items Started and initialized");
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
        Objects.requireNonNull(Bukkit.getPluginCommand("getitem")).setExecutor(new GetRegisteredItem(this));
        Objects.requireNonNull(Bukkit.getPluginCommand("listitems")).setExecutor(new ListRegisteredItems());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new AttackActionListener(), this);
        getServer().getPluginManager().registerEvents(new ClickActionsListener(), this);
        getServer().getPluginManager().registerEvents(new CraftItemActionListener(), this);
        getServer().getPluginManager().registerEvents(new DoubleJumpListener(), this);
        getServer().getPluginManager().registerEvents(new DropActionListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickActionListener(), this);
        getServer().getPluginManager().registerEvents(new SelectItemActionListener(), this);
        getServer().getPluginManager().registerEvents(new KillActionListener(), this);
        getServer().getPluginManager().registerEvents(new MineBlockActionListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitActionListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileLaunchActionListener(), this);
        getServer().getPluginManager().registerEvents(new SwapOffHandActionListener(), this);
        getServer().getPluginManager().registerEvents(new TakeDamageActionListener(), this);
    }

    private void cancelTasks() {
        for (int taskID : taskIDs) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }
}
