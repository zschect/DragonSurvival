package by.dragonsurvivalteam.dragonsurvival.common.codecs.ability;

import by.dragonsurvivalteam.dragonsurvival.registry.dragon.ability.DragonAbilityInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import org.jetbrains.annotations.NotNull;

public record Upgrade(Type type, int maximumLevel, LevelBasedValue experienceCost) {
    public static final Codec<Upgrade> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Type.CODEC.fieldOf("type").forGetter(Upgrade::type),
        ExtraCodecs.intRange(DragonAbilityInstance.MIN_LEVEL, DragonAbilityInstance.MAX_LEVEL).fieldOf("maximum_level").forGetter(Upgrade::maximumLevel),
        /* How it may work:
        - MANUAL:
            - These will display the plus/minus buttons to level them up
        - PASSIVE:
            - When retrieving the current level compare player xp with the cost
        */
        LevelBasedValue.CODEC.fieldOf("experience_cost").forGetter(Upgrade::experienceCost)
    ).apply(instance, Upgrade::new));

    public enum Type implements StringRepresentable {
        MANUAL("manual"),
        PASSIVE("passive");

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

    public void updateUpgradeState(Player player, DragonAbilityInstance ability) {
        if(type() == Type.PASSIVE) {
            float cost = experienceCost().calculate(ability.level());
            float availableExperience = player.totalExperience;
            while(availableExperience < cost) {
                if(ability.level() == 0) {
                    break;
                }

                ability.setLevel(ability.level() - 1);
                cost = experienceCost().calculate(ability.level());
            }
            while(availableExperience >= cost) {
                if(ability.level() == maximumLevel()) {
                    break;
                }

                ability.setLevel(ability.level() + 1);
                cost = experienceCost().calculate(ability.level());
            }
        }
    }

    public void tryToUpgrade(Player player, DragonAbilityInstance ability) {
        if(type() == Type.MANUAL) {
            float cost = experienceCost().calculate(ability.level() + 1);
            float availableExperience = player.totalExperience;
            if (availableExperience >= cost && ability.level() < maximumLevel()){
                player.giveExperiencePoints((int) -cost);
                ability.setLevel(ability.level() + 1);
            }
        }
    }
}
