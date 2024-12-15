package by.dragonsurvivalteam.dragonsurvival.common.handlers;

import by.dragonsurvivalteam.dragonsurvival.registry.dragon.DragonType;
import by.dragonsurvivalteam.dragonsurvival.registry.dragon.ability.DragonAbility;
import by.dragonsurvivalteam.dragonsurvival.registry.dragon.stage.DragonStage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@EventBusSubscriber
public class DataReloadHandler {
    public static long lastReload;

    @SubscribeEvent
    public static void handleDatapackReload(final TagsUpdatedEvent event) {
        lastReload = System.currentTimeMillis();
        DragonStage.update(event.getRegistryAccess());
        DragonType.validate(event.getRegistryAccess());
        DragonAbility.validate(event.getRegistryAccess());
    }
}
