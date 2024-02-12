package me.djtheredstoner.perspectivemod.config;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

import java.io.File;

public class PerspectiveModConfig extends Config {

    @Checkbox(
            name = "Perspective Mod",
            description = "Toggle Perspective Mod entirely.",
            category = "General",
            subcategory = "General"
    )
    public boolean modEnabled = true;

    @Checkbox(
        name = "Hold Mode",
        category = "General",
        subcategory = "General",
        description = "Return to normal perspective after releasing keybind."
    )
    public boolean holdMode = true;

    @Checkbox(
        name = "Invert Pitch",
        category = "General",
        subcategory = "General",
        description = "Invert the pitch while in perspective (same as blc/lunar)."
    )
    public boolean invertPitch = false;

    public PerspectiveModConfig() {
        super(new Mod("FreeLook", ModType.UTIL_QOL, "/assets/logo.png"), "perspectivemodv4.json");
        initialize();
    }
}
