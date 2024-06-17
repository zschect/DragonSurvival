package by.dragonsurvivalteam.dragonsurvival;

import static by.dragonsurvivalteam.dragonsurvival.registry.DSBlocks.DS_BLOCKS;
import static by.dragonsurvivalteam.dragonsurvival.registry.DSContainers.DS_CONTAINERS;
import static by.dragonsurvivalteam.dragonsurvival.registry.DSCreativeTabs.DS_CREATIVE_MODE_TABS;
import static by.dragonsurvivalteam.dragonsurvival.registry.DSEffects.DS_MOB_EFFECTS;
import static by.dragonsurvivalteam.dragonsurvival.registry.DSEntities.DS_ENTITY_TYPES;
import static by.dragonsurvivalteam.dragonsurvival.registry.DSItems.DS_ITEMS;
import static by.dragonsurvivalteam.dragonsurvival.registry.DSParticles.DS_PARTICLES;
import static by.dragonsurvivalteam.dragonsurvival.registry.DSPotions.DS_POTIONS;
import static by.dragonsurvivalteam.dragonsurvival.registry.DSSounds.DS_SOUNDS;
import static by.dragonsurvivalteam.dragonsurvival.registry.DSTileEntities.DS_TILE_ENTITIES;

//import by.dragonsurvivalteam.dragonsurvival.api.appleskin.AppleSkinEventHandler;
import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.capability.EntityStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonBodies;
import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.DragonTypes;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.WingObtainmentController;
import by.dragonsurvivalteam.dragonsurvival.config.ConfigHandler;
import by.dragonsurvivalteam.dragonsurvival.magic.DragonAbilities;
import com.mojang.serialization.MapCodec;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLibClient;

@Mod( DragonSurvivalMod.MODID )
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DragonSurvivalMod{
	public static final String MODID = "dragonsurvival";
	public static final Logger LOGGER = LogManager.getLogger("Dragon Survival");
	public static final DeferredRegister<AttachmentType<?>> DS_ATTACHMENT_TYPES = DeferredRegister.create(
			NeoForgeRegistries.Keys.ATTACHMENT_TYPES,
			MODID);
	public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);
	public static final Supplier<AttachmentType<EntityStateHandler>> ENTITY_HANDLER = DS_ATTACHMENT_TYPES.register(
			"entity_handler",
			() -> AttachmentType.serializable(EntityStateHandler::new).copyOnDeath().build()
	);
	public static final Supplier<AttachmentType<DragonStateHandler>> DRAGON_HANDLER = DS_ATTACHMENT_TYPES.register(
			"dragon_handler",
			() -> AttachmentType.serializable(DragonStateHandler::new).copyOnDeath().build()
	);

	public DragonSurvivalMod(IEventBus modEventBus, ModContainer modContainer){
		if(FMLEnvironment.dist  == Dist.CLIENT){
			GeckoLibClient.init();
		}
		DragonTypes.registerTypes();
		DragonBodies.registerBodies();

		ConfigHandler.initConfig();
		DragonAbilities.initAbilities();

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::clientSetup);

		// We need to register blocks before items, since otherwise the items will register before the item-blocks can be assigned
		DS_BLOCKS.register(modEventBus);
		DS_ITEMS.register(modEventBus);
		DS_ATTACHMENT_TYPES.register(modEventBus);
		DS_MOB_EFFECTS.register(modEventBus);
		DS_CONTAINERS.register(modEventBus);
		DS_CREATIVE_MODE_TABS.register(modEventBus);
		DS_PARTICLES.register(modEventBus);
		DS_SOUNDS.register(modEventBus);
		DS_POTIONS.register(modEventBus);
		DS_TILE_ENTITIES.register(modEventBus);
		DS_ENTITY_TYPES.register(modEventBus);
		GLM.register(modEventBus);
	}

	private void commonSetup(final FMLCommonSetupEvent event){
		WingObtainmentController.loadDragonPhrases();
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		// FIXME: When AppleSkin updates
		/*if (ModList.get().isLoaded("appleskin")) {
			NeoForge.EVENT_BUS.register(new AppleSkinEventHandler());
		}*/
	}
	
	@SubscribeEvent
	public static void addPackFinders(AddPackFindersEvent event) {
		if (event.getPackType() == PackType.CLIENT_RESOURCES) {
			HashMap<MutableComponent, String> resourcePacks = new HashMap<>();
			//resourcePacks.put(Component.literal("- Dragon East"), "resourcepacks/ds_east");
			//resourcePacks.put(Component.literal("- Dragon North"), "resourcepacks/ds_north");
			//resourcePacks.put(Component.literal("- Dragon South"), "resourcepacks/ds_south");
			//resourcePacks.put(Component.literal("- Dragon West"), "resourcepacks/ds_west");
			resourcePacks.put(Component.literal("- Old Magic Icons for DS"), "resourcepacks/ds_old_magic");
			resourcePacks.put(Component.literal("- Dark GUI for DS"), "resourcepacks/ds_dark_gui");
			for (Map.Entry<MutableComponent, String> entry : resourcePacks.entrySet()) {
				event.addPackFinders(ResourceLocation.fromNamespaceAndPath(MODID, entry.getValue()), PackType.CLIENT_RESOURCES, entry.getKey(), PackSource.BUILT_IN, false, Pack.Position.TOP);
			}
		}
	}
}