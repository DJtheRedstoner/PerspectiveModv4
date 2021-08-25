package me.djtheredstoner.perspectivemod.asm.transformers;

import me.djtheredstoner.perspectivemod.asm.ITransformer;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

import static org.objectweb.asm.Opcodes.*;

public class ActiveRenderInfoTransformer implements ITransformer {

    private static final String HOOK_CLASS = "me/djtheredstoner/perspectivemod/asm/hooks/ActiveRenderInfoHook";

    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.ActiveRenderInfo"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            String methodName = mapMethodName(classNode, method);

            if (methodName.equals("updateRenderInfo") || methodName.equals("func_74583_a")) {
                ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();

                while (iterator.hasNext()) {
                    AbstractInsnNode next = iterator.next();

                    if(next.getOpcode() == GETFIELD) {
                        FieldInsnNode insn = (FieldInsnNode) next;

                        String ownerName = mapClassName(insn.owner);
                        String fieldName = mapFieldNameFromNode(insn);

                        if(ownerName.equals("net/minecraft/entity/player/EntityPlayer") && insn.desc.equals("F")) {
                            InsnList insnList = null;

                            switch (fieldName) {
                                case "rotationPitch":
                                case "field_70125_A":
                                    insnList = insertRotationHook("Pitch");
                                    break;
                                case "rotationYaw":
                                case "field_70177_z":
                                    insnList = insertRotationHook("Yaw");
                                    break;
                            }

                            if(insnList != null) {
                                method.instructions.insertBefore(insn, insnList);
                                method.instructions.remove(insn);
                            }
                        }
                    }
                }
            }
        }
    }

    private InsnList insertRotationHook(String field) {
        InsnList insnList = new InsnList();

        insnList.add(new MethodInsnNode(INVOKESTATIC, HOOK_CLASS, "rotation" + field + "Hook", "(Lnet/minecraft/entity/player/EntityPlayer;)F", false));

        return insnList;
    }
}
