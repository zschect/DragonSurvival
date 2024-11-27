package by.dragonsurvivalteam.dragonsurvival.common.codecs.ability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

// No activation -> passive / Passive itself has no mana cost (the effects may apply one though)
public record Activation(Type type, Optional<LevelBasedValue> manaCost, Optional<LevelBasedValue> castTime, Optional<LevelBasedValue> cooldown) {
    public static final int INSTANT = -1;
    public static final int NO_COOLDOWN = 0;

    /*
    - some mana cost logic will need to be handled by the effects if applicable
    - the mana cost for channeled would be applied per tick? since effects may tick differently?
        - or this only has initial_mana_cost and the effects employ their mana cost (if present) while the channel applies them?
    - unsure if 'toggled' activation should exist - how should the effects be handled if an ability sets the toggle on or off?
    */

    public static final Codec<Activation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Type.CODEC.fieldOf("type").forGetter(Activation::type),
            // Either applied once with 'simple' or per tick (rate) when 'channeled'
            LevelBasedValue.CODEC.optionalFieldOf("mana_cost").forGetter(Activation::manaCost),
            // TODO :: are these in ticks? seconds?
            LevelBasedValue.CODEC.optionalFieldOf("cast_time").forGetter(Activation::castTime),
            LevelBasedValue.CODEC.optionalFieldOf("cooldown").forGetter(Activation::cooldown)
    ).apply(instance, Activation::new));

    public enum Type implements StringRepresentable {
        CHANNELED("channeled"),
        SIMPLE("simple");

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        private final String name;

        Type(final String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}