package com.dicedpixels.recipebooktweaks.mixin;

import com.dicedpixels.recipebooktweaks.RecipeBookTweaks;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

// Disables bounce animations for newly discovered recipes
@Mixin(RecipeBook.class)
abstract class MixinRecipeBook {
    @Shadow
    @Final
    protected Set<Identifier> toBeDisplayed;

    @Inject(method = "shouldDisplay", at = @At("HEAD"), cancellable = true)
    private void onShouldDisplay(Recipe<?> recipe, CallbackInfoReturnable<Boolean> returnable) {
        if (RecipeBookTweaks.config.bounce) {
            returnable.setReturnValue(toBeDisplayed.contains(recipe.getId()));
        } else {
            returnable.setReturnValue(false);
        }
    }
}
