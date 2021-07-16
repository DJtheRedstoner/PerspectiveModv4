package me.djtheredstoner.perspectivemod.forge;

import me.djtheredstoner.perspectivemod.asm.ClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.zip.ZipFile;


@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class PerspectiveModTweaker implements IFMLLoadingPlugin {

    public PerspectiveModTweaker() {
        File mods = new File(Launch.minecraftHome, "mods");

        if (!mods.exists()) {
            mods.mkdirs(); // mods folder may not exist in dev yet
        }

        File[] coreModList = mods.listFiles((dir, name) -> name.endsWith(".jar"));
        for (File file : coreModList) {
            try {
                try (ZipFile zipFile = new ZipFile(file)) {
                    if (zipFile.getEntry("net/canelex/perspectivemod/PerspectiveMod.class") != null) {
                        halt("<html><p>Perspective Mod v4 is not compatible with Perspective Mod v3 by Canelex. Please remove Canelex's in order to launch the game.</p></html>");
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void halt(final String message) {
        JOptionPane.showMessageDialog(null, message, "Launch Aborted", JOptionPane.ERROR_MESSAGE);
        try {
            final Class<?> aClass = Class.forName("java.lang.Shutdown");
            final Method exit = aClass.getDeclaredMethod("exit", int.class);
            exit.setAccessible(true);
            exit.invoke(null, 0);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{ClassTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> map) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
