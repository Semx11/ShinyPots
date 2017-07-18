package me.semx11.shinypots;

import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class RenderItemTransformer implements IClassTransformer {

    private Logger logger = LogManager.getLogger("ShinyPots");

    @Override
    public byte[] transform(String name, String transformedName, byte[] buffer) {
        if (transformedName.equals("net.minecraft.client.renderer.entity.RenderItem")) {
            logger.info("Found RenderItem class");
            ClassReader reader = new ClassReader(buffer);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);
            ClassVisitor visitor = new ClassVisitor(Opcodes.ASM4, writer) {

                @Override
                public MethodVisitor visitMethod(int access, String name, String desc,
                        String signature, String[] exceptions) {
                    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

                    if (desc.equals("(Lnet/minecraft/client/resources/model/IBakedModel;)V")) {
                        logger.info("Found RenderItem#renderEffect");
                        return new RenderEffectVisitor(mv);
                    }

                    return mv;
                }

            };

            reader.accept(visitor, 0);
            return writer.toByteArray();
        }

        return buffer;
    }

}
