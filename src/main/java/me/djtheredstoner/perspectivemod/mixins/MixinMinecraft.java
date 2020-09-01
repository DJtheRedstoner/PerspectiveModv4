package me.djtheredstoner.perspectivemod.mixins;

import me.djtheredstoner.perspectivemod.PerspectiveMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static org.objectweb.asm.Opcodes.*;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Redirect(method = "runTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;thirdPersonView:I", opcode = PUTFIELD))
    public void setThirdPersonView(GameSettings gameSettings, int value) {
        if(PerspectiveMod.perspectiveToggled) {
            PerspectiveMod.resetPerspective();
        } else {
            gameSettings.thirdPersonView = value;
        }
    }

}
