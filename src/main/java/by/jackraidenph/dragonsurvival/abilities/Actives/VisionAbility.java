package by.jackraidenph.dragonsurvival.abilities.Actives;

import by.jackraidenph.dragonsurvival.Functions;
import by.jackraidenph.dragonsurvival.abilities.common.ActiveDragonAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class VisionAbility extends ActiveDragonAbility
{
	private Effect effect;
	public VisionAbility(Effect effect, String name, String icon, int minLevel, int maxLevel, int manaCost, int castTime, int cooldown, Integer[] requiredLevels)
	{
		super(name, icon, minLevel, maxLevel, manaCost, castTime, cooldown, requiredLevels);
		this.effect = effect;
	}
	
	@Override
	public VisionAbility createInstance()
	{
		return new VisionAbility(effect, id, icon, minLevel, maxLevel, manaCost, castTime, abilityCooldown, requiredLevels);
	}
	
	public int getDuration(){
		return 30 * getLevel();
	}
	
	@Override
	public void onActivation(PlayerEntity player)
	{
		super.onActivation(player);
		player.addEffect(new EffectInstance(effect, Functions.secondsToTicks(getDuration())));
	}
	
	@Override
	public IFormattableTextComponent getDescription()
	{
		return new TranslationTextComponent("ds.skill.description." + getId(), getDuration());
	}
}
