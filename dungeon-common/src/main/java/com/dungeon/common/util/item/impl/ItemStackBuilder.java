package com.dungeon.common.util.item.impl;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class ItemStackBuilder {

    private final ItemStack itemStack;

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }

    public ItemStackBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemStackBuilder(Material material, int data) {
        this(new ItemStack(material, (short) data));
    }

    public ItemStackBuilder displayName(String displayName) {
        final MiniMessage miniMessage = MiniMessage.miniMessage();
        return modifyItemMeta(itemMeta -> itemMeta.displayName(miniMessage.deserialize(displayName)));
    }

    public ItemStackBuilder lore(String... lore) {
        final MiniMessage miniMessage = MiniMessage.miniMessage();
        final List<Component> components = Arrays.stream(lore)
                .map(miniMessage::deserialize)
                .toList();

        return modifyItemMeta(itemMeta -> itemMeta.lore(components));
    }

    public ItemStackBuilder lore(List<String> lore) {
        final MiniMessage miniMessage = MiniMessage.miniMessage();
        final List<Component> components = lore.stream()
                .map(miniMessage::deserialize)
                .toList();
        return modifyItemMeta(itemMeta -> itemMeta.lore(components));
    }

    public ItemStackBuilder addLore(String... lore) {
        List<Component> currentLore = itemStack.getItemMeta().lore();
        if (currentLore == null) {
            currentLore = new ArrayList<>();
        }

        final MiniMessage miniMessage = MiniMessage.miniMessage();
        final List<Component> components = Arrays.stream(lore)
                .map(miniMessage::deserialize)
                .toList();
        currentLore.addAll(components);

        final List<Component> finalLore = currentLore;
        return modifyItemMeta(itemMeta -> itemMeta.lore(finalLore));
    }

    public ItemStackBuilder clearLore() {
        return modifyItemMeta(itemMeta -> itemMeta.setLore(new ArrayList<>()));
    }

    public ItemStackBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder enchantment(Enchantment enchant, int level) {
        return modifyItemMeta(itemMeta -> itemMeta.addEnchant(enchant, level, true));
    }

    public ItemStackBuilder removeEnchantment(Enchantment enchant) {
        return modifyItemMeta(itemMeta -> itemMeta.removeEnchant(enchant));
    }

    public ItemStackBuilder itemFlags(ItemFlag... flags) {
        return modifyItemMeta(itemMeta -> itemMeta.addItemFlags(flags));
    }

    public ItemStackBuilder hideAll() {
        return modifyItemMeta(itemMeta -> itemMeta.addItemFlags(ItemFlag.values()));
    }

    public ItemStackBuilder skullFromName(String playerName) {
        final SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        if (itemStack.getType() != Material.PLAYER_HEAD) {
            return null;
        }

        final OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        skullMeta.setOwningPlayer(player);

        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemStackBuilder addPersistentData(Plugin plugin, String key, String value) {
        return modifyItemMeta(itemMeta -> {
            final NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

            final PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
            dataContainer.set(namespacedKey, PersistentDataType.STRING, value);
        });
    }

    public String getPersistentData(Plugin plugin, String key) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }

        final NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        final PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        return dataContainer.get(namespacedKey, PersistentDataType.STRING);
    }

    public boolean hasPersistentData(Plugin plugin, String key) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return false;
        }

        final NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        final PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        return dataContainer.has(namespacedKey);
    }

    public ItemStack build() {
        return itemStack;
    }

    private ItemStackBuilder modifyItemMeta(Consumer<ItemMeta> consumer) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        consumer.accept(itemMeta);

        itemStack.setItemMeta(itemMeta);
        return this;
    }
}