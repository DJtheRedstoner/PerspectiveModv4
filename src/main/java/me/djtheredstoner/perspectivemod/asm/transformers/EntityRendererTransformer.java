package me.djtheredstoner.perspectivemod.asm.transformers;

import me.djtheredstoner.perspectivemod.asm.ITransformer;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * @see net.minecraft.client.renderer.EntityRenderer
 */
public class EntityRendererTransformer implements ITransformer {

    private static final String HOOK_CLASS = "me/djtheredstoner/perspectivemod/asm/hooks/EntityRendererHook";

    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.EntityRenderer"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            String methodName = mapMethodName(classNode, method);

            if (methodName.equals("orientCamera") || methodName.equals("func_78467_g")) {

                Iterator<AbstractInsnNode> iterator = method.instructions.iterator();

                while (iterator.hasNext()) {
                    AbstractInsnNode next = iterator.next();

                    if(next.getOpcode() == GETFIELD) {
                        FieldInsnNode insn = (FieldInsnNode)next;

                        String ownerName = mapClassName(insn.owner);
                        String fieldName = mapFieldNameFromNode(insn);

                        if(ownerName.equals("net/minecraft/entity/Entity" ) && insn.desc.equals("F")) {

                            InsnList insnList = null;

                            switch (fieldName) {
                                case "rotationYaw":
                                case "field_70177_z":
                                    insnList = insertRotationHook("rotationYaw");
                                    break;
                                case "prevRotationYaw":
                                case "field_70126_B":
                                    insnList = insertRotationHook("prevRotationYaw");
                                    break;
                                case "rotationPitch":
                                case "field_70125_A":
                                    insnList = insertRotationHook("rotationPitch");
                                    break;
                                case "prevRotationPitch":
                                case "field_70127_C":
                                    insnList = insertRotationHook("prevRotationPitch");
                                    break;
                            }

                            if(insnList != null) {
                                method.instructions.insertBefore(insn, insnList);
                                method.instructions.remove(insn);
                            }
                        }
                    } /*else if (next.getOpcode() == DLOAD) {
                        VarInsnNode insn = (VarInsnNode) next;

                        if(insn.getPrevious().getOpcode() == FCONST_0 && insn.getNext().getOpcode() == DNEG) {
                            method.instructions.insert(insn, insertDistanceHook());
                        }
                    }*/
                }
            } else if (methodName.equals("updateCameraAndRender") || methodName.equals("func_181560_a")) {

                Iterator<AbstractInsnNode> iterator = method.instructions.iterator();

                while (iterator.hasNext()) {
                    AbstractInsnNode next = iterator.next();

                    if(next.getOpcode() == GETFIELD) {
                        FieldInsnNode insn = (FieldInsnNode)next;

                        String ownerName = mapClassName(insn.owner);
                        String fieldName = mapFieldNameFromNode(insn);

                        if(ownerName.equals("net/minecraft/client/Minecraft") && (fieldName.equals("inGameHasFocus") || fieldName.equals("field_71415_G")) && insn.desc.equals("Z")
                                && insn.getNext().getNext().getNext().getOpcode() == IFEQ) {
                            method.instructions.insertBefore(insn, insertMouseHook());
                            method.instructions.remove(insn);
                        }
                    }
                }
            }
            //Poggers Ultra FPS Praseodymium mode.
            /* else if (methodName.equals("renderWorldPass") || methodName.equals("func_175068_a")) {

                method.instructions.insert(method.instructions.getFirst(), new InsnNode(RETURN));
            }*/
        }
    }

    private InsnList insertRotationHook(String field) {
        InsnList list = new InsnList();

        list.add(new MethodInsnNode(INVOKESTATIC, HOOK_CLASS, field + "Hook", "(Lnet/minecraft/entity/Entity;)F", false));

        return list;
    }

    private InsnList insertMouseHook() {
        InsnList list = new InsnList();

        list.add(new MethodInsnNode(INVOKESTATIC, HOOK_CLASS, "mouseHook", "(Lnet/minecraft/client/Minecraft;)Z", false));

        return list;
    }

    private InsnList insertDistanceHook() {
        InsnList list = new InsnList();

        list.add(new MethodInsnNode(INVOKESTATIC, HOOK_CLASS, "distanceHook", "(D)D", false));

        return list;
    }
}
