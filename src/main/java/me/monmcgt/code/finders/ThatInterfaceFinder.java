package me.monmcgt.code.finders;

import me.monmcgt.code.Debug;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ThatInterfaceFinder {
    public static String name = null;

    public static void find(String className, byte[] classFileBuffer) {
        ClassReader classReader = new ClassReader(classFileBuffer);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        // check if this class is not interface then return
        if ((classNode.access & 0x200) == 0) {
            /*if (className.startsWith("lunar/j/IIllIlIlllIIIIIIlllllIlII")) {
                System.out.println("[ThatInterfaceFinder] Found interface but false detected");
            }*/

            return;
        }

        Set<String> methodsName = new HashSet<>();
        addSet(methodsName);

        boolean match = Arrays.stream(classNode.methods.toArray())
                .map((m) -> (MethodNode) m)
                .map(m -> m.name)
                .filter(methodsName::remove)
                .anyMatch(__ -> methodsName.isEmpty());

        if (match) {
            name = className;

            Debug.println("[ThatInterfaceFinder] Found: " + name);
        } /*else {
            if (className.startsWith("lunar/j/IIllIlIlllIIIIIIlllllIlII")) {
                System.out.println("[ThatInterfaceFinder] Not found idk why");
                for (String l : methodsName) {
                    System.out.println("[ThatInterfaceFinder] Not found: " + l);
                }
                List<MethodNode> collect = Arrays.stream(classNode.methods.toArray())
                        .map((m) -> (MethodNode) m)
                        .collect(Collectors.toList());

                for (MethodNode methodNode : collect) {
                    System.out.println("Method: " + methodNode.name);
                }
            }
        }*/
    }

    private static void addSet(Set<String> set) {
        set.add("bridge$getPlayer");
        set.add("bridge$setPlayer");
        set.add("bridge$getPlayerController");
        set.add("bridge$getWorld");
        set.add("bridge$getPointedEntity");
        set.add("bridge$getNetHandler");
        set.add("bridge$getCurrentScreen");
        set.add("bridge$getFontRenderer");
        set.add("bridge$getResourceManager");
        set.add("bridge$getMcDefaultResourcePack");
        set.add("bridge$getTextureManager");
        set.add("bridge$getSession");
        set.add("bridge$getProfileProperties");
        set.add("bridge$getSessionService");
        set.add("bridge$getEntityRenderer");
        set.add("bridge$getCurrentServerData");
        set.add("bridge$getRenderItem");
        set.add("bridge$getSoundHandler");
        set.add("bridge$setSession");
        set.add("bridge$getMcDataDir");
        set.add("bridge$displayScreen");
        set.add("bridge$displayWidth");
        set.add("bridge$displayHeight");
//        set.add("bridge$unicodeFlag");
        set.add("bridge$getGuiScale");
    }
}
