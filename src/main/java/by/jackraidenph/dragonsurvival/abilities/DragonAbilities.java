package by.jackraidenph.dragonsurvival.abilities;

import by.jackraidenph.dragonsurvival.Functions;
import by.jackraidenph.dragonsurvival.abilities.Actives.AoeBuffAbility;
import by.jackraidenph.dragonsurvival.abilities.Actives.VisionAbility;
import by.jackraidenph.dragonsurvival.abilities.InformationSkills.DragonClawsAbility;
import by.jackraidenph.dragonsurvival.abilities.InformationSkills.DragonWingAbility;
import by.jackraidenph.dragonsurvival.abilities.Passives.AthleticsAbility;
import by.jackraidenph.dragonsurvival.abilities.Passives.MagicAbility;
import by.jackraidenph.dragonsurvival.abilities.Passives.WaterAbility;
import by.jackraidenph.dragonsurvival.abilities.common.ActiveDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.DragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.InformationDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.PassiveDragonAbility;
import by.jackraidenph.dragonsurvival.registration.DragonEffects;
import by.jackraidenph.dragonsurvival.util.DragonType;
import net.minecraft.potion.Effects;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DragonAbilities
{
	//Forest dragon
	public static ActiveDragonAbility POISONOUS_BREATH;
	public static ActiveDragonAbility SPIKE;
	public static ActiveDragonAbility INSPIRATION;
	public static ActiveDragonAbility HUNTER;
	
	public static PassiveDragonAbility FOREST_MAGIC;
	public static PassiveDragonAbility FOREST_ATHLETICS;
	public static PassiveDragonAbility LIGHT_IN_DARKNESS;
	public static PassiveDragonAbility CLIFFHANGER;
	
	public static InformationDragonAbility FOREST_CLAWS_AND_TEETH;
	public static InformationDragonAbility FOREST_WINGS;
	public static InformationDragonAbility FOREST_DRAGON;
	public static InformationDragonAbility FEAR_OF_DARK;
	
	//Sea dragon
	public static ActiveDragonAbility STORM_BREATH;
	public static ActiveDragonAbility BALL_LIGHTNING;
	public static ActiveDragonAbility REVEALING_THE_SOUL;
	public static ActiveDragonAbility SEA_EYES;
	
	public static PassiveDragonAbility SEA_MAGIC;
	public static PassiveDragonAbility SEA_ATHLETICS;
	public static PassiveDragonAbility WATER;
	public static PassiveDragonAbility SPECTRAL_IMPACT;
	
	public static InformationDragonAbility SEA_CLAWS_AND_TEETH;
	public static InformationDragonAbility SEA_WINGS;
	public static InformationDragonAbility SEA_DRAGON;
	public static InformationDragonAbility AMPHIBIAN;
	
	//Cave dragon
	public static ActiveDragonAbility NETHER_BREATH;
	public static ActiveDragonAbility FIREBALL;
	public static ActiveDragonAbility STRONG_LEATHER;
	public static ActiveDragonAbility LAVA_VISION;
	
	public static PassiveDragonAbility CAVE_MAGIC;
	public static PassiveDragonAbility CAVE_ATHLETICS;
	public static PassiveDragonAbility CONTRAST_SHOWER;
	public static PassiveDragonAbility BURN;
	
	public static InformationDragonAbility CAVE_CLAWS_AND_TEETH;
	public static InformationDragonAbility CAVE_WINGS;
	public static InformationDragonAbility CAVE_DRAGON;
	public static InformationDragonAbility HOT_BLOOD;
	
	public static void initAbilities(){
		//Forest dragon
		POISONOUS_BREATH = register(DragonType.FOREST, new ActiveDragonAbility("poisonous_breath", "forest/poisonous_breath", 1, 4, 2, 0, 0, new Integer[]{0, 10, 30, 50}));
		SPIKE = register(DragonType.FOREST, new ActiveDragonAbility("spike", "forest/spike", 0, 4, 1, 0, 0, new Integer[]{0, 20, 30, 40}));
		INSPIRATION = register(DragonType.FOREST, new AoeBuffAbility(Effects.NIGHT_VISION, Color.GREEN, "inspiration", "forest/inspiration", 0, 5, 5, Functions.secondsToTicks(5), Functions.secondsToTicks(30), new Integer[]{0, 15, 35}));
		HUNTER = register(DragonType.FOREST, new ActiveDragonAbility("hunter", "forest/hunter", 0, 2, 3, 0, 0, new Integer[]{0, 25}));
		
		FOREST_MAGIC = register(DragonType.FOREST, new MagicAbility("forest_magic", "forest/forest_magic", 0, 10));
		FOREST_ATHLETICS = register(DragonType.FOREST, new AthleticsAbility("forest_athletics", "forest/forest_athletics", 0, 5));
		LIGHT_IN_DARKNESS = register(DragonType.FOREST, new PassiveDragonAbility("light_in_darkness", "forest/light_in_darkness", 0, 6));
		CLIFFHANGER = register(DragonType.FOREST, new PassiveDragonAbility("cliffhanger", "forest/cliffhanger", 0, 5));
		
		FOREST_CLAWS_AND_TEETH = register(DragonType.FOREST, new DragonClawsAbility("forest_claws_and_teeth", "forest/forest_claws_and_teeth", 1, 5));
		FOREST_WINGS = register(DragonType.FOREST, new DragonWingAbility("forest_wings", "forest/forest_wings", 1, 1));
		FOREST_DRAGON = register(DragonType.FOREST, new InformationDragonAbility("forest_dragon", "forest/forest_dragon", 1, 1));
		FEAR_OF_DARK = register(DragonType.FOREST, new InformationDragonAbility("fear_of_dark", "forest/fear_of_dark", 1, 1));
		
		//Sea dragon
		STORM_BREATH = register(DragonType.SEA, new ActiveDragonAbility("storm_breath", "sea/storm_breath", 1, 4, 0, 0, 0, new Integer[]{0, 10, 30, 50}));
		BALL_LIGHTNING = register(DragonType.SEA, new ActiveDragonAbility("ball_lightning", "sea/ball_lightning", 0, 4, 0,0, 0,  new Integer[]{0, 20, 45}));
		REVEALING_THE_SOUL = register(DragonType.SEA, new AoeBuffAbility(Effects.NIGHT_VISION, Color.BLUE, "revealing_the_soul", "sea/revealing_the_soul", 0, 3, 5, Functions.secondsToTicks(5), Functions.secondsToTicks(30),  new Integer[]{0, 25, 40}));
		SEA_EYES = register(DragonType.SEA, new VisionAbility(DragonEffects.WATER_VISION, "sea_eyes", "sea/sea_eyes", 0, 2, 0, 0, Functions.secondsToTicks(30), new Integer[]{0, 15}));
		
		SEA_MAGIC = register(DragonType.SEA, new MagicAbility("sea_magic", "sea/sea_magic", 0, 10));
		SEA_ATHLETICS = register(DragonType.SEA, new AthleticsAbility("sea_athletics", "sea/sea_athletics", 0, 5));
		WATER = register(DragonType.SEA, new WaterAbility("water", "sea/water", 0, 5));
		SPECTRAL_IMPACT = register(DragonType.SEA, new PassiveDragonAbility("spectral_impact", "sea/spectral_impact", 0, 3));
		
		SEA_CLAWS_AND_TEETH = register(DragonType.SEA, new DragonClawsAbility("sea_claws_and_teeth", "sea/sea_claws_and_teeth", 1, 5));
		SEA_WINGS = register(DragonType.SEA, new DragonWingAbility("sea_wings", "sea/sea_wings", 1, 1));
		SEA_DRAGON = register(DragonType.SEA, new InformationDragonAbility("sea_dragon", "sea/sea_dragon", 1, 1));
		AMPHIBIAN = register(DragonType.SEA, new InformationDragonAbility("amphibian", "sea/amphibian", 1, 1));
		
		//Cave dragon
		NETHER_BREATH = register(DragonType.CAVE, new ActiveDragonAbility("nether_breath", "cave/nether_breath", 1, 4, 0,0, 0,  new Integer[]{0, 10, 30, 50}));
		FIREBALL = register(DragonType.CAVE, new ActiveDragonAbility("fireball", "cave/fireball", 0, 4, 0,0, 0,  new Integer[]{0, 20, 40, 45}));
		STRONG_LEATHER = register(DragonType.CAVE, new AoeBuffAbility(Effects.NIGHT_VISION, Color.RED, "strong_leather", "cave/strong_leather", 0, 3, 5, Functions.secondsToTicks(5), Functions.secondsToTicks(30),  new Integer[]{0, 15, 35}));
		LAVA_VISION = register(DragonType.CAVE, new VisionAbility(DragonEffects.LAVA_VISION, "lava_vision", "cave/lava_vision", 0, 2, 0, 0, Functions.secondsToTicks(30),  new Integer[]{0, 25}));
		
		CAVE_MAGIC = register(DragonType.CAVE, new MagicAbility("cave_magic", "cave/cave_magic", 0, 10));
		CAVE_ATHLETICS = register(DragonType.CAVE, new AthleticsAbility("cave_athletics", "cave/cave_athletics", 0, 5));
		CONTRAST_SHOWER = register(DragonType.CAVE, new PassiveDragonAbility("contrast_shower", "cave/contrast_shower", 0, 5));
		BURN = register(DragonType.CAVE, new PassiveDragonAbility("burn", "cave/burn", 0, 3));
		
		CAVE_CLAWS_AND_TEETH = register(DragonType.CAVE, new DragonClawsAbility("cave_claws_and_teeth", "cave/cave_claws_and_teeth", 1, 5));
		CAVE_WINGS = register(DragonType.CAVE, new DragonWingAbility("cave_wings", "cave/cave_wings", 1, 1));
		CAVE_DRAGON = register(DragonType.CAVE, new InformationDragonAbility("cave_dragon", "cave/cave_dragon", 1, 1));
		HOT_BLOOD = register(DragonType.CAVE, new InformationDragonAbility("hot_bloode", "cave/hot_bloode", 1, 1));
	}
	
	
	public static HashMap<DragonType, ArrayList<DragonAbility>> ABILITIES = new HashMap<>();
	public static HashMap<DragonType, ArrayList<ActiveDragonAbility>> ACTIVE_ABILITIES = new HashMap<>();
	public static HashMap<DragonType, ArrayList<PassiveDragonAbility>> PASSIVE_ABILITIES = new HashMap<>();
	public static HashMap<DragonType, ArrayList<InformationDragonAbility>> INFORMATION_ABILITIES = new HashMap<>();
	
	public static HashMap<String, DragonAbility> ABILITY_LOOKUP = new HashMap<>();
	
	public static ActiveDragonAbility register(DragonType type, ActiveDragonAbility activeDragonAbility){
		if(!ABILITIES.containsKey(type)){
			ABILITIES.put(type, new ArrayList<>());
		}
		
		if(!ACTIVE_ABILITIES.containsKey(type)){
			ACTIVE_ABILITIES.put(type, new ArrayList<>());
		}
		
		ABILITIES.get(type).add(activeDragonAbility);
		ACTIVE_ABILITIES.get(type).add(activeDragonAbility);
		
		ABILITY_LOOKUP.put(activeDragonAbility.getId(), activeDragonAbility);
		
		return activeDragonAbility;
	}
	
	public static PassiveDragonAbility register(DragonType type, PassiveDragonAbility passiveDragonAbility){
		if(!ABILITIES.containsKey(type)){
			ABILITIES.put(type, new ArrayList<>());
		}
		
		if(!PASSIVE_ABILITIES.containsKey(type)){
			PASSIVE_ABILITIES.put(type, new ArrayList<>());
		}
		
		ABILITIES.get(type).add(passiveDragonAbility);
		PASSIVE_ABILITIES.get(type).add(passiveDragonAbility);
		ABILITY_LOOKUP.put(passiveDragonAbility.getId(), passiveDragonAbility);
		
		return passiveDragonAbility;
	}
	
	public static InformationDragonAbility register(DragonType type, InformationDragonAbility informationDragonAbility){
		if(!ABILITIES.containsKey(type)){
			ABILITIES.put(type, new ArrayList<>());
		}
		
		if(!INFORMATION_ABILITIES.containsKey(type)){
			INFORMATION_ABILITIES.put(type, new ArrayList<>());
		}
		
		ABILITIES.get(type).add(informationDragonAbility);
		INFORMATION_ABILITIES.get(type).add(informationDragonAbility);
		ABILITY_LOOKUP.put(informationDragonAbility.getId(), informationDragonAbility);
		
		return informationDragonAbility;
	}
}
