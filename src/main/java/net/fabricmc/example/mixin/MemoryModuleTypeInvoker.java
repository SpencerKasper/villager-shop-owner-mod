package net.fabricmc.example.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

import java.util.Set;

@Mixin(MemoryModuleType.class)
public interface MemoryModuleTypeInvoker {
    @Invoker("register")
    public static <U> MemoryModuleType<U> invokeRegister(String id, Codec<U> codec) {
        throw new AssertionError();
    }
}