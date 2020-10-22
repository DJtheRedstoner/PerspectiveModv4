package me.djtheredstoner.perspectivemod.asm.hooks;

import me.djtheredstoner.perspectivemod.PerspectiveMod;
import net.minecraft.client.settings.GameSettings;

public class MinecraftHook {

    public static void thirdPersonHook(GameSettings gameSettings, int value) {
        if(PerspectiveMod.instance.perspectiveToggled) {
            PerspectiveMod.instance.resetPerspective();
        } else {
            gameSettings.thirdPersonView = value;
        }
    }

}
