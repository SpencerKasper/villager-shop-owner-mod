package net.fabricmc.example;

import com.google.common.collect.*;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.*;
import net.fabricmc.example.mixin.*;
import net.fabricmc.example.mixin.ActivityInvoker;
import net.fabricmc.example.mixin.MemoryModuleTypeInvoker;
import net.fabricmc.example.mixin.PointOfInterestTypeInvoker;
import net.minecraft.block.*;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.*;
import net.minecraft.structure.*;
import net.minecraft.util.dynamic.*;
import net.minecraft.world.poi.*;
import org.apache.logging.log4j.*;

import java.util.function.*;

public class VillagerShopOwnerMod implements ModInitializer {
    public static final String MOD_ID = "villager-shop-owner-mod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> createShopOwnerTasks(float speed) {
        return ImmutableList.of(
                Pair.of(1, new FindShopTask())
        );
    }

    public static final Activity SELL = ActivityInvoker.invokeRegister("sell");
    public static final MemoryModuleType<GlobalPos> USER_SHOP = MemoryModuleTypeInvoker.invokeRegister(
            "user_shop",
            GlobalPos.CODEC
    );

    public static final PointOfInterestType DIAMOND_BLOCK_POI = PointOfInterestTypeInvoker.invokeRegister(
            "diamond_block_poi", PointOfInterestTypeInvoker.invokeGetAllStatesOf(Blocks.DIAMOND_BLOCK), 32, 100
    );

    static {
        addScheduled();
        addMemoryModules();
        addPointsOfInterest();
        VillageGenerator.init();
    }

    private static void addScheduled() {
        ScheduleAccessor.setVillagerDefault(
                new ScheduleBuilder(Schedule.VILLAGER_DEFAULT).withActivity(10, VillagerShopOwnerMod.SELL).build());
    }

    private static void addMemoryModules() {
        VillagerEntityAccessor.setMemoryModules(new ImmutableList.Builder<MemoryModuleType<?>>()
                .addAll(VillagerEntityAccessor.getMemoryModules()).add(USER_SHOP).build());
    }

    private static void addPointsOfInterest() {
        VillagerEntityAccessor.setPointsOfInterest(
                new ImmutableMap.Builder<MemoryModuleType<GlobalPos>, BiPredicate<VillagerEntity, PointOfInterestType>>()
                        .putAll(VillagerEntity.POINTS_OF_INTEREST)
                        .put(USER_SHOP, (villagerEntity, pointOfInterestType) -> pointOfInterestType == DIAMOND_BLOCK_POI)
                        .build());
    }
}
