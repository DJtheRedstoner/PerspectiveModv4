package me.djtheredstoner.perspectivemod.asm.hooks;

import me.djtheredstoner.perspectivemod.PerspectiveMod;
import net.minecraft.entity.player.EntityPlayer;

public class ActiveRenderInfoHook {

    public static float rotationYawHook(EntityPlayer entity) {
        return PerspectiveMod.instance.perspectiveToggled ? PerspectiveMod.instance.cameraYaw : entity.rotationYaw;
    }

    public static float rotationPitchHook(EntityPlayer entity) {
        return PerspectiveMod.instance.perspectiveToggled ? PerspectiveMod.instance.cameraPitch : entity.rotationPitch;
    }

}
