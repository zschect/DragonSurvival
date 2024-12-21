package by.dragonsurvivalteam.dragonsurvival.common.effects;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateProvider;
import by.dragonsurvivalteam.dragonsurvival.common.capability.EntityStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.EffectHandler;
import by.dragonsurvivalteam.dragonsurvival.common.particles.SmallFireParticleOption;
import by.dragonsurvivalteam.dragonsurvival.registry.DSDamageTypes;
import by.dragonsurvivalteam.dragonsurvival.registry.attachments.DSDataAttachments;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForgeMod;

public class BurnEffect extends ModifiableMobEffect {
    private int duration;

    public BurnEffect(MobEffectCategory type, int color, boolean incurable) {
        super(type, color, incurable);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        this.duration = duration;
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.isEyeInFluidType(NeoForgeMod.WATER_TYPE.value()) || livingEntity.isInWaterRainOrBubble()) {
            return false;
        }

        if(duration % 20 == 0) {
            EntityStateHandler data = livingEntity.getData(DSDataAttachments.ENTITY_HANDLER);
            if (!livingEntity.fireImmune()) {
                if(!DragonStateProvider.isDragon(livingEntity)) {
                    ParticleOptions particle = new SmallFireParticleOption(37F, false);
                    for (int i = 0; i < 4; i++) {
                        EffectHandler.renderEffectParticle(livingEntity, particle);
                    }
                }

                if (data.lastPos != null) {
                    double distance = livingEntity.distanceToSqr(data.lastPos);
                    float damage = (amplifier + 1) * Mth.clamp((float) distance, 0, 10);

                    if (damage > 0) {
                        if (!livingEntity.isOnFire()) {
                            // Short enough fire duration to not cause fire damage but still drop cooked items
                            livingEntity.setRemainingFireTicks(1);
                        }

                        Player player = livingEntity.level().getEntity(data.lastAfflicted) instanceof Player ? (Player) livingEntity.level().getEntity(data.lastAfflicted) : null;
                        livingEntity.hurt(new DamageSource(DSDamageTypes.get(livingEntity.level(), DSDamageTypes.CAVE_DRAGON_BURN), player), damage);
                    }
                }
            }
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }
}
