package com.dicedpixels.recipebooktweaks;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.Config.Gui.Background;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.PrefixText;

@Config(name = "recipe-book-tweaks")
@Background(Background.TRANSPARENT)
public class ModConfig implements ConfigData {
    public boolean bounce = true;
    public boolean ungroup = false;

    @PrefixText()
    public boolean unlock = false;
}
