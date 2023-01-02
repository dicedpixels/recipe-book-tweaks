package com.dicedpixels.recipebooktweaks.mixin;

import com.dicedpixels.recipebooktweaks.RecipeBookTweaks;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mixin(ClientRecipeBook.class)
public class Ungroup extends RecipeBook {
    @Shadow
    private Map<RecipeBookGroup, List<RecipeResultCollection>> resultsByGroup;

    @Inject(method = "getResultsForGroup", at = @At("HEAD"), cancellable = true)
    protected void onGetResultsForGroup(RecipeBookGroup category, CallbackInfoReturnable<List<RecipeResultCollection>> returnable) {
        if (RecipeBookTweaks.config.ungroup) {
            List<RecipeResultCollection> rrcForCategory = resultsByGroup.getOrDefault(category, Collections.emptyList());
            List<RecipeResultCollection> rrcUngrouped = Lists.newArrayList(rrcForCategory);

            for (RecipeResultCollection rrc : rrcForCategory) {
                if (!rrc.hasSingleOutput()) {
                    rrcUngrouped.remove(rrc);
                    List<Recipe<?>> recipes = rrc.getAllRecipes();

                    for (Recipe<?> recipe : recipes) {
                        RecipeResultCollection rrcSingle = new RecipeResultCollection(Collections.singletonList(recipe));
                        rrcSingle.initialize(this);
                        rrcUngrouped.add(rrcSingle);
                    }
                }
            }

            returnable.setReturnValue(rrcUngrouped);
        }

    }
}
