package me.foxils.foxutils.utility;

import java.util.Objects;
import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.foxils.foxutils.FoxutilsItems.PDCLocationKey;

@SuppressWarnings("UnstableApiUsage")
public final class ItemUtils {


    public static @Nullable NamespacedKey getFoxItemKey(final @Nullable ItemMeta itemMeta) {
        if (itemMeta == null)
            return null;

        final String itemKeyString = itemMeta.getPersistentDataContainer().get(PDCLocationKey.ITEMSTACK_ITEMKEY_STORAGE, PersistentDataType.STRING);

        return (itemKeyString == null) ? null : NamespacedKey.fromString(itemKeyString);
    }

    public static @Nullable UUID getUidFromItemMeta(final @NotNull ItemMeta itemMeta) {
        final String itemUid = itemMeta.getPersistentDataContainer().get(PDCLocationKey.ITEMSTACK_UID_STORAGE, PersistentDataType.STRING);

        return (itemUid == null) ? null : UUID.fromString(itemUid);
    }

    public static @Nullable UUID getUidFromItemStack(final @NotNull ItemStack itemStack) {
        return getUidFromItemMeta(itemStack.getItemMeta());
    }

    public static @Nullable boolean setRelatedItemUid(final @NotNull Projectile projectile,
                                                      final @NotNull ItemMeta itemMeta) {
        final UUID itemUid = getUidFromItemMeta(itemMeta);
        if (itemUid == null)
            return false;

        projectile.getPersistentDataContainer().set(PDCLocationKey.DATAHOLDER_RELATED_ITEMSTACK_UID_STORAGE, PersistentDataType.STRING,
                itemUid.toString());
        return true;
    }

    public static @Nullable boolean setRelatedItemUid(final @NotNull Projectile projectile,
                                                      final @NotNull ItemStack itemStack) {
        if (!itemStack.hasItemMeta())
            return false;

        return (itemStack.hasItemMeta()) ? setRelatedItemUid(projectile, itemStack.getItemMeta()) : false;
    }

    public static @Nullable UUID getRelatedItemUid(final @NotNull Projectile projectile) {
        final String uidInStorage = projectile.getPersistentDataContainer().get(PDCLocationKey.DATAHOLDER_RELATED_ITEMSTACK_UID_STORAGE,
                PersistentDataType.STRING);

        return (uidInStorage == null) ? null : UUID.fromString(uidInStorage);
    }

    /**
     * @param key               Location to where the cooldown should be and/or is stored.
     * @param itemStack         ItemStack to apply a cooldown to.
     * @param cooldownInSeconds The cooldown in seconds.
     * @return True if there is a cooldown or False if there is not.
     */
    public static boolean getCooldown(final @NotNull NamespacedKey key,
                                      final @NotNull ItemStack itemStack,
                                      final double cooldownInSeconds) {
        if (!itemStack.hasItemMeta())
            return false;

        return getCooldown(key, itemStack.getItemMeta(), cooldownInSeconds);
    }

    /**
     * @param key               Location to where the cooldown should be and/or is stored.
     * @param itemMeta          ItemMeta of an ItemStack to apply a cooldown to.
     * @param cooldownInSeconds The cooldown in seconds.
     * @return True if there is a cooldown or False if there is not.
     */
    public static boolean getCooldown(final @NotNull NamespacedKey key,
                                      final @NotNull ItemMeta itemMeta,
                                      final double cooldownInSeconds) {
        final long timestampOfNowInNanoseconds = System.nanoTime();

        // (timestampOfNowInNanoseconds - timestampOfLastCooldownInNanoseconds) > (cooldownInSeconds * 1000000000)
        // timeSinceLastCooldownInNanoseconds > cooldownInNanoseconds
        if ((timestampOfNowInNanoseconds - Objects.requireNonNullElse(itemMeta.getPersistentDataContainer().get(key, PersistentDataType.LONG),
                0L)) > (cooldownInSeconds * 1000000000)) {
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.LONG, timestampOfNowInNanoseconds);
            return false;
        }

        return true;
    }

    private ItemUtils() {}
}
