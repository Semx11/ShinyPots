package me.semx11.shinypots;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class RenderEffectVisitor extends MethodVisitor {

    public RenderEffectVisitor(MethodVisitor mv) {
        super(Opcodes.ASM4, mv);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        if (opcode == Opcodes.SIPUSH && operand == 768) {

            // -== DOES NOT WORK ==-
            // Rendering of Item Glint has changed drastically in 1.8, and I haven't done much
            // research to try and make it work. It probably isn't as easy as changing a single
            // integer anymore.. If anyone knows what should be changed, feel free to make a
            // pull request.

            operand = 773; // This adds the 'shiny pots'
            // org.lwjgl.opengl.GL11.GL_ONE_MINUS_DST_ALPHA
        }

        super.visitIntInsn(opcode, operand);
    }

}
