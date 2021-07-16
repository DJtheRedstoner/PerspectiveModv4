package me.djtheredstoner.perspectivemod.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class PerspectiveModConfig extends Vigilant {

    @Property(
        type = PropertyType.SWITCH,
        name = "Perspective Mod",
        category = "General",
        subcategory = "General",
        description = "Toggle Perspective Mod entirely."
    )
    public boolean modEnabled = true;

    @Property(
        type = PropertyType.SWITCH,
        name = "Hold Mode",
        category = "General",
        subcategory = "General",
        description = "Return to normal perspective after releasing keybind."
    )
    public boolean holdMode = true;

    @Property(
        type = PropertyType.SWITCH,
        name = "Invert Pitch",
        category = "General",
        subcategory = "General",
        description = "Invert the pitch while in perspective (same as blc/lunar)."
    )
    public boolean invertPitch = false;

    public PerspectiveModConfig() {
        super(new File("./config/perspectivemodv4.toml"));
        initialize();
    }
}
