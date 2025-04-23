package me.foxils.foxutils;

import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.command.GetRegisteredItem;
import me.foxils.foxutils.command.ListRegisteredItems;
import me.foxils.foxutils.item.ThyTestItem;
import me.foxils.foxutils.itemaction.HoldingItemAction;
import me.foxils.foxutils.itemaction.PassiveAction;
import me.foxils.foxutils.listener.AttackActionListener;
import me.foxils.foxutils.listener.ClickActionsListener;
import me.foxils.foxutils.listener.CraftItemActionListener;
import me.foxils.foxutils.listener.DoubleJumpListener;
import me.foxils.foxutils.listener.DropActionListener;
import me.foxils.foxutils.listener.InventoryClickActionListener;
import me.foxils.foxutils.listener.KillActionListener;
import me.foxils.foxutils.listener.MineBlockActionListener;
import me.foxils.foxutils.listener.ProjectileHitActionListener;
import me.foxils.foxutils.listener.ProjectileLaunchActionListener;
import me.foxils.foxutils.listener.SelectItemActionListener;
import me.foxils.foxutils.listener.SwapOffHandActionListener;
import me.foxils.foxutils.listener.TakeDamageActionListener;
import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.utility.ActionType;
import me.foxils.foxutils.utility.ItemAbility;
import net.md_5.bungee.api.ChatColor;

public final class FoxutilsItems extends JavaPlugin {

    private ItemKey itemKey;

    @Override
    public void onEnable() {
        itemKey = new ItemKey(this);

        registerEvents();
        scheduleTasks();
        registerCommands();
        registerItems();

        getLogger().info("foxutils-items Started and initialized");
    }

    private void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new AttackActionListener(), this);
        pluginManager.registerEvents(new ClickActionsListener(), this);
        pluginManager.registerEvents(new CraftItemActionListener(), this);
        pluginManager.registerEvents(new DoubleJumpListener(), this);
        pluginManager.registerEvents(new DropActionListener(), this);
        pluginManager.registerEvents(new InventoryClickActionListener(), this);
        pluginManager.registerEvents(new SelectItemActionListener(), this);
        pluginManager.registerEvents(new KillActionListener(), this);
        pluginManager.registerEvents(new MineBlockActionListener(), this);
        pluginManager.registerEvents(new ProjectileHitActionListener(), this);
        pluginManager.registerEvents(new ProjectileLaunchActionListener(), this);
        pluginManager.registerEvents(new SwapOffHandActionListener(), this);
        pluginManager.registerEvents(new TakeDamageActionListener(), this);
    }

    private void scheduleTasks() {
        final BukkitScheduler bukkitScheduler = Bukkit.getScheduler();

        bukkitScheduler.scheduleSyncRepeatingTask(this, PassiveAction::passiveCall, PassiveAction.passiveTaskInterval, PassiveAction.passiveTaskInterval);
        bukkitScheduler.scheduleSyncRepeatingTask(this, HoldingItemAction::holdActionCall, HoldingItemAction.holdActionInterval, HoldingItemAction.holdActionInterval);
    }

    private void registerCommands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("getitem")).setExecutor(new GetRegisteredItem(this));
        Objects.requireNonNull(Bukkit.getPluginCommand("listitems")).setExecutor(new ListRegisteredItems());
    }

    private void registerItems() {
        ItemRegistry.registerItem(new ThyTestItem(itemKey.TEST_ITEM, this, Material.TRIDENT, "Thy Test Item", 43290423, List.of(
                new ItemAbility("Test Shit", List.of("They see me testin'", ChatColor.ITALIC + "They hatin'"
                ), ActionType.NONE)
        ), List.of(
                new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER)
        ), false, false));
    }

    public final ItemKey getItemKey() {
        return itemKey;
    }

    private final class ItemKey {
        public final NamespacedKey TEST_ITEM;

        ItemKey(final @NotNull FoxutilsItems plugin) {
            this.TEST_ITEM = new NamespacedKey(plugin, "test_item");
        }
    }
}
