package me.djtheredstoner.perspectivemod.asm.transformers;

import me.djtheredstoner.perspectivemod.asm.ITransformer;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * @see net.minecraft.client.renderer.entity.RenderManager
 */
public class RenderManagerTransformer implements ITransformer {
    private static final String HOOK_CLASS = "me/djtheredstoner/perspectivemod/asm/hooks/RenderManagerHook";

    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.entity.RenderManager"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode method : classNode.methods) {
            String methodName = mapMethodName(classNode, method);

            if (methodName.equals("cacheActiveRenderInfo") || methodName.equals("func_180597_a")) {
                Iterator<AbstractInsnNode> iterator = method.instructions.iterator();

                while (iterator.hasNext()) {
                    AbstractInsnNode next = iterator.next();

                    if (next.getOpcode() == PUTFIELD) {
                        FieldInsnNode insn = (FieldInsnNode)next;

                        String ownerName = mapClassName(insn.owner);
                        String fieldName = mapFieldNameFromNode(insn);

                        if (ownerName.equals("net/minecraft/client/renderer/entity/RenderManager") && insn.desc.equals("F")) {

                            InsnList insnList = null;

                            switch (fieldName) {
                                case "playerViewX":
                                case "field_78732_j":
                                    insnList = insertRotationHook("playerViewX");
                                    break;
                                case "playerViewY":
                                case "field_78735_i":
                                    insnList = insertRotationHook("playerViewY");
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

    public InsnList insertRotationHook(String field) {
        InsnList list = new InsnList();

        list.add(new MethodInsnNode(INVOKESTATIC, HOOK_CLASS, field + "Hook", "(Lnet/minecraft/client/renderer/entity/RenderManager;F)V", false));

        return list;
    }
}
