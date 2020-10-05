package me.djtheredstoner.perspectivemod.commands;

import club.sk1er.mods.core.ModCore;
import me.djtheredstoner.perspectivemod.PerspectiveMod;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.Collections;
import java.util.List;

public class PerspectiveModCommand extends CommandBase {

    private final String PREFIX = "§c[§6Perspective Mod§c] §r";

    @Override
    public String getCommandName() {
        return "perspectivemod";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("pmod");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "§6Perspective Mod Help\n" +
            "§b/pmod <enable|disable> §7- Enables or disables the mod.\n" +
            "§b/pmod mode <hold|toggle> §7- Changes the mode.\n" +
            "§7Edit the keybind in the minecraft controls menu.";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equals("eW91IGZvdW5kIGEgc2VjcmV0IQo=")) {
            sendMessage("you found a secret!");
        } else {
            ModCore.getInstance().getGuiHandler().open(PerspectiveMod.config.gui());
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }

    private void sendHelp() {
        sendMessage(getCommandUsage(Minecraft.getMinecraft().thePlayer), false);
    }

    private void sendMessage(String message, boolean addPrefix) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText((addPrefix ? PREFIX : "") + message));
    }

    private void sendMessage(String message) {
        sendMessage(message, true);
    }
}
