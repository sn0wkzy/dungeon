package com.dungeon.common.factory.shop;

import com.dungeon.common.model.dungeon.shop.item.ShopItem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopItemFactory {

    private static final LinkedList<ShopItem> cache = new LinkedList<>() {
        {
            //todo add shop items
        }
    };

    public List<ShopItem> getShuffledItems() {
        final LinkedList<ShopItem> shopItems = new LinkedList<>(cache);
        Collections.shuffle(shopItems);

        return shopItems.stream()
                .limit(10)
                .collect(Collectors.toList());
    }
}
