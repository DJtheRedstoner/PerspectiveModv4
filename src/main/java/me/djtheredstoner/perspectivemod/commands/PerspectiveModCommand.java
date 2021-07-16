package me.djtheredstoner.perspectivemod.commands;

import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import me.djtheredstoner.perspectivemod.PerspectiveMod;

public class PerspectiveModCommand extends Command {

    public PerspectiveModCommand(String name) {
        super(name);
    }

    @DefaultHandler
    public void handle() {
        EssentialAPI.getGuiUtil().openScreen(PerspectiveMod.instance.config.gui());
    }

}
