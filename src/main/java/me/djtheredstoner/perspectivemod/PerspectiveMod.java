package me.djtheredstoner.perspectivemod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.djtheredstoner.perspectivemod.config.PerspectiveModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.io.File;

@Mod(modid = "djperspectivemod", name = "Perspective Mod v4", version = "4.0", acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class PerspectiveMod {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final KeyBinding perspectiveKey = new KeyBinding("Perspective", Keyboard.KEY_LMENU, "Perspective Mod");
    private static final Logger logger = LogManager.getLogger("Perspective Mod v4");

    public static PerspectiveModConfig config;

    public static boolean perspectiveToggled = false;
    public static float cameraYaw = 0F;
    public static float cameraPitch = 0F;
    private static int previousPerspective = 0;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File configFile = new File(event.getModConfigurationDirectory(), "perspectivemodv4.json");
        loadConfig(configFile);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveConfig(configFile)));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientRegistry.registerKeyBinding(perspectiveKey);
        ClientCommandHandler.instance.registerCommand(new PerspectiveModCommand());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyEvent(InputEvent.KeyInputEvent event) {
        if (perspectiveKey.getKeyCode() > 0) {
            onPressed(Keyboard.getEventKey(), Keyboard.getEventKeyState());
        }
    }

    @SubscribeEvent
    public void onMouseEvent(InputEvent.MouseInputEvent event) {
        if (perspectiveKey.getKeyCode() < 0) {
            onPressed(Mouse.getEventButton() - 100, Mouse.getEventButtonState());
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(event.gui != null) {
            resetPerspective();
        }
    }

    public static void onPressed(int eventKey, boolean state) {
        if (eventKey == perspectiveKey.getKeyCode()) {
            if (config.modEnabled) {
                if (state) {
                    perspectiveToggled = !perspectiveToggled;
                    cameraYaw = mc.thePlayer.rotationYaw;
                    cameraPitch = mc.thePlayer.rotationPitch;

                    if (perspectiveToggled) {
                        previousPerspective = mc.gameSettings.thirdPersonView;
                        mc.gameSettings.thirdPersonView = 1;
                    } else {
                        mc.gameSettings.thirdPersonView = previousPerspective;
                    }
                } else if (config.holdMode) {
                    resetPerspective();
                }
            } else if (perspectiveToggled) {
                resetPerspective();
            }
        }
    }

    public static boolean overrideMouse() {
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

            cameraYaw += f3 * 0.15F;
            cameraPitch += f4 * 0.15F;

            if (cameraPitch > 90) cameraPitch = 90;
            if (cameraPitch < -90) cameraPitch = -90;
        }

        return false;
    }

    public static void resetPerspective() {
        perspectiveToggled = false;
        mc.gameSettings.thirdPersonView = previousPerspective;
    }

    public static void loadConfig(File configFile) {
        if (configFile.exists()) {
            try {
                String json = FileUtils.readFileToString(configFile);
                config = gson.fromJson(json, PerspectiveModConfig.class);
            } catch (Exception e) {
                logger.error("Error Loading Config, try deleting the file.");
                e.printStackTrace();
            }
        } else {
            config = new PerspectiveModConfig();
            saveConfig(configFile);
        }
    }

    public static void saveConfig(File configFile) {
        try {
            String json = gson.toJson(config);
            FileUtils.write(configFile, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
