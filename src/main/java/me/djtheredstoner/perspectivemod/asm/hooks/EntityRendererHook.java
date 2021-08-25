package me.djtheredstoner.perspectivemod.asm.hooks;

import me.djtheredstoner.perspectivemod.PerspectiveMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class EntityRendererHook {

    public static float rotationYawHook(Entity entity) {
        return PerspectiveMod.instance.perspectiveToggled ? PerspectiveMod.instance.cameraYaw : entity.rotationYaw;
    }

    public static float prevRotationYawHook(Entity entity) {
        return PerspectiveMod.instance.perspectiveToggled ? PerspectiveMod.instance.cameraYaw : entity.prevRotationYaw;
    }

    public static float rotationPitchHook(Entity entity) {
        return PerspectiveMod.instance.perspectiveToggled ? PerspectiveMod.instance.cameraPitch : entity.rotationPitch;
    }

    public static float prevRotationPitchHook(Entity entity) {
        return PerspectiveMod.instance.perspectiveToggled ? PerspectiveMod.instance.cameraPitch : entity.prevRotationPitch;
    }

    public static boolean mouseHook(Minecraft minecraft) {
        return PerspectiveMod.instance == null ? minecraft.inGameHasFocus : PerspectiveMod.instance.overrideMouse();
    }

    public static double distanceHook(double value) {
        return value;
    }
}
