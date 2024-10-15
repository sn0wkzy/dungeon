package com.dungeon.common.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class EventWrapper extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public EventWrapper(boolean isAsync) {
        super(isAsync);
    }

    public EventWrapper() {
        super(false);
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public boolean call() {
        Bukkit.getPluginManager().callEvent(this);
        if (this instanceof Cancellable) return !((Cancellable) this).isCancelled();

        return true;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
