package by.dragonsurvivalteam.dragonsurvival.registry.dragon.ability;

import by.dragonsurvivalteam.dragonsurvival.DragonSurvival;
import by.dragonsurvivalteam.dragonsurvival.common.codecs.Condition;
import by.dragonsurvivalteam.dragonsurvival.common.codecs.LevelBasedResource;
import by.dragonsurvivalteam.dragonsurvival.common.codecs.Modifier;
import by.dragonsurvivalteam.dragonsurvival.common.codecs.ModifierWithDuration;
import by.dragonsurvivalteam.dragonsurvival.common.codecs.ability.*;
import by.dragonsurvivalteam.dragonsurvival.common.codecs.ability.animation.AnimationLayer;
import by.dragonsurvivalteam.dragonsurvival.common.codecs.ability.animation.SimpleAbilityAnimation;
import by.dragonsurvivalteam.dragonsurvival.common.particles.LargeFireParticleOption;
import by.dragonsurvivalteam.dragonsurvival.common.particles.SmallFireParticleOption;
import by.dragonsurvivalteam.dragonsurvival.registry.DSDamageTypes;
import by.dragonsurvivalteam.dragonsurvival.registry.DSEffects;
import by.dragonsurvivalteam.dragonsurvival.registry.DSSounds;
import by.dragonsurvivalteam.dragonsurvival.registry.datagen.Translation;
import by.dragonsurvivalteam.dragonsurvival.registry.dragon.ability.entity_effects.*;
import by.dragonsurvivalteam.dragonsurvival.registry.dragon.ability.targeting.AbilityTargeting;
import by.dragonsurvivalteam.dragonsurvival.registry.dragon.ability.targeting.AreaTarget;
import by.dragonsurvivalteam.dragonsurvival.registry.dragon.ability.targeting.DragonBreathTarget;
import by.dragonsurvivalteam.dragonsurvival.registry.dragon.ability.targeting.SelfTarget;
import by.dragonsurvivalteam.dragonsurvival.registry.projectile.ProjectileData;
import by.dragonsurvivalteam.dragonsurvival.registry.projectile.Projectiles;
import by.dragonsurvivalteam.dragonsurvival.util.Functions;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Optional;

public class DragonAbilities {
    @Translation(type = Translation.Type.ABILITY_DESCRIPTION, comments = "■ Ranged attack: shoots out a fireball that §cexplodes§r and sets the area on fire.")
    @Translation(type = Translation.Type.ABILITY, comments = "Fireball")
    public static final ResourceKey<DragonAbility> FIRE_BALL = key("fire_ball");

    @Translation(type = Translation.Type.ABILITY, comments = "Spike test.")
    public static final ResourceKey<DragonAbility> SPIKE_TEST = key("spike_test");

    @Translation(type = Translation.Type.ABILITY, comments = "Test ball lightning ability.")
    public static final ResourceKey<DragonAbility> BALL_LIGHTNING = key("ball_lightning_test");

    // TODO: How to actually do this in the new system?
    //  have one generic translation part which applies to all abilities (name, cooldown, duration, level)
    //  people can move them around using the string.format syntax, e.g. -> '%2$' etc. (example for using the second parameter)
    //  the ability description itself needs to be generic since there is no reasonable way to supply what each effect does etc.
    //  (at most you might be able to give the range / radius of each target)
    @Translation(type = Translation.Type.ABILITY_DESCRIPTION, comments = {
            "■ Elemental breath: a stream of fire that ignites enemies and blocks. Range depends on age of the dragon.",
            "■ Is able to destroy some blocks. Cannot be used under water, and during rain."
    })
    @Translation(type = Translation.Type.ABILITY, comments = "Nether breath")
    public static final ResourceKey<DragonAbility> NETHER_BREATH = key("nether_breath");

    @Translation(type = Translation.Type.ABILITY_DESCRIPTION, comments = "■ Personal buff: makes lava more §2transparent§r while active.")
    @Translation(type = Translation.Type.ABILITY, comments = "Lava Vision")
    public static final ResourceKey<DragonAbility> LAVA_VISION = key("lava_vision");

