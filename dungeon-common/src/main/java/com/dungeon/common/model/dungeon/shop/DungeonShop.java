package com.dungeon.common.model.dungeon.shop;

import com.dungeon.common.model.dungeon.shop.item.ShopItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
public class DungeonShop {

    private final List<ShopItem> items = new ArrayList<>();

}
