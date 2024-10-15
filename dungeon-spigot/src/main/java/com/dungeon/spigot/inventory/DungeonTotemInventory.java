package com.dungeon.spigot.inventory;

import com.dungeon.common.factory.type.DungeonEntityTypeFactory;
import com.dungeon.common.model.dungeon.DungeonContext;
import com.dungeon.common.model.entity.type.DungeonEntityType;
import com.dungeon.common.model.view.KilledMob;
import com.dungeon.common.util.NumberFormatter;
import com.dungeon.common.util.item.impl.ItemStackBuilder;
import me.saiintbrisson.minecraft.PaginatedView;
import me.saiintbrisson.minecraft.PaginatedViewSlotContext;
import me.saiintbrisson.minecraft.ViewContext;
import me.saiintbrisson.minecraft.ViewItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DungeonTotemInventory extends PaginatedView<KilledMob> {

    public DungeonTotemInventory() {
        super(5 * 9, "Totem");

        setSource(context -> {
            final DungeonContext dungeonContext = context.get("dungeonContext");
            return dungeonContext.getKilledMobs();
        });
        setLayout("XXXXXXXXX",
                "XXXXXXXXX",
                "XXOOOOOXX",
                "XXOOOOOXX",
                "<XXXXXXX>");

        setCancelOnClick(true);
        setCancelOnDrop(true);
        setCancelOnDrag(true);
        setCancelOnPickup(true);
    }

    @Override
    protected void onRender(@NotNull ViewContext context) {

        final DungeonContext dungeonContext = context.get("dungeonContext");

        context.slot(3).onRender(render -> render.setItem(getShopIcon()));
        context.slot(5).onRender(render -> render.setItem(getCheckpointIcon()));
    }

    @Override
    protected void onItemRender(@NotNull PaginatedViewSlotContext<KilledMob> paginatedViewSlotContext, @NotNull ViewItem viewItem, @NotNull KilledMob killedMob) {
        viewItem.onRender(render -> render.setItem(getKilledMobIcon(killedMob)));
    }

    private ItemStack getShopIcon() {
        return new ItemStackBuilder(Material.CRAFTING_TABLE)
                .displayName("<color:#90aa3b>Loja de suprimentos</color>")
                .addLore("<color:#919191>Clique para abrir a loja.</color>")
                .build();
    }

    private ItemStack getCheckpointIcon() {
        return new ItemStackBuilder(Material.SUNFLOWER)
                .displayName("<color:#90aa3b>Checkpoints</color>")
                .addLore("<color:#919191>Clique para visualizar os seus</color>",
                        "<color:#919191>checkpoints de waves.</color>")
                .build();
    }

    private ItemStack getKilledMobIcon(@NotNull KilledMob killedMob) {
        final DungeonEntityType dungeonEntityType = DungeonEntityTypeFactory.getType(killedMob.getEntityType());

        //todo get correct skull
        return new ItemStackBuilder(Material.SKELETON_SKULL)
                .displayName(dungeonEntityType.getSimpleName())
                .addLore("<color:#919191>Mobs mortos: </color><color:#FFFFFF>" + NumberFormatter.format(killedMob.getAmount()) + "</color>")
                .build();
    }
}
