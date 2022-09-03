package me.monmcgt.code.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AdviceClassVisitor extends ClassVisitor {
    private String owner;

    public AdviceClassVisitor(ClassVisitor parent, EnterAdviceInterface enterAdviceInterface, ExitAdviceInterface exitAdviceInterface) {
        super(Opcodes.ASM9, parent);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.owner = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        return methodVisitor;
    }

    public static interface EnterAdviceInterface {
        void run();
    }

    public static interface ExitAdviceInterface {
        void run(int num);
    }
}
