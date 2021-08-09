package me.djtheredstoner.perspectivemod;

import me.djtheredstoner.perspectivemod.commands.PerspectiveModCommand;
import me.djtheredstoner.perspectivemod.config.PerspectiveModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@Mod(modid = "djperspectivemod", name = "Perspective Mod v4", version = "4.5", acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class PerspectiveMod {

    @Mod.Instance
    public static PerspectiveMod instance;

    public final PerspectiveModConfig config = new PerspectiveModConfig();

    public boolean perspectiveToggled = false;
    public float cameraYaw = 0F;
    public float cameraPitch = 0F;

    private int previousPerspective = 0;
    private boolean prevState = false;

    private final Minecraft mc = Minecraft.getMinecraft();
    private final KeyBinding perspectiveKey = new KeyBinding("Perspective", Keyboard.KEY_LMENU, "Perspective Mod");
    private final Logger logger = LogManager.getLogger("Perspective Mod v4");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        config.preload();

        ClientRegistry.registerKeyBinding(perspectiveKey);
        new PerspectiveModCommand("perspectivemod").register();
        new PerspectiveModCommand("pmod").register();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void tick(TickEvent.RenderTickEvent event) {
        boolean down = GameSettings.isKeyDown(perspectiveKey);
        if(down != prevState && mc.currentScreen == null && mc.theWorld != null && mc.thePlayer != null) {
            prevState = down;
            onPressed(down);
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui != null && perspectiveToggled && config.holdMode) {
            resetPerspective();
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (perspectiveToggled) {
            resetPerspective();
        }
    }

    public void onPressed(boolean state) {
        if (config.modEnabled) {
            if (state) {
                cameraYaw = mc.thePlayer.rotationYaw;
                cameraPitch = mc.thePlayer.rotationPitch;

                if (perspectiveToggled) {
                    resetPerspective();
                } else {
                    enterPerspective();
                }
                mc.renderGlobal.setDisplayListEntitiesDirty();
            } else if (config.holdMode) {
                resetPerspective();
            }
        } else if (perspectiveToggled) {
            resetPerspective();
        }
    }

    public void enterPerspective() {
        perspectiveToggled = true;
        previousPerspective = mc.gameSettings.thirdPersonView;
        mc.gameSettings.thirdPersonView = 1;
    }

    public void resetPerspective() {
        perspectiveToggled = false;
        mc.gameSettings.thirdPersonView = previousPerspective;
        mc.renderGlobal.setDisplayListEntitiesDirty();
    }

    public boolean overrideMouse() {
        if (mc.inGameHasFocus && Display.isActive()) {
            if (!perspectiveToggled) {
                return true;
            }

            // CODE
            mc.mouseHelper.mouseXYChange();
            float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8.0F;
            float f3 = (float) mc.mouseHelper.deltaX * f2;
            float f4 = (float) mc.mouseHelper.deltaY * f2;

            if(config.invertPitch) {
                f4 = -f4;
            }

            cameraYaw += f3 * 0.15F;
            cameraPitch += f4 * 0.15F;

            if (cameraPitch > 90) cameraPitch = 90;
            if (cameraPitch < -90) cameraPitch = -90;
            mc.renderGlobal.setDisplayListEntitiesDirty();
        }

        return false;
    }
}
