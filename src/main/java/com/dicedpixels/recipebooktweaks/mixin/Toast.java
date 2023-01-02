package com.dicedpixels.recipebooktweaks.mixin;

import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast.Visibility;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeToast.class)
public class Toast {
    @Inject(at = @At("HEAD"), method = "draw", cancellable = true)
    public void onDraw(MatrixStack matrices, ToastManager manager, long startTime, CallbackInfoReturnable<Visibility> returnable) {
        returnable.setReturnValue(Visibility.HIDE);
    }
}
