package me.foxils.foxutils.items;

import me.foxils.foxutils.Item;
import me.foxils.foxutils.itemactions.CraftItemAction;
import me.foxils.foxutils.itemactions.KillActions;
import me.foxils.foxutils.utilities.ItemAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThyTestItem extends Item implements CraftItemAction, KillActions {

    public ThyTestItem(@NotNull Material material, int customModelData, @NotNull String name, @NotNull Plugin plugin, @Nullable List<ItemAbility> abilityList) {
        super(material, customModelData, name, plugin, abilityList);
    }

    @Override
    public void onCraftThisItem(CraftItemEvent craftItemEvent, ItemStack thisItemStack, Player crafterPlayer) {
        crafterPlayer.sendMessage("You have crafted a test item somehow tf");
    }

    @Override
    public void onCraftOtherItem(CraftItemEvent craftItemEvent, ItemStack craftedItemStack, ItemStack thisItemStack, Player crafterPlayer) {
        crafterPlayer.sendMessage("You have crafted another item and the test item detected it");
    }

    @Override
    public void onKillWithThisItem(PlayerDeathEvent playerDeathEvent, ItemStack thisItemStack, Player killedPlayer, Player killerPlayer) {
        killerPlayer.sendMessage("You killed with the test item.");
    }

    @Override
    public void onKillWithOtherItem(PlayerDeathEvent playerDeathEvent, ItemStack thisItemStack, ItemStack itemStackUsedToKill, Player killedPlayer, Player killerPlayer) {
        killerPlayer.sendMessage("You killed with an item and the test item detected it");
    }
}
