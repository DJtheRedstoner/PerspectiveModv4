package me.djtheredstoner.perspectivemod.forge;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

// ONLY needed in development environment, the mixin tweaker handles this in prod.
public class PerspectiveModLoadingPlugin implements IFMLLoadingPlugin {

    public PerspectiveModLoadingPlugin() {
        MixinBootstrap.init();

        Mixins.addConfiguration("mixins.djperspectivemod.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
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
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
