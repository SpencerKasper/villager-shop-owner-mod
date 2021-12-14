package net.fabricmc.example.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

import java.util.Set;

@Mixin(PointOfInterestType.class)
public interface PointOfInterestTypeInvoker {
    @Invoker("getAllStatesOf")
    public static Set<BlockState> invokeGetAllStatesOf(Block block) {
        throw new AssertionError();
    }
    @Invoker("register")
    public static PointOfInterestType invokeRegister(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance) {
        throw new AssertionError();
    }
}