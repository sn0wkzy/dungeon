package com.dungeon.spigot.command;

import com.dungeon.common.command.CustomCommand;
import com.dungeon.spigot.inventory.customitem.CustomItemInventory;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomItemCommand extends CustomCommand {

    private final ViewFrame viewFrame;

    public CustomItemCommand(ViewFrame viewFrame) {
        super("customitem", "dungeon.customitem", true);

        this.viewFrame = viewFrame;
    }

    @Override
    protected void onCommand(CommandSender commandSender, String[] arguments) {
        final Player player = (Player) commandSender;
        viewFrame.open(CustomItemInventory.class, player);
    }
}
