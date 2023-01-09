package com.dicedpixels.recipebooktweaks.mixin;

import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Locale;

// Enables search when recipes are ungrouped
@Mixin(RecipeBookWidget.class)
abstract class MixinRecipeBookWidget {
    @Shadow
    protected AbstractRecipeScreenHandler<?> craftingScreenHandler;
    @Shadow
    private String searchText;
    @Shadow
    private ClientRecipeBook recipeBook;
    @Shadow
    @Final
    private RecipeBookResults recipesArea;

    @Inject(method = "refreshResults", locals = LocalCapture.CAPTURE_FAILSOFT, at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectLinkedOpenHashSet;<init>(Ljava/util/Collection;)V"), cancellable = true)
    private void onRefreshResults(
            boolean resetCurrentPage,
            CallbackInfo callbackInfo,
            List<RecipeResultCollection> rrcForGroup,
            List<RecipeResultCollection> rrcUpdated,
            String searchFieldText) {

        rrcUpdated.removeIf((rrc) -> {
            for (Recipe<?> recipe : rrc.getAllRecipes()) {
                String recipeName = recipe.getOutput().getName().getString().toLowerCase(Locale.ROOT);
                return !recipeName.contains(this.searchText.toLowerCase(Locale.ROOT));
            }
            return false;
        });

        if (this.recipeBook.isFilteringCraftable(this.craftingScreenHandler)) {
            rrcUpdated.removeIf((rrc) -> !rrc.hasCraftableRecipes());
        }

        this.recipesArea.setResults(rrcUpdated, resetCurrentPage);
        callbackInfo.cancel();
    }
}
