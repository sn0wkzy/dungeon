package com.dungeon.common.model.dungeon.shop.item;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import static com.dungeon.common.keys.DungeonKeys.THREAD_LOCAL_RANDOM;

@Getter
public class ShopItem {

    private final ItemStack itemStack;
    private final double price;
    private int stock;

    public ShopItem(ItemStack itemStack, double price) {
        this.itemStack = itemStack;
        this.price = price;

        this.stock = THREAD_LOCAL_RANDOM.nextInt(1, 10);
    }

    public void decreaseStock() {
        this.stock--;
    }
}
