package com.dicedpixels.recipebooktweaks.mixin;

import com.dicedpixels.recipebooktweaks.RecipeBookTweaks;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class Unlock {
    @Inject(method = "onPlayerConnect", at = @At("RETURN"))
    public void onOnPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo callbackInfo) {
        if (RecipeBookTweaks.config.unlock) {
            player.unlockRecipes(player.server.getRecipeManager().values());
        }
    }
}
