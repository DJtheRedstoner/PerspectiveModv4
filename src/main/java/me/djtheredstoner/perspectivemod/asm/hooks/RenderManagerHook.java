package me.djtheredstoner.perspectivemod.asm.hooks;

import me.djtheredstoner.perspectivemod.PerspectiveMod;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderManagerHook {

    public static void playerViewXHook(RenderManager renderManager, float value) {
        renderManager.playerViewX = PerspectiveMod.instance.perspectiveToggled ? PerspectiveMod.instance.cameraPitch : value;
    }

    public static void playerViewYHook(RenderManager renderManager, float value) {
        renderManager.playerViewY = PerspectiveMod.instance.perspectiveToggled ? PerspectiveMod.instance.cameraYaw : value;
    }
}
