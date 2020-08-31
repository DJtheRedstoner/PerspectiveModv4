package me.djtheredstoner.perspectivemod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
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
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0 && args.length < 3) {
            String arg = args[0];
            if (arg.equalsIgnoreCase("enable")) {
                PerspectiveMod.config.modEnabled = true;

                sendMessage(sender, "§bMod §aEnabled§b.");
            } else if (arg.equalsIgnoreCase("disable")) {
                PerspectiveMod.config.modEnabled = false;

                sendMessage(sender, "§bMod §cDisabled§b.");
            } else if (arg.equalsIgnoreCase("mode")) {
                if (args.length == 2) {
                    String mode = args[1];
                    if (mode.equalsIgnoreCase("hold")) {
                        PerspectiveMod.config.holdMode = true;

                        sendMessage(sender, "§bMode set to hold.");
                    } else if (mode.equalsIgnoreCase("toggle")) {
                        PerspectiveMod.config.holdMode = false;

                        sendMessage(sender, "§bMode set to toggle.");
                    } else {
                        sendMessage(sender, "§cInvalid mode. Valid mode are hold and toggle.");
                    }
                } else {
                    sendMessage(sender, "§cYou must specify a mode. Valid Modes are hold and toggle.");
                }
            } else {
                sendHelp(sender);
            }
        } else {
            sendHelp(sender);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }

    private void sendHelp(ICommandSender sender) {
        sendMessage(sender, getCommandUsage(sender), false);
    }

    private void sendMessage(ICommandSender sender, String message, boolean addPrefix) {
        sender.addChatMessage(new ChatComponentText((addPrefix ? PREFIX : "") + message));
    }

    private void sendMessage(ICommandSender sender, String message) {
        sendMessage(sender, message, true);
    }
}
