package by.dragonsurvivalteam.dragonsurvival.common.items.food;

import by.dragonsurvivalteam.dragonsurvival.common.handlers.DragonFoodHandler;
import by.dragonsurvivalteam.dragonsurvival.registry.DSEffects;
import by.dragonsurvivalteam.dragonsurvival.util.Functions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChargedSoupItem extends Item {
    public ChargedSoupItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(1)
                .saturationModifier(0.4f)
                .alwaysEdible()
                .effect(() -> new MobEffectInstance(MobEffects.POISON, Functions.secondsToTicks(15), 0), 1)
                .effect(() -> new MobEffectInstance(DSEffects.FIRE, Functions.secondsToTicks(DragonFoodHandler.chargedSoupBuffDuration), 0), 1)
                .build())
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, Item.@NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("ds.description.chargedSoup")); // TODO
    }
}