package com.dungeon.common.model.dungeon;

import com.dungeon.common.model.dungeon.barrier.Barrier;
import com.dungeon.common.model.dungeon.wave.DungeonWave;
import com.dungeon.common.model.entity.DungeonTotemEntityInstance;
import com.dungeon.common.model.entity.handler.impl.TotemDungeonEntityHandler;
import com.dungeon.common.model.view.KilledMob;
import com.dungeon.common.packet.impl.display.TextDisplayEntity;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static com.dungeon.common.keys.DungeonKeys.PACKET_EVENTS_API;
import static com.dungeon.common.keys.DungeonKeys.TOTEM_DEFAULT_LOCATION;

@RequiredArgsConstructor
@Data
public class Dungeon implements DungeonContext {

    private final String owner;
    private final DungeonTotemEntityInstance dungeonTotem;
    private final TextDisplayEntity displayEntity;
    private final BossBar bossBar;
    private DungeonWave dungeonWave;

    private final List<String> friends = new ArrayList<>();
    private final Map<Vector3d, Barrier> barriers = new HashMap<>();
    private final Map<EntityType, Integer> killedMobs = new HashMap<>();

    public Dungeon(String owner) {
        this.owner = owner;
        this.dungeonTotem = new DungeonTotemEntityInstance(
                EntityTypes.END_CRYSTAL,
                new TotemDungeonEntityHandler(),
                TOTEM_DEFAULT_LOCATION
        );
        this.dungeonWave = new DungeonWave(this, 1);
        this.bossBar = createBossBar();
        this.displayEntity = createTextDisplay();
    }

    @Contract(" -> new")
    private @NotNull BossBar createBossBar() {
        return BossBar.bossBar(
                MiniMessage.miniMessage().deserialize("<gradient:#2a57aa:#62a0fc>Wave 1</gradient>"),
                1F,
                BossBar.Color.GREEN,
                BossBar.Overlay.NOTCHED_6
        );
    }

    public void start() {
        dungeonTotem.spawn(this);
        dungeonWave.loadEntities();

        initializeDisplayEntity();
        notifyPlayers("§aDungeon started!");
    }

    @Contract(" -> new")
    private @NotNull TextDisplayEntity createTextDisplay() {
        return new TextDisplayEntity(TOTEM_DEFAULT_LOCATION.add(0.0, 5.0, 0.0));
    }

    private void initializeDisplayEntity() {
        displayEntity.spawn(this, new Vector3f(7, 7, 7), 100f, 100f);

        updateDisplay();
        showBossBarToPlayers();
    }

    public void setupNextWave() {
        final int waveId = dungeonWave.getWaveId();
        notifyPlayersWithTitle(waveId);

        dungeonWave = new DungeonWave(this, waveId + 1);
        dungeonWave.loadEntities();

        updateDisplay();
        updateBossBar();
    }

    private void notifyPlayersWithTitle(int waveId) {
        final Title title = Title.title(
                MiniMessage.miniMessage().deserialize("<gradient:#2a57aa:#62a0fc>Wave " + waveId + "</gradient>"),
                MiniMessage.miniMessage().deserialize("<color:#62a0fc>Successfully</color>")
        );
        getViewers().forEach(player -> player.showTitle(title));
    }

    @Override
    public boolean hasBarrier(Vector3d position) {
        return barriers.containsKey(position);
    }

    @Override
    public Barrier getBarrier(Vector3d position) {
        return barriers.get(position);
    }

    @Override
    public void addBarrier(Barrier barrier) {
        this.barriers.put(barrier.getLocation(), barrier);
    }

    @Override
    public void removeBarrier(@NotNull Barrier barrier) {
        this.barriers.remove(barrier.getLocation());
    }

    @Override
    public void addKilledMob(EntityType entityType) {
        killedMobs.merge(entityType, 1, Integer::sum);
    }

    @Override
    public List<KilledMob> getKilledMobs() {
        return killedMobs.entrySet().stream()
                .map(entry -> new KilledMob(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateDisplay() {
        displayEntity.setTitle(MiniMessage.miniMessage()
                .deserialize("<gradient:#2a57aa:#62a0fc>" + dungeonWave.getAliveEntitiesCount() + " Mobs vivos</gradient>"));
        displayEntity.update(this);
    }

    private void updateBossBar() {
        bossBar.name(MiniMessage.miniMessage().deserialize("<gradient:#2a57aa:#62a0fc>Wave " + dungeonWave.getWaveId() + "</gradient>"));
    }

    @Override
    public void broadcastPacket(PacketWrapper<?> packet) {
        getViewers().forEach(player -> PACKET_EVENTS_API.getPlayerManager().sendPacket(player, packet));
    }

    @Override
    public Set<Player> getViewers() {
        final List<String> playerNames = new ArrayList<>(friends);
        playerNames.add(owner);

        return playerNames.stream()
                .map(Bukkit::getPlayerExact)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private void showBossBarToPlayers() {
        getViewers().forEach(player -> player.showBossBar(bossBar));
    }

    private void notifyPlayers(String message) {
        getViewers().forEach(player -> player.sendMessage(message));
    }
}
