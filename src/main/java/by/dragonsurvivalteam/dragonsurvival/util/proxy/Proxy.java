package by.dragonsurvivalteam.dragonsurvival.util.proxy;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface Proxy {
    default @Nullable Player getLocalPlayer() {
        return null;
    }

    default @Nullable Level getLocalLevel() {
        return null;
    }

    default void playSoundAtEyeLevel(final Player player, final SoundEvent event) { /* Nothing to do */ }

    default void queueTickingSound(final ResourceLocation id, final SoundEvent soundEvent, final SoundSource soundSource, final Entity entity) { /* Nothing to do */ }

    default void stopTickingSound(final ResourceLocation id) { /* Nothing to do */ }
}
