package net.fabricmc.example.mixin;

import net.minecraft.entity.ai.brain.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(Activity.class)
public interface ActivityInvoker {
    @Invoker("register")
    public static Activity invokeRegister(String id) {
        throw new AssertionError();
    }
}