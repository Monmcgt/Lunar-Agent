package me.monmcgt.code.asm;

public class AdviceUtil {
    /*public static AdviceAdapter createAdvice(MethodVisitor parent, int access, String name, String descriptor, AdviceClassVisitor.EnterAdviceInterface enterAdvice, AdviceClassVisitor.ExitAdviceInterface exitAdvice) {
        return new AdviceAdapter(Opcodes.ASM9, parent, access, name, descriptor) {
            @Override
            protected void onMethodEnter() {
                super.onMethodEnter();
                enterAdvice.run();
            }

            @Override
            protected void onMethodExit(int i) {
                super.onMethodExit(i);
                exitAdvice.run(i);
            }
        };
    }*/
}
