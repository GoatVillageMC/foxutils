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

public final class ItemUtils {

    public static <Primitive, Complex> boolean storeDataOfType(@NotNull PersistentDataType<Primitive, Complex> type,
                                                               @NotNull Complex data,
                                                               @NotNull NamespacedKey key,
                                                               @NotNull ItemMeta itemMeta) {
        itemMeta.getPersistentDataContainer().set(key, type, data);
        return true;
    }

    public static boolean storeBooleanData(@NotNull Boolean booleanData,
                                           @NotNull NamespacedKey key,
                                           @NotNull ItemMeta itemMeta) {
        return storeDataOfType(PersistentDataType.BOOLEAN, booleanData, key, itemMeta);
    }

    public static boolean storeIntegerData(@NotNull Integer integerData,
                                           @NotNull NamespacedKey key,
                                           @NotNull ItemMeta itemMeta) {
        return storeDataOfType(PersistentDataType.INTEGER, integerData, key, itemMeta);
    }

    public static boolean storeStringData(@NotNull String stringData,
                                          @NotNull NamespacedKey key,
                                          @NotNull ItemMeta itemMeta) {
        return storeDataOfType(PersistentDataType.STRING, stringData, key, itemMeta);
    }

    public static <Primitive, Complex> boolean storeDataOfType(@NotNull PersistentDataType<Primitive, Complex> type,
                                                               @NotNull Complex data,
                                                               @NotNull NamespacedKey key,
                                                               @NotNull ItemStack itemStack) {
        if (!itemStack.hasItemMeta())
            return false;

        return storeDataOfType(type, data, key, itemStack.getItemMeta());
    }

    public static boolean storeBooleanData(@NotNull Boolean booleanData,
                                           @NotNull NamespacedKey key,
                                           @NotNull ItemStack itemStack) {
        return storeDataOfType(PersistentDataType.BOOLEAN, booleanData, key, itemStack);
    }

    public static boolean storeIntegerData(@NotNull Integer integerData,
                                           @NotNull NamespacedKey key,
                                           @NotNull ItemStack itemStack) {
        return storeDataOfType(PersistentDataType.INTEGER, integerData, key, itemStack);
    }

    public static boolean storeStringData(@NotNull String stringData,
                                          @NotNull NamespacedKey key,
                                          @NotNull ItemStack itemStack) {
        return storeDataOfType(PersistentDataType.STRING, stringData, key, itemStack);
    }

    public static <Primitive, Complex> @Nullable Complex getDataOfType(@NotNull PersistentDataType<Primitive, Complex> type,
                                                                       @NotNull NamespacedKey key,
                                                                       @NotNull ItemMeta itemMeta) {
        return itemMeta.getPersistentDataContainer().get(key, type);
    }

    public static @Nullable Boolean getBooleanData(@NotNull NamespacedKey key, @NotNull ItemMeta itemMeta) {
        return getDataOfType(PersistentDataType.BOOLEAN, key, itemMeta);
    }

    public static @Nullable Integer getIntegerData(@NotNull NamespacedKey key, @NotNull ItemMeta itemMeta) {
        return getDataOfType(PersistentDataType.INTEGER, key, itemMeta);
    }

    public static @Nullable String getStringData(@NotNull NamespacedKey key, @NotNull ItemMeta itemMeta) {
        return getDataOfType(PersistentDataType.STRING, key, itemMeta);
    }

    public static <Primitive, Complex> @Nullable Complex getDataOfType(@NotNull PersistentDataType<Primitive, Complex> type,
                                                                       @NotNull NamespacedKey key,
                                                                       @NotNull ItemStack itemStack) {
        if (!itemStack.hasItemMeta())
            return null;

        return itemStack.getItemMeta().getPersistentDataContainer().get(key, type);
    }

    public static @Nullable Boolean getBooleanData(@NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        return getDataOfType(PersistentDataType.BOOLEAN, key, itemStack);
    }

    public static @Nullable Integer getIntegerData(@NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        return getDataOfType(PersistentDataType.INTEGER, key, itemStack);
    }

    public static @Nullable String getStringData(@NotNull NamespacedKey key, @NotNull ItemStack itemStack) {
        return getDataOfType(PersistentDataType.STRING, key, itemStack);
    }

    public static @Nullable NamespacedKey getFoxItemKey(final @NotNull ItemMeta itemMeta) {
        final String itemKeyString = itemMeta.getPersistentDataContainer().get(PDCLocationKey.ITEMSTACK_ITEMKEY_STORAGE, PersistentDataType.STRING);

        return (itemKeyString == null) ? null : NamespacedKey.fromString(itemKeyString);
    }

    public static @Nullable NamespacedKey getFoxItemKey(final @NotNull ItemStack itemStack) {
        return (itemStack.hasItemMeta()) ? getFoxItemKey(itemStack.getItemMeta()) : null;
    }

    public static @Nullable UUID getUid(final @NotNull ItemMeta itemMeta) {
        final String itemUid = getStringData(PDCLocationKey.ITEMSTACK_UID_STORAGE, itemMeta);

        return (itemUid == null) ? null : UUID.fromString(itemUid);
    }

    public static @Nullable UUID getUid(final @NotNull ItemStack itemStack) {
        return (itemStack.hasItemMeta()) ? getUid(itemStack.getItemMeta()) : null;
    }

    public static @Nullable boolean setRelatedItemUid(final @NotNull Projectile projectile,
                                                      final @NotNull ItemMeta itemMeta) {
        final UUID itemUid = getUid(itemMeta);
        if (itemUid == null)
            return false;

        storeStringData(itemUid.toString(), PDCLocationKey.DATAHOLDER_RELATED_ITEMSTACK_UID_STORAGE, itemMeta);
        return true;
    }

    public static @Nullable boolean setRelatedItemUid(final @NotNull Projectile projectile,
                                                      final @NotNull ItemStack itemStack) {
        return (itemStack.hasItemMeta()) ? setRelatedItemUid(projectile, itemStack.getItemMeta()) : false;
    }

    public static @Nullable UUID getRelatedItemUid(final @NotNull Projectile projectile) {
        final String uidInStorage = projectile.getPersistentDataContainer().get(PDCLocationKey.DATAHOLDER_RELATED_ITEMSTACK_UID_STORAGE, PersistentDataType.STRING);

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
        return (itemStack.hasItemMeta()) ? getCooldown(key, itemStack.getItemMeta(), cooldownInSeconds) : false;
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
        if ((timestampOfNowInNanoseconds - Objects.requireNonNullElse(getDataOfType(PersistentDataType.LONG, key, itemMeta), 0L)) > (cooldownInSeconds * 1000000000)) {
            storeDataOfType(PersistentDataType.LONG, timestampOfNowInNanoseconds, key, itemMeta);
            return false;
        }

        return true;
    }

    private ItemUtils() {}
}
