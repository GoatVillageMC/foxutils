package me.foxils.foxutils.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

import me.foxils.foxutils.itemaction.DoubleJumpAction;
import me.foxils.foxutils.registry.ItemRegistry;

public final class DoubleJumpListener implements Listener {

    // TODO: Rewrite this whole thing cause its trash and requires a dependency and that's cringe

    @EventHandler
    public void onDoubleJump(final PlayerToggleFlightEvent toggleFlightEvent) {
        final Player player = toggleFlightEvent.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)
            return;

        toggleFlightEvent.setCancelled(true);

        if (!(toggleFlightEvent.isFlying()))
            return;

        for (final ItemStack itemStack : player.getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(itemStack) instanceof final DoubleJumpAction itemWithDoubleJumpAction))
                continue;

            itemWithDoubleJumpAction.onDoubleJump(toggleFlightEvent, itemStack);
        }
    }
}
