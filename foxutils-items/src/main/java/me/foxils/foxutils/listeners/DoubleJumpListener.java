package me.foxils.foxutils.listeners;

import me.foxils.foxutils.registry.ItemRegistry;
import me.foxils.foxutils.itemactions.DoubleJumpAction;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public class DoubleJumpListener implements Listener {

    /*
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player playerWhoJoined = playerJoinEvent.getPlayer();

        ArmorPlugin.reequipPlayer(playerWhoJoined);
    }*/

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent toggleFlightEvent) {
        final Player player = toggleFlightEvent.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        // Could interfere with other plugins and general flying for admins
        // TODO: Find a proper way to do this
        toggleFlightEvent.setCancelled(true);

        if (!toggleFlightEvent.isFlying()) return;

        for (ItemStack item : player.getInventory().getContents()) {
            if (!(ItemRegistry.getItemFromItemStack(item) instanceof DoubleJumpAction itemWithDoubleJumpAction)) continue;

            itemWithDoubleJumpAction.doubleJumpAction(toggleFlightEvent, item);
        }
    }
}
