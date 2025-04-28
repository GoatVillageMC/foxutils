package me.foxils.foxutils;

import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import me.foxils.foxutils.command.GetRegisteredItem;
import me.foxils.foxutils.command.ListRegisteredItems;
import me.foxils.foxutils.item.ThyTestItem;
import me.foxils.foxutils.itemaction.ActiveItemAction;
import me.foxils.foxutils.itemaction.PassiveAction;
import me.foxils.foxutils.listener.AttackActionListener;
import me.foxils.foxutils.listener.ClickActionsListener;
import me.foxils.foxutils.listener.CraftItemActionListener;
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
import me.foxils.foxutils.utility.ItemAbility;
import me.foxils.foxutils.utility.ItemAbility.ActionType;
import net.md_5.bungee.api.ChatColor;

public final class FoxutilsItems extends JavaPlugin {

    private static final long PASSIVE_TASK_INTERVAL_TICKS = 5;

    private ItemKey itemKey;
    private ItemRegistry itemRegistry;

    @Override
    public void onEnable() {
        itemRegistry = new ItemRegistry();
        Bukkit.getServicesManager().register(ItemRegistry.class, itemRegistry, this, ServicePriority.Highest);

        itemKey = new ItemKey(this);
        PDCLocationKey.initializeKeys(this);

        registerEvents();
        scheduleTasks();
        registerCommands();
        registerItems();
    }

    private void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new AttackActionListener(itemRegistry), this);
        pluginManager.registerEvents(new ClickActionsListener(itemRegistry), this);
        pluginManager.registerEvents(new CraftItemActionListener(itemRegistry), this);
        pluginManager.registerEvents(new DropActionListener(itemRegistry), this);
        pluginManager.registerEvents(new InventoryClickActionListener(itemRegistry), this);
        pluginManager.registerEvents(new SelectItemActionListener(itemRegistry), this);
        pluginManager.registerEvents(new KillActionListener(itemRegistry), this);
        pluginManager.registerEvents(new MineBlockActionListener(itemRegistry), this);
        pluginManager.registerEvents(new ProjectileHitActionListener(itemRegistry), this);
        pluginManager.registerEvents(new ProjectileLaunchActionListener(itemRegistry), this);
        pluginManager.registerEvents(new SwapOffHandActionListener(itemRegistry), this);
        pluginManager.registerEvents(new TakeDamageActionListener(itemRegistry), this);
    }

    private void scheduleTasks() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                final PlayerInventory playerInventory = player.getInventory();

                for (final ItemStack itemStack : playerInventory.getContents()) {
                    if (!(itemRegistry.getItemFromItemStack(itemStack) instanceof final PassiveAction passiveActionItem))
                        continue;
                    passiveActionItem.onPassiveAction(player, itemStack);
                }

                for (final ItemStack itemStack : new ItemStack[]{playerInventory.getHelmet(), playerInventory.getChestplate(), playerInventory.getLeggings(), playerInventory.getBoots(), playerInventory.getItemInMainHand(), playerInventory.getItemInOffHand()}) {
                    if (!(itemRegistry.getItemFromItemStack(itemStack) instanceof final ActiveItemAction activeItemActionItem))
                        continue;

                    activeItemActionItem.onActiveAction(player, itemStack);
                }
            }
        }, 0L, PASSIVE_TASK_INTERVAL_TICKS);
    }

    private void registerCommands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("getitem")).setExecutor(new GetRegisteredItem(itemRegistry));
        Objects.requireNonNull(Bukkit.getPluginCommand("listitems")).setExecutor(new ListRegisteredItems(itemRegistry));
    }

    private void registerItems() {
        itemRegistry.registerItem(new ThyTestItem(itemKey.TEST_ITEM, this, Material.TRIDENT, "Thy Test Item", null,
                List.of(
                        new ItemAbility("Test Shit",
                                List.of(
                                        "They see me testin'", ChatColor.ITALIC + "They hatin'"
                                ), ActionType.NONE, null)
                ),
                List.of(
                        new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(
                                Material.BARRIER),
                        new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(
                                Material.BARRIER),
                        new ItemStack(Material.BARRIER), new ItemStack(Material.BARRIER), new ItemStack(
                                Material.BARRIER)),
                false, false));
    }

    public final class PDCLocationKey {
        public static NamespacedKey ITEMSTACK_ITEMKEY_STORAGE;
        public static NamespacedKey ITEMSTACK_UID_STORAGE;
        public static NamespacedKey DATAHOLDER_RELATED_ITEMSTACK_UID_STORAGE;
        private static boolean keysInitialized = false;

        static final void initializeKeys(final @NotNull FoxutilsItems plugin) {
            if (keysInitialized)
                return;

            ITEMSTACK_ITEMKEY_STORAGE = new NamespacedKey(plugin, "fox_item");
            ITEMSTACK_UID_STORAGE = new NamespacedKey(plugin, "item_uid_storage");
            DATAHOLDER_RELATED_ITEMSTACK_UID_STORAGE = new NamespacedKey(plugin, "related_itemstack_uids_storage");

            keysInitialized = true;
        }

        private PDCLocationKey() {}
    }

    private final class ItemKey {
        public final NamespacedKey TEST_ITEM;

        ItemKey(final @NotNull FoxutilsItems plugin) {
            this.TEST_ITEM = new NamespacedKey(plugin, "test_item");
        }
    }
}
