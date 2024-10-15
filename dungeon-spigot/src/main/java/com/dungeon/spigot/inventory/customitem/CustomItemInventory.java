package com.dungeon.spigot.inventory.customitem;

import com.dungeon.common.cache.CustomItemCache;
import com.dungeon.common.customitem.CustomItem;
import com.dungeon.common.util.item.impl.ItemStackBuilder;
import com.dungeon.spigot.customitem.CustomItemAPI;
import me.saiintbrisson.minecraft.PaginatedView;
import me.saiintbrisson.minecraft.PaginatedViewSlotContext;
import me.saiintbrisson.minecraft.ViewItem;
import me.saiintbrisson.minecraft.ViewSlotClickContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CustomItemInventory extends PaginatedView<CustomItem> {

    private final CustomItemCache customItemCache;

    public CustomItemInventory(CustomItemCache customItemCache) {
        super(5, "Custom items");

        this.customItemCache = customItemCache;

        setSource(customItemCache.getAll());
        setLayout("XXXXXXXXX",
                "XXOOOOOXX",
                "XXOOOOOXX",
                "XXXXXXXXX",
                "<XXXXXXX>");

        setCancelOnClick(true);
    }

    @Override
    protected void onItemRender(@NotNull PaginatedViewSlotContext<CustomItem> paginatedViewSlotContext, @NotNull ViewItem viewItem, @NotNull CustomItem customItem) {
        viewItem.onRender(render -> render.setItem(getCustomItemIcon(customItem)))
                .onClick(onCustomItemClick(customItem));
    }

    public ItemStack getCustomItemIcon(CustomItem customItem) {
        final ItemStackBuilder itemStackBuilder = customItem.getBuilder();
        return new ItemStackBuilder(itemStackBuilder.build())
                .addLore(" ",
                        "<white> Key: </white><gray>" + customItem.getKey() + "</gray>",
                        " ")
                .build();

    }

    private Consumer<ViewSlotClickContext> onCustomItemClick(CustomItem customItem) {
        return context -> {
            final Player player = context.getPlayer();

            final CustomItemAPI customItemAPI = CustomItemAPI.getInstance();
            customItemAPI.give(player, 1, customItem);
        };
    }
}
