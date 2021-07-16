package me.djtheredstoner.perspectivemod.asm.transformers;

import me.djtheredstoner.perspectivemod.asm.ITransformer;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * @see net.minecraft.client.Minecraft
 */
public class MinecraftTransformer implements ITransformer {

    private static final String HOOK_CLASS = "me/djtheredstoner/perspectivemod/asm/hooks/MinecraftHook";

    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.Minecraft"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            String methodName = mapMethodName(classNode, method);

            if (methodName.equals("runTick") || methodName.equals("func_71407_l")) {
                Iterator<AbstractInsnNode> iterator = method.instructions.iterator();

                while (iterator.hasNext()) {
                    AbstractInsnNode next = iterator.next();

                    if (next.getOpcode() == PUTFIELD) {
                        FieldInsnNode insn = (FieldInsnNode) next;

                        String ownerName = mapClassName(insn.owner);
                        String fieldName = mapFieldNameFromNode(insn);

                        if (ownerName.equals("net/minecraft/client/settings/GameSettings") &&
                            (fieldName.equals("thirdPersonView") || fieldName.equals("field_74320_O")) && insn.desc.equals("I")) {
                            method.instructions.insertBefore(insn, insertThirdPersonHook());
                            method.instructions.remove(insn);
                        }
                    }
                }
            }
        }
    }

    public InsnList insertThirdPersonHook() {
        InsnList list = new InsnList();

        list.add(new MethodInsnNode(INVOKESTATIC, HOOK_CLASS, "thirdPersonHook", "(Lnet/minecraft/client/settings/GameSettings;I)V", false));

        return list;
    }
}
