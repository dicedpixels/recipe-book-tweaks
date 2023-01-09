package com.dicedpixels.recipebooktweaks;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeBookTweaks implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("recipe-book-tweaks");
    public static RecipeBookTweaksConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(RecipeBookTweaksConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(RecipeBookTweaksConfig.class).getConfig();

        LOGGER.info("Recipe Book Tweaks initialized.");
    }
}
