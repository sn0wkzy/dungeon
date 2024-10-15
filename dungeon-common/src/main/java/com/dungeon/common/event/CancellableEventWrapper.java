package com.dungeon.common.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

@Getter
public abstract class CancellableEventWrapper extends EventWrapper implements Cancellable {

    @Setter
    private boolean cancelled;

    public CancellableEventWrapper(boolean isAsync) {
        super(isAsync);
    }

    public CancellableEventWrapper() {
        super(false);
    }
}
