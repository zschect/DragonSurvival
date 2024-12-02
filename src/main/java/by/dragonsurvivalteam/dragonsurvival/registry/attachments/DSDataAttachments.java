package by.dragonsurvivalteam.dragonsurvival.registry.attachments;

import by.dragonsurvivalteam.dragonsurvival.DragonSurvival;
import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.common.capability.EntityStateHandler;
import by.dragonsurvivalteam.dragonsurvival.server.handlers.LightningHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DSDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> DS_ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, DragonSurvival.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<EntityStateHandler>> ENTITY_HANDLER = DS_ATTACHMENT_TYPES.register("entity_handler", () -> AttachmentType.serializable(EntityStateHandler::new).build());
    // TODO :: does this need a custom copy handle for entering the end portal?
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<DragonStateHandler>> DRAGON_HANDLER = DS_ATTACHMENT_TYPES.register("dragon_handler", () -> AttachmentType.serializable(DragonStateHandler::new).copyOnDeath().build());
    // TODO :: copy on death?
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<LightningHandler>> LIGHTNING_BOLT = DS_ATTACHMENT_TYPES.register("lightning_bolt_data", () -> AttachmentType.serializable(LightningHandler::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MovementData>> MOVEMENT = DS_ATTACHMENT_TYPES.register("movement_data", () -> AttachmentType.builder(MovementData::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SpinData>> SPIN = DS_ATTACHMENT_TYPES.register("spin_data", () -> AttachmentType.serializable(SpinData::new).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ClawInventoryData>> CLAW_INVENTORY = DS_ATTACHMENT_TYPES.register("claw_inventory_data", () -> AttachmentType.serializable(ClawInventoryData::new).copyOnDeath().build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<TreasureRestData>> TREASURE_REST = DS_ATTACHMENT_TYPES.register("treasure_rest_data", () -> AttachmentType.serializable(TreasureRestData::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AltarData>> ALTAR = DS_ATTACHMENT_TYPES.register("altar_data", () -> AttachmentType.serializable(AltarData::new).copyOnDeath().build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PenaltySupply>> PENALTY_SUPPLY = DS_ATTACHMENT_TYPES.register("penalty_supply", () -> AttachmentType.serializable(PenaltySupply::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ModifiersWithDuration>> MODIFIERS_WITH_DURATION = DS_ATTACHMENT_TYPES.register("modifiers_with_duration", () -> AttachmentType.serializable(ModifiersWithDuration::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<DamageModifications>> DAMAGE_MODIFICATIONS = DS_ATTACHMENT_TYPES.register("damage_modifications", () -> AttachmentType.serializable(DamageModifications::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HarvestBonuses>> HARVEST_BONUSES = DS_ATTACHMENT_TYPES.register("harvest_bonuses", () -> AttachmentType.serializable(HarvestBonuses::new).build());
}
