package me.foxils.foxutils.listeners;

import me.foxils.foxutils.ItemRegistry;
import me.foxils.foxutils.itemactions.InventoryClickAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class InventoryClickActionListener implements Listener {

    private final Plugin plugin;

    public InventoryClickActionListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final HumanEntity whoClicked = event.getWhoClicked();

        switch (event.getClick()) {
            case NUMBER_KEY -> {
                final int hotbarItemSlot = event.getHotbarButton();
                final int itemStackSlot = event.getSlot();
                final Inventory inventorySentTo = event.getClickedInventory();
                final Inventory playerInventory = whoClicked.getInventory();

                if (inventorySentTo == playerInventory) return;

                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    ItemStack itemStackClickedOn = inventorySentTo.getItem(itemStackSlot);
                    ItemStack itemStackSwappedWith = playerInventory.getItem(hotbarItemSlot);

                    if (!(ItemRegistry.getItemFromItemStack(itemStackClickedOn) instanceof InventoryClickAction inventoryClickActionItem))
                        return;

                    playerInventory.setItem(hotbarItemSlot, itemStackClickedOn);
                    inventorySentTo.setItem(itemStackSlot, itemStackSwappedWith);

                    inventoryClickActionItem.onInvetoryPull(event, itemStackClickedOn);
                }, 1L);
            }
            case SWAP_OFFHAND -> {
                final int itemStackSlot = event.getSlot();
                final Inventory inventorySentTo = event.getClickedInventory();
                final PlayerInventory originInventory = whoClicked.getInventory();

                if (inventorySentTo == null) return;
                if (inventorySentTo == originInventory) return;

                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    final ItemStack itemStackClickedOn = inventorySentTo.getItem(itemStackSlot);
                    final ItemStack itemStackSwappedWith = originInventory.getItemInOffHand();

                    if (!(ItemRegistry.getItemFromItemStack(itemStackClickedOn) instanceof InventoryClickAction inventoryClickActionItem))
                        return;

                    originInventory.setItemInOffHand(itemStackClickedOn);
                    inventorySentTo.setItem(itemStackSlot, itemStackSwappedWith);

                    inventoryClickActionItem.onInvetoryPull(event, itemStackClickedOn);
                }, 1L);
            }
            default -> {
                ItemStack itemStackClickedOn = event.getCurrentItem();

                if (!(ItemRegistry.getItemFromItemStack(itemStackClickedOn) instanceof InventoryClickAction inventoryClickActionItem))
                    return;

                inventoryClickActionItem.onInvetoryPull(event, itemStackClickedOn);
            }
        }
    }
}
