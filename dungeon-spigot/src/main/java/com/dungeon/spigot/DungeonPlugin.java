package com.dungeon.spigot;

import com.dungeon.common.cache.CustomItemCache;
import com.dungeon.common.cache.DungeonCache;
import com.dungeon.common.command.CustomCommand;
import com.dungeon.common.customitem.impl.BarrierCustomItem;
import com.dungeon.common.util.item.impl.ItemStackBuilder;
import com.dungeon.spigot.command.CustomItemCommand;
import com.dungeon.spigot.customitem.CustomItemAPI;
import com.dungeon.spigot.inventory.DungeonTotemInventory;
import com.dungeon.spigot.inventory.customitem.CustomItemInventory;
import com.dungeon.spigot.listener.PlayerConnectionListener;
import com.dungeon.spigot.listener.PlayerInteractListener;
import com.dungeon.spigot.listener.customitem.CustomItemListener;
import com.dungeon.spigot.listener.protocol.DungeonTotemInteractListener;
import com.dungeon.spigot.task.DungeonTickTask;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.EventManager;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.SneakyThrows;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandMap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.patheloper.mapping.PatheticMapper;

import java.lang.reflect.Field;

public class DungeonPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().init();
        PatheticMapper.initialize(this);

        final ViewFrame viewFrame = ViewFrame.of(this);
        viewFrame.setNextPageItem(
                (context, viewItem) -> viewItem.rendered(() -> context.hasNextPage() ? getNextPageItem() : null));

        viewFrame.setPreviousPageItem((context, viewItem) ->
                viewItem.rendered(() -> context.hasPreviousPage() ? getPreviousPageItem() : null));

        final DungeonCache dungeonCache = new DungeonCache();
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(dungeonCache), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(dungeonCache), this);

        final CustomItemCache customItemCache = new CustomItemCache();
        customItemCache.registerAll(new BarrierCustomItem(dungeonCache));

        viewFrame.with(new DungeonTotemInventory(), new CustomItemInventory(customItemCache));
        viewFrame.register();

        registerCommand(new CustomItemCommand(viewFrame));
        Bukkit.getPluginManager().registerEvents(new CustomItemListener(customItemCache), this);

        new CustomItemAPI(customItemCache);

        final EventManager eventManager = PacketEvents.getAPI().getEventManager();
        eventManager.registerListener(new DungeonTotemInteractListener(dungeonCache, viewFrame), PacketListenerPriority.LOW);

        final BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(this, new DungeonTickTask(dungeonCache), 10L, 10L);
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    @SneakyThrows
    private void registerCommand(CustomCommand customCommand) {
        final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMapField.setAccessible(true);

        final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        commandMap.register(customCommand.getName(), customCommand);
    }

    private ItemStack getNextPageItem() {
        return new ItemStackBuilder(Material.ARROW)
                .displayName("§5Próxima pagina")
                .build();
    }

    private ItemStack getPreviousPageItem() {
        return new ItemStackBuilder(Material.ARROW)
                .displayName("§5Página anterior")
                .build();
    }

    public static @NotNull Plugin getInstance() {
        return getPlugin(DungeonPlugin.class);
    }
}