    @Translation(type = Translation.Type.ABILITY_DESCRIPTION, comments = "Grants additional armor points to all entities in an area around the dragon.")
    @Translation(type = Translation.Type.ABILITY, comments = "Sturdy Skin") // TODO :: strong leather, tough skin or sturdy skin?
    public static final ResourceKey<DragonAbility> TOUGH_SKIN = key("tough_skin");

    public static void registerAbilities(final BootstrapContext<DragonAbility> context) {
        context.register(FIRE_BALL, new DragonAbility(
                new Activation(
                        Activation.Type.ACTIVE_SIMPLE,
                        Optional.of(LevelBasedValue.constant(1)),
                        Optional.empty(),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(2))),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(7))),
                        Optional.of(new Activation.Sound(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.of(SoundEvents.FIRECHARGE_USE)
                        )),
                        Optional.of(new Activation.Animations(
                                Optional.of(Either.right(new SimpleAbilityAnimation("breath", AnimationLayer.BREATH, 5, false, false))),
                                Optional.empty(),
                                Optional.empty()
                        ))
                ),
                Optional.of(new Upgrade(Upgrade.Type.PASSIVE, 4, LevelBasedValue.lookup(List.of(0f, 20f, 40f, 45f), LevelBasedValue.perLevel(15)))),
                Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.location().setFluid(FluidPredicate.Builder.fluid().of(Fluids.WATER))).build()),
                List.of(new ActionContainer(
                        new SelfTarget(
                                Either.right(
                                        new AbilityTargeting.EntityTargeting(
                                                Optional.of(Condition.living()),
                                                List.of(new ProjectileEffect(
                                                        context.lookup(ProjectileData.REGISTRY).getOrThrow(Projectiles.FIREBALL),
                                                        LevelBasedValue.constant(1),
                                                        LevelBasedValue.constant(0),
                                                        LevelBasedValue.constant(1)
                                                )),
                                                false
                                        )
                                )
                        ),
                        LevelBasedValue.constant(1)
                )),
                new LevelBasedResource(List.of(
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/fireball_0.png"), 0),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/fireball_1.png"), 1),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/fireball_2.png"), 2),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/fireball_3.png"), 3),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/fireball_4.png"), 4)
                ))
        ));

        context.register(SPIKE_TEST, new DragonAbility(
                new Activation(
                        Activation.Type.ACTIVE_SIMPLE,
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(1))),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(2))),
                        Optional.empty(),
                        Optional.empty()
                ),
                Optional.empty(),
                Optional.empty(),
                List.of(new ActionContainer(
                        new SelfTarget(
                                Either.right(
                                        new AbilityTargeting.EntityTargeting(
                                                Optional.of(Condition.living()),
                                                List.of(new ProjectileEffect(
                                                        context.lookup(ProjectileData.REGISTRY).getOrThrow(Projectiles.SPIKE),
                                                        LevelBasedValue.constant(1),
                                                        LevelBasedValue.constant(0),
                                                        LevelBasedValue.constant(1)
                                                )),
                                                false
                                        )
                                )
                        ),
                        LevelBasedValue.constant(1)
                )),
                new LevelBasedResource(
                        List.of(new LevelBasedResource.TextureEntry(
                                ResourceLocation.fromNamespaceAndPath(DragonSurvival.MODID, "textures/icons/body_type_central.png"),
                                1
                        ))
                )
        ));

        context.register(BALL_LIGHTNING, new DragonAbility(
                new Activation(
                        Activation.Type.ACTIVE_SIMPLE,
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(1))),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(2))),
                        Optional.empty(),
                        Optional.empty()
                ),
                Optional.empty(),
                Optional.empty(),
                List.of(new ActionContainer(
                        new SelfTarget(
                                Either.right(
                                        new AbilityTargeting.EntityTargeting(
                                                Optional.of(Condition.living()),
                                                List.of(new ProjectileEffect(
                                                        context.lookup(ProjectileData.REGISTRY).getOrThrow(Projectiles.BALL_LIGHTNING),
                                                        LevelBasedValue.constant(1),
                                                        LevelBasedValue.constant(0),
                                                        LevelBasedValue.constant(1)
                                                )),
                                                false
                                        )
                                )
                        ),
                        LevelBasedValue.constant(1)
                )),
                new LevelBasedResource(
                        List.of(new LevelBasedResource.TextureEntry(
                                ResourceLocation.fromNamespaceAndPath(DragonSurvival.MODID, "textures/icons/body_type_central.png"),
                                1
                        ))
                )
        ));

        context.register(NETHER_BREATH, new DragonAbility(
                new Activation(
                        Activation.Type.ACTIVE_CHANNELED,
                        Optional.empty(),
                        Optional.of(ManaCost.ticking(LevelBasedValue.constant(0.025f))),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(1))),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(2))),
                        Optional.of(new Activation.Sound(
                                Optional.of(DSSounds.FIRE_BREATH_START.get()),
                                Optional.empty(),
                                Optional.of(DSSounds.FIRE_BREATH_LOOP.get()),
                                Optional.of(DSSounds.FIRE_BREATH_END.get())
                        )),
                        Optional.of(new Activation.Animations(
                                Optional.empty(),
                                Optional.of(new SimpleAbilityAnimation("breath", AnimationLayer.BREATH, 5, false, false)),
                                Optional.empty()
                        ))
                ),
                Optional.of(new Upgrade(Upgrade.Type.PASSIVE, 4, LevelBasedValue.lookup(List.of(0f, 10f, 30f, 50f), LevelBasedValue.perLevel(15)))),
                Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.location().setFluid(FluidPredicate.Builder.fluid().of(Fluids.WATER))).build()),
                List.of(new ActionContainer(new DragonBreathTarget(Either.right(
                                new AbilityTargeting.EntityTargeting(
                                        Optional.of(Condition.living()),
                                        List.of(
                                                new DamageEffect(
                                                        context.lookup(Registries.DAMAGE_TYPE).getOrThrow(DSDamageTypes.CAVE_DRAGON_BREATH),
                                                        LevelBasedValue.perLevel(3)
                                                ),
                                                new FireEffect(
                                                        LevelBasedValue.perLevel(Functions.secondsToTicks(5))
                                                ),
                                                new PotionEffect(
                                                        HolderSet.direct(DSEffects.BURN),
                                                        LevelBasedValue.constant(0),
                                                        LevelBasedValue.constant(Functions.secondsToTicks(10)),
                                                        LevelBasedValue.constant(1)
                                                )
                                        ),
                                        false
                                )
                        ), LevelBasedValue.constant(1)), LevelBasedValue.constant(10)),
                        new ActionContainer(new DragonBreathTarget(Either.left(
                                new AbilityTargeting.BlockTargeting(
                                                Optional.empty(),
                                                List.of(new by.dragonsurvivalteam.dragonsurvival.registry.dragon.ability.block_effects.FireEffect(
                                                        LevelBasedValue.constant(0.05f)
                                                )))
                        ), LevelBasedValue.constant(1)), LevelBasedValue.constant(1)),
                        new ActionContainer(new SelfTarget(Either.right(
                                new AbilityTargeting.EntityTargeting(
                                        Optional.empty(),
                                        List.of(new BreathParticlesEffect(
                                                0.2f,
                                                0.02f,
                                                new SmallFireParticleOption(37, true),
                                                new LargeFireParticleOption(37, false)
                                        )),
                                        true
                                )
                        )), LevelBasedValue.constant(1))),
                new LevelBasedResource(List.of(
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/nether_breath_0.png"), 0),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/nether_breath_1.png"), 1),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/nether_breath_2.png"), 2),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/nether_breath_3.png"), 3),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/nether_breath_4.png"), 4)
                ))
        ));

        context.register(LAVA_VISION, new DragonAbility(
                new Activation(
                        Activation.Type.ACTIVE_SIMPLE,
                        Optional.of(LevelBasedValue.constant(1)),
                        Optional.empty(),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(1))),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(30))),
                        Optional.of(new Activation.Sound(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.of(SoundEvents.UI_TOAST_IN)
                        )),
                        Optional.of(new Activation.Animations(
                                Optional.of(Either.right(new SimpleAbilityAnimation("cast_self_buff", AnimationLayer.BASE, 2, true, false))),
                                Optional.empty(),
                                Optional.of(new SimpleAbilityAnimation("self_buff", AnimationLayer.BASE, 0, true, false))
                        ))
                ),
                Optional.of(new Upgrade(Upgrade.Type.PASSIVE, 4, LevelBasedValue.lookup(List.of(0f, 25f, 45f, 60f), LevelBasedValue.perLevel(15)))),
                Optional.empty(),
                List.of(new ActionContainer(new SelfTarget(Either.right(
                                new AbilityTargeting.EntityTargeting(
                                        Optional.empty(),
                                        List.of(new PotionEffect(
                                                HolderSet.direct(DSEffects.LAVA_VISION),
                                                LevelBasedValue.constant(0),
                                                LevelBasedValue.perLevel(Functions.secondsToTicks(30)),
                                                LevelBasedValue.constant(1)
                                        )),
                                        true
                                )
                        )), LevelBasedValue.constant(1))),
                new LevelBasedResource(List.of(
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/lava_vision_0.png"), 0),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/lava_vision_1.png"), 1),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/lava_vision_2.png"), 2),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/lava_vision_3.png"), 3),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/lava_vision_4.png"), 4)
                ))
        ));

        context.register(TOUGH_SKIN, new DragonAbility(
                new Activation(
                        Activation.Type.ACTIVE_SIMPLE,
                        Optional.of(LevelBasedValue.constant(1)),
                        Optional.empty(),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(1))),
                        Optional.of(LevelBasedValue.constant(Functions.secondsToTicks(30))),
                        Optional.of(new Activation.Sound(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                Optional.of(SoundEvents.UI_TOAST_IN)
                        )),
                        Optional.of(new Activation.Animations(
                                Optional.of(Either.right(new SimpleAbilityAnimation("cast_mass_buff", AnimationLayer.BASE, 2, true, true))),
                                Optional.empty(),
                                Optional.of(new SimpleAbilityAnimation("mass_buff", AnimationLayer.BASE, 0, true, true))
                        ))
                ),
                Optional.of(new Upgrade(Upgrade.Type.PASSIVE, 3, LevelBasedValue.lookup(List.of(0f, 15f, 35f), LevelBasedValue.perLevel(15)))),
                Optional.empty(),
                List.of(new ActionContainer(new AreaTarget(Either.right(
                        new AbilityTargeting.EntityTargeting(
                                Optional.empty(),
                                List.of(new ModifierEffect(
                                        List.of(
                                                new ModifierWithDuration(
                                                        ResourceLocation.fromNamespaceAndPath(DragonSurvival.MODID, "tough_leather"),
                                                        ResourceLocation.fromNamespaceAndPath(DragonSurvival.MODID, "textures/modifiers/strong_leather.png"),
                                                        List.of(
                                                                new Modifier(
                                                                        Attributes.ARMOR,
                                                                        LevelBasedValue.constant(3),
                                                                        AttributeModifier.Operation.ADD_VALUE,
                                                                        Optional.empty()
                                                                )
                                                        ),
                                                        LevelBasedValue.perLevel(Functions.secondsToTicks(60))
                                                )
                                        )
                                )),
                                true
                        )),
                        LevelBasedValue.constant(5)
                ), LevelBasedValue.constant(1))),
                new LevelBasedResource(List.of(
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/strong_leather_0.png"), 0),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/strong_leather_1.png"), 1),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/strong_leather_2.png"), 2),
                        new LevelBasedResource.TextureEntry(DragonSurvival.res("textures/skills/cave/strong_leather_3.png"), 3)
                ))
        ));
    }

    public static ResourceKey<DragonAbility> key(final ResourceLocation location) {
        return ResourceKey.create(DragonAbility.REGISTRY, location);
    }

    private static ResourceKey<DragonAbility> key(final String path) {
        return key(DragonSurvival.res(path));
    }
}
