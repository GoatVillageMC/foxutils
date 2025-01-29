package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.DoubleJumpAction;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public final class DoubleJumpListener implements Listener {

    // TODO: Rewrite this whole thing cause its trash and requires a dependency and that's cringe
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player playerWhoJoined = playerJoinEvent.getPlayer();

        // ArmorPlugin.reequipPlayer(playerWhoJoined);
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent toggleFlightEvent) {
        Player player = toggleFlightEvent.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)
            return;

        toggleFlightEvent.setCancelled(true);

        if (!(toggleFlightEvent.isFlying()))
            return;

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof DoubleJumpAction itemWithDoubleJumpAction))
                continue;

            itemWithDoubleJumpAction.onDoubleJump(toggleFlightEvent, itemStack);
        }
    }
}
