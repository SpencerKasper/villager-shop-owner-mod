package net.fabricmc.example;

import com.google.common.collect.*;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.passive.*;
import net.minecraft.server.world.*;
import net.minecraft.util.dynamic.*;
import net.minecraft.world.poi.*;

public class FindShopTask extends Task<VillagerEntity> {
    public FindShopTask() {
        super(ImmutableMap.of(VillagerShopOwnerMod.USER_SHOP, MemoryModuleState.VALUE_ABSENT));
    }

    protected boolean shouldRun(ServerWorld world, VillagerEntity entity) {
        return world.getPointOfInterestStorage()
                .getNearestPosition(
                        VillagerShopOwnerMod.DIAMOND_BLOCK_POI.getCompletionCondition(),
                        entity.getBlockPos(),
                        48,
                        PointOfInterestStorage.OccupationStatus.ANY
                ).isPresent();
    }

    protected void run(ServerWorld world, VillagerEntity entity, long time) {
        VillagerShopOwnerMod.LOGGER.info("FindTemple:run!");

        world.getPointOfInterestStorage()
                .getPositions(
                        VillagerShopOwnerMod.DIAMOND_BLOCK_POI.getCompletionCondition(),
                        (blockPos) -> {
                            Path path = entity
                                    .getNavigation()
                                    .findPathTo(blockPos, VillagerShopOwnerMod.DIAMOND_BLOCK_POI.getSearchDistance());
                            return (path != null && path.reachesTarget());
                        },
                        entity.getBlockPos(),
                        48,
                        PointOfInterestStorage.OccupationStatus.ANY
                )
                .findAny()
                .ifPresent(blockPos -> {
                    GlobalPos globalPos = GlobalPos.create(world.getRegistryKey(), blockPos);
                    entity.getBrain().remember(VillagerShopOwnerMod.USER_SHOP, globalPos);
                });
    }
}