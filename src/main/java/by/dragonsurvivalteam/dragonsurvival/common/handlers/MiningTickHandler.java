package by.dragonsurvivalteam.dragonsurvival.common.handlers;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateProvider;
import by.dragonsurvivalteam.dragonsurvival.mixins.AccessorServerPlayerGameMode;
import by.dragonsurvivalteam.dragonsurvival.network.status.SyncDiggingStatus;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber
public class MiningTickHandler{
	@SubscribeEvent
	public static void updateMiningTick(PlayerTickEvent.Post playerTickEvent){
		Player player = playerTickEvent.getEntity();
		DragonStateProvider.getCap(player).ifPresent(dragonStateHandler -> {
			if(dragonStateHandler.isDragon()){
				if(player instanceof ServerPlayer){
					ServerPlayerGameMode interactionManager = ((ServerPlayer)player).gameMode;
					boolean isMining = ((AccessorServerPlayerGameMode)interactionManager).getIsDestroyingBlock() && player.swinging;

					if(isMining != dragonStateHandler.getMovementData().dig){
						dragonStateHandler.getMovementData().dig = isMining;
						PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, new SyncDiggingStatus.Data(player.getId(), isMining));
					}
				}
			}
		});
	}
}