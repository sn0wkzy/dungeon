package com.dungeon.common.customitem;

import com.dungeon.common.util.item.impl.ItemStackBuilder;
import lombok.Getter;
import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executors;

@Getter
public abstract class CustomItem {

    private final String key;
    private final EventBus eventBus;

    public CustomItem(String key) {
        this.key = key;
        this.eventBus = EventBus.builder().throwSubscriberException(true)
                .executorService(Executors.newCachedThreadPool())
                .build();

        eventBus.register(this);
    }

    public abstract ItemStackBuilder getBuilder();

}
