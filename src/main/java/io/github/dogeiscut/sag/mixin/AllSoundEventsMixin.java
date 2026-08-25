package io.github.dogeiscut.sag.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.AllSoundEvents;
import io.github.dogeiscut.sag.registry.SagSoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllSoundEvents.SoundEntryBuilder.class)
public abstract class AllSoundEventsMixin {

    @Inject(
            method = "build",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"),
            cancellable = true,
            remap = false
    )
    private void sag$cancelCreateMapPut(CallbackInfoReturnable<AllSoundEvents.SoundEntry> cir, @Local AllSoundEvents.SoundEntry entry) {
        if (((Object) this) instanceof SagSoundEvents.SagSoundEntryBuilder) {
            cir.setReturnValue(entry);
        }
    }
}