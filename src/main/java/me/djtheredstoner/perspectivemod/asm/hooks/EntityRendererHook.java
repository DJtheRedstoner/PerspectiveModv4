package me.djtheredstoner.perspectivemod.asm.hooks;

import me.djtheredstoner.perspectivemod.PerspectiveMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class EntityRendererHook {

    public static float rotationYawHook(Entity entity) {
        return PerspectiveMod.perspectiveToggled ? PerspectiveMod.cameraYaw : entity.rotationYaw;
    }

    public static float prevRotationYawHook(Entity entity) {
        return PerspectiveMod.perspectiveToggled ? PerspectiveMod.cameraYaw : entity.prevRotationYaw;
    }

    public static float rotationPitchHook(Entity entity) {
        return PerspectiveMod.perspectiveToggled ? PerspectiveMod.cameraPitch : entity.rotationPitch;
    }

    public static float prevRotationPitchHook(Entity entity) {
        return PerspectiveMod.perspectiveToggled ? PerspectiveMod.cameraPitch : entity.prevRotationPitch;
    }

    public static boolean mouseHook(Minecraft minecraft) {
        return PerspectiveMod.overrideMouse();
    }

    public static double distanceHook(double value) {
        return value;
    }
}
