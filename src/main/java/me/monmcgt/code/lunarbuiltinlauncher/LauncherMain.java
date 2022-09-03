package me.monmcgt.code.lunarbuiltinlauncher;

import me.monmcgt.code.Debug;
import me.monmcgt.code.Fields;
import me.monmcgt.code.lunarbuiltinlauncher.entities.Player;
import me.monmcgt.code.lunarbuiltinlauncher.entities.PlayerInfo;
import me.monmcgt.code.lunarbuiltinlauncher.exceptions.NotReadyException;
import me.monmcgt.code.lunarbuiltinlauncher.httpservers.Express;
import me.monmcgt.code.modules.ESP;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class LauncherMain implements Runnable {
    public static LauncherMain INSTANCE;

    public static Express expressInstance;

    public static String[] mainArgs;
    private static Thread thread;

    public ClassLoader classLoader;

    public Class<?> initChatClass;
    public Method initChatMethod;

    public Class<?> bridgeChatClass;
    public Method bridgeChatMethod;

    public Class<?> printChatParam = null;

    public boolean alreadyInited = false;

    private static boolean alreadyStart = false;

    public static Class<?> lunarFcClass;
    public static Method getBridgeMainInstanceMethod;
    public static Object bridgeMainInstance;
    public static Class<?> bridgeMainClass;
    public static Method bridge$getGuiIngameMethod;
    public static Object guiIngameObject;
    public static Class<?> guiIngameClass;
    public static Method bridge$getChatGUIMethod;
    public static Object chatGUIObject;
    public static Class<?> chatGUIClass;
    public static Method bridge$printChatMessageMethod;
//    public static Method sendMessageMethod;
    public static Class<?> textComponentClass;
    public static Constructor<?> textComponentConstructor;
    public static Method bridge$getPlayerMethod;
    public static Method bridge$sendChatMessageMethod = null;

    /*static {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);

//                    INSTANCE.classLoader = Thread.currentThread().getContextClassLoader();

                    INSTANCE.classLoader = MessageEventClassFinder.classLoader;

//                    System.out.println("Reloaded classloader");
//                    System.out.println("INSTANCE.classLoader = " + INSTANCE.classLoader);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/

    public static void start() {
        if (alreadyStart) {
            return;
        }

        Debug.println("Starting LauncherMain class...");

        /*mainArgs = args;*/
        INSTANCE = new LauncherMain();
        /*thread = new Thread(INSTANCE);
        thread.start();*/

        if (!INSTANCE.alreadyInited) {
            INSTANCE.init();
        }

        expressInstance = new Express();
        expressInstance.start();

        /*ScannerMain scannerMain = new ScannerMain();
        scannerMain.start();*/

        /*String test = "{\"message\":\"Hello World!\"}";
        JSON$C$SendChat json = new Gson().fromJson(test, JSON$C$SendChat.class);
        System.out.println(json.toString());*/

        alreadyStart = true;
    }

    public void init() {
        try {
//            System.out.println("Initialising...");

//            this.classLoader = Thread.currentThread().getContextClassLoader();

            this.alreadyInited = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void init2() {
        try {
            lunarFcClass = this.classLoader.loadClass(Data.LUNAR_FC_CLASS.replace("/", "."));
            getBridgeMainInstanceMethod = lunarFcClass.getMethod(Data.PRE_PRINT_CHAT_METHOD_IN_LUNAR_FC);
            bridgeMainInstance = getBridgeMainInstanceMethod.invoke(null);
            bridgeMainClass = bridgeMainInstance.getClass();
            bridge$getGuiIngameMethod = bridgeMainClass.getMethod("bridge$getGuiIngame");
            guiIngameObject = bridge$getGuiIngameMethod.invoke(bridgeMainInstance);
            guiIngameClass = guiIngameObject.getClass();
            bridge$getChatGUIMethod = guiIngameClass.getMethod("bridge$getChatGUI");
            chatGUIObject = bridge$getChatGUIMethod.invoke(guiIngameObject);
            chatGUIClass = chatGUIObject.getClass();
            if (this.printChatParam == null) {
                this.setPrintChatParam(chatGUIClass);
            }
            bridge$printChatMessageMethod = chatGUIClass.getMethod("bridge$printChatMessage", this.printChatParam);
            textComponentClass = this.classLoader.loadClass(Data.TEXT_COMPONENT_CLASS.replace("/", "."));
            textComponentConstructor = textComponentClass.getConstructor(String.class);
//            sendMessageMethod = bridgeMainClass.getMethod("sendMessage", String.class, boolean.class);
            bridge$getPlayerMethod = bridgeMainClass.getMethod("bridge$getPlayer");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printChatMessage(String message) throws NotReadyException {
        this.checkReady();

        try {
            /*Class<?> textComponentClass = this.classLoader.loadClass(Data.TEXT_COMPONENT_CLASS.replace("/", "."));
            Object textComponent = textComponentClass.getConstructor(String.class).newInstance(message);*/
            Object textComponent = textComponentConstructor.newInstance(message);

            /*Class<?> var0 = this.classLoader.loadClass(Data.LUNAR_FC_CLASS.replace("/", "."));
            Method var1 = var0.getMethod(Data.PRE_PRINT_CHAT_METHOD_IN_LUNAR_FC);
            Object var2 = var1.invoke(null);*/
            /*Object var2 = this.getBridgeInterfaceObject();
            Class<?> var3 = var2.getClass();
            Method var4 = var3.getMethod("bridge$getGuiIngame");
            Object var5 = var4.invoke(var2);
            Class<?> var6 = var5.getClass();
            Method var7 = var6.getMethod("bridge$getChatGUI");
            Object var8 = var7.invoke(var5);
            Class<?> var9 = var8.getClass();
            if (this.printChatParam == null) {
                this.setPrintChatParam(var9);
            }
            Method var10 = var9.getMethod("bridge$printChatMessage", this.printChatParam);
            var10.invoke(var8, textComponent);*/
            bridge$printChatMessageMethod.invoke(chatGUIObject, textComponent);
        } catch (/*ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTarget*/Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getDebugFPS() throws NotReadyException {
        this.checkReady();

        try {
            Object var0 = this.getBridgeInterfaceObject();
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$getDebugFPS");
            return (int) var2.invoke(var0);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDisplayTitle(String title) throws NotReadyException {
        this.checkReady();

        try {
            Object var0 = this.getBridgeInterfaceObject();
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$setDisplayTitle");
            var2.invoke(var0, title);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message, boolean b) throws NotReadyException {
        this.checkReady();

        try {
            Class<?> var0 = this.classLoader.loadClass(Data.LUNAR_FC_CLASS.replace("/", "."));
            Method var1 = var0.getMethod("sendMessage", String.class, boolean.class);
            var1.invoke(null, message, b);
//            sendMessageMethod.invoke(null, message, b);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) throws NotReadyException {
        this.sendMessage(message, true);
    }

    public void sendChatMessage(String message) throws NotReadyException {
        this.checkReady();

        try {
            /*Object var0 = this.getBridgeInterfaceObject();
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$getPlayer");*/
            Method var2 = bridge$getPlayerMethod;
//            Object var3 = var2.invoke(var0);
            Object var3 = var2.invoke(bridgeMainInstance);
            Class<?> var4 = var3.getClass();
//            Method var5 = var4.getMethod("bridge$sendChatMessage", String.class);
            if (bridge$sendChatMessageMethod == null) {
                bridge$sendChatMessageMethod = var4.getMethod("bridge$sendChatMessage", String.class);
            }
//            var5.invoke(var3, message);
            bridge$sendChatMessageMethod.invoke(var3, message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getPlayerEntities() throws NotReadyException {
        this.checkReady();

        if (Data.ENTITY_OTHER_PLAYER_MP_CLASS == null) {
            throw new NotReadyException("EntityPlayerSP class is null");
        }

        List<Player> players = new ArrayList<>();

        try {
            Object var0 = this.getBridgeInterfaceObject();
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$getWorld");
            Object var3 = var2.invoke(var0);
            Class<?> var4 = var3.getClass();
            Method var5 = var4.getMethod("bridge$getPlayerEntities");
            Object var6 = var5.invoke(var3);

            return var6;

                // lunar.S.lIlIIIllIlIIIlIlIlIIllIlI
            /*} else {
                System.out.println("MySelf null?" + mySelf);
                System.out.println("Others size?" + others.size());
            }*/
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException /*|
                 ClassNotFoundException*/ e) {
            throw new RuntimeException(e);
        }

//        return players;
    }

    public List<Player> getPlayerEntitiesList() throws NotReadyException {
        List<Player> players = new ArrayList<>();
        try {
            Object var0 = this.getPlayerEntities();
            List var7 = (List) var0;
            Object mySelf = null;
            List<Object> others = new ArrayList<>();
            List<Object> all = new ArrayList<>();
            for (Object var8 : var7) {
                Class<?> var9 = var8.getClass();
                String var10 = var9.getName();
                if (var10.equals(Data.ENTITY_OTHER_PLAYER_MP_CLASS.replace("/", "."))) {
                    String playerName = var9.getField("nameClear").get(var8).toString();
                    //                    double posX = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_POS_X_FIELD).get(var8);
                    //                    double posY = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_POS_Y_FIELD).get(var8);
                    //                    double posZ = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_POS_Z_FIELD).get(var8);
                    //                    double yaw = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_YAW_FIELD).get(var8);
                    //                    double pitch = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_PITCH_FIELD).get(var8);

                    double posX = (double) var9.getMethod("bridge$getPosX").invoke(var8);
                    double posY = (double) var9.getMethod("bridge$getPosY").invoke(var8);
                    double posZ = (double) var9.getMethod("bridge$getPosZ").invoke(var8);
                    double yaw = (double) var9.getMethod("bridge$getRotationYaw").invoke(var8);
                    double pitch = (double) var9.getMethod("bridge$getRotationPitch").invoke(var8);

    //                    System.out.println("Player: " + playerName + " Pos: " + posX + " " + posY + " " + posZ + " Yaw: " + yaw + " Pitch: " + pitch);
                    Player player = new Player(playerName, posX, posY, posZ, yaw, pitch);
                    players.add(player);
                    all.add(var8);
                    others.add(var8);
                } else {
                    Data.ENTITY_PLAYER_SP_CLASS = var10;
                    String playerName = var9.getField("nameClear").get(var8).toString();
                    double[] pos = this.getPlayerPos();
    //                    System.out.println("MySelf: " + playerName);
    //                    System.out.println("MySelf: " + playerName + " Pos: " + pos[0] + " " + pos[1] + " " + pos[2] + " Yaw: " + pos[3] + " Pitch: " + pos[4]);
                    Player player = new Player(playerName, pos[0], pos[1], pos[2], pos[3], pos[4]);
                    players.add(player);
                    all.add(var8);
                    mySelf = var8;
                }
            }
//            System.out.println("Found " + players.size() + " players");
        } catch (IllegalAccessException | NoSuchFieldException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return players;
    }

    public List<PlayerInfo> getPlayerEntitiesListInfo() throws NotReadyException {
        List<PlayerInfo> players = new ArrayList<>();
        try {
            Object var0 = this.getPlayerEntities();
            List var7 = (List) var0;
            Object mySelf = null;
            List<Object> others = new ArrayList<>();
            List<Object> all = new ArrayList<>();
            for (Object var8 : var7) {
                Class<?> var9 = var8.getClass();
                String var10 = var9.getName();
                if (var10.equals(Data.ENTITY_OTHER_PLAYER_MP_CLASS.replace("/", "."))) {
                    String playerName = var9.getField("nameClear").get(var8).toString();
    //                    double posX = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_POS_X_FIELD).get(var8);
    //                    double posY = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_POS_Y_FIELD).get(var8);
    //                    double posZ = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_POS_Z_FIELD).get(var8);
    //                    double yaw = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_YAW_FIELD).get(var8);
    //                    double pitch = (double) var9.getField(Data.ENTITY_OTHER_PLAYER_MP_PITCH_FIELD).get(var8);

                    /*double posX = (double) var9.getMethod("bridge$getPosX").invoke(var8);
                    double posY = (double) var9.getMethod("bridge$getPosY").invoke(var8);
                    double posZ = (double) var9.getMethod("bridge$getPosZ").invoke(var8);
                    double yaw = (double) var9.getMethod("bridge$getRotationYaw").invoke(var8);
                    double pitch = (double) var9.getMethod("bridge$getRotationPitch").invoke(var8);

    //                    System.out.println("Player: " + playerName + " Pos: " + posX + " " + posY + " " + posZ + " Yaw: " + yaw + " Pitch: " + pitch);
                    Player player = new Player(playerName, posX, posY, posZ, yaw, pitch);
                    players.add(player);*/
                    all.add(var8);
                    others.add(var8);
                } else {
                    Data.ENTITY_PLAYER_SP_CLASS = var10;
                    /*String playerName = var9.getField("nameClear").get(var8).toString();
                    double[] pos = this.getPlayerPos();
    //                    System.out.println("MySelf: " + playerName);
    //                    System.out.println("MySelf: " + playerName + " Pos: " + pos[0] + " " + pos[1] + " " + pos[2] + " Yaw: " + pos[3] + " Pitch: " + pos[4]);
                    Player player = new Player(playerName, pos[0], pos[1], pos[2], pos[3], pos[4]);
                    players.add(player);*/
                    all.add(var8);
                    mySelf = var8;
                }
            }

//            System.out.println("Found " + players.size() + " players");

            if (mySelf != null) {
                Class<?> var9 = mySelf.getClass();
                String playerName = var9.getField("nameClear").get(mySelf).toString();
                double[] pos = this.getPlayerPos();
                boolean isInvisible = (boolean) var9.getMethod("bridge$isInvisible").invoke(mySelf);
                //                    System.out.println("MySelf: " + playerName);
                //                    System.out.println("MySelf: " + playerName + " Pos: " + pos[0] + " " + pos[1] + " " + pos[2] + " Yaw: " + pos[3] + " Pitch: " + pos[4]);
                PlayerInfo player = new PlayerInfo(playerName, pos[0], pos[1], pos[2], pos[3], pos[4], isInvisible, isInvisible);
                players.add(player);
            }

            if (mySelf != null && others.size() > 0) {
                Class<?> var11 = others.get(0).getClass();
                Class<?> isInvisibleToParamClass = null;
                Method[] methods = var11.getMethods();
//                boolean found = false;
                for (Method m : methods) {
                        /*System.out.println("| METHOD: " + m.getName());
                        System.out.println("| METHOD PARAM: " + Arrays.toString(m.getParameterTypes()));*/
                    if (m.getName().equalsIgnoreCase("bridge$isInvisibleTo")) {
                        isInvisibleToParamClass = m.getParameterTypes()[0];
                    }
                        /*if (m.getName().equalsIgnoreCase("Invisible")) {
                            found = true;
                            System.out.println("Method: " + m.getName());
    //                        System.out.println("Param:");
    //                        System.out.println(Arrays.toString(m.getParameterTypes()));
                            *//*Parameter[] parameters = m.getParameters();
                            for (Parameter p : parameters) {
                                System.out.println("Param: " + p.getName() + " | " + p.getType().getName());
                            }*//*
                        }*/
                }
                /*if (!found) {
                    System.out.println("Method invis not found");
                }*/
                if (isInvisibleToParamClass != null) {
                    Method var12 = var11.getMethod("bridge$isInvisibleTo", isInvisibleToParamClass);
                    Method var12_1 = var11.getMethod("bridge$isInvisible");
                    for (Object var13 : others) {
                        boolean isInvisToMyself = (boolean) var12.invoke(var13, mySelf);
                        boolean isInvis = (boolean) var12_1.invoke(var13);
                        String nameClear = var13.getClass().getField("nameClear").get(var13).toString();
                        /*if (nameClear.isEmpty()) {
                            nameClear = "!Unknown!";
                        }*/
                        /*if (var14) {
                            System.out.println("Player: " + nameClear + " is invisible to me");
                        } else {
                            System.out.println("Player: " + nameClear + " is visible to me");
                        }*/
                        double posX = (double) var13.getClass().getMethod("bridge$getPosX").invoke(var13);
                        double posY = (double) var13.getClass().getMethod("bridge$getPosY").invoke(var13);
                        double posZ = (double) var13.getClass().getMethod("bridge$getPosZ").invoke(var13);
                        double yaw = (double) var13.getClass().getMethod("bridge$getRotationYaw").invoke(var13);
                        double pitch = (double) var13.getClass().getMethod("bridge$getRotationPitch").invoke(var13);

                        PlayerInfo player = new PlayerInfo(nameClear, posX, posY, posZ, yaw, pitch, isInvisToMyself, isInvis);
                        players.add(player);
                    }
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return players;
    }

    public void getPlayerInfoMap() throws NotReadyException {
        this.checkReady();

        try {
            Object var0 = this.getBridgeInterfaceObject();
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$getNetHandler");
            Object var3 = var2.invoke(var0);
            Class<?> var4 = var3.getClass();
            Method var5 = var4.getMethod("bridge$getPlayerInfoMap");
            Object var6 = var5.invoke(var3);
            List var7 = (List) var6;
            Iterator var8 = var7.iterator();
            while (var8.hasNext()) {
                Object var9 = var8.next();
                Debug.println("Player Map Class: " + var9.getClass().getName());
            }
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public String getCurrentScreen() throws NotReadyException {
        this.checkReady();

        try {
            Object var0 = this.getBridgeInterfaceObject();
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$getCurrentScreen");
            Object var3 = var2.invoke(var0);
            Class<?> var4 = var3.getClass();
            Method var5 = var4.getMethod("bridge$getScreenName");
            Object var6 = var5.invoke(var3);
            Optional<?> var7 = (Optional<?>) var6;
            if (var7.isPresent()) {
                Object var8 = var7.get();
                return (String) var8;
            } else {
                return null;
            }
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSelfName() throws NotReadyException {
        this.checkReady();

        try {
            Object var0 = this.getBridgeInterfaceObject();
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$getPlayer");
            Object var3 = var2.invoke(var0);
            Class<?> var4 = var3.getClass();
            Method getName = var4.getMethod("bridge$getName");
            return (String) getName.invoke(var3);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public double[] getPlayerPos() throws NotReadyException {
        this.checkReady();

        try {
            Object var0 = this.getBridgeInterfaceObject();
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$getPlayer");
            Object var3 = var2.invoke(var0);
            Class<?> var4 = var3.getClass();
            Method mX = var4.getMethod("bridge$getPosX");
            Method mY = var4.getMethod("bridge$getPosY");
            Method mZ = var4.getMethod("bridge$getPosZ");
            Method rY = var4.getMethod("bridge$getRotationYaw");
            Method rP = var4.getMethod("bridge$getRotationPitch");
            double x = (double) mX.invoke(var3);
            double y = (double) mY.invoke(var3);
            double z = (double) mZ.invoke(var3);
            double yaw = (double) rY.invoke(var3);
            double pitch = (double) rP.invoke(var3);
            return new double[] { x, y, z, yaw, pitch };
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getMinecraft() {
        this.checkReady();

        try {
            String minecraftGetMinecraftMethodName = Fields.MINECRAFT_GET_MINECRAFT_METHOD_NAME;
            Class<?> minecraftClassPatchName = ESP.getMinecraftClass();
            Method minecraftGetMinecraftMethod = minecraftClassPatchName.getMethod(minecraftGetMinecraftMethodName);
            return minecraftGetMinecraftMethod.invoke(null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public Object getCurrentServerData() {
        this.checkReady();

        try {
            Object var0 = this.getBridgeInterfaceObject();
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$getCurrentServerData");
            return var2.invoke(var0);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public String getCurrentServerIp() {
        try {
            Object var0 = this.getCurrentServerData();
            if (var0 == null) {
                return null;
            }
            Class<?> var1 = var0.getClass();
            Method var2 = var1.getMethod("bridge$serverIP");
            return (String) var2.invoke(var0);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkReady() throws NotReadyException {
        if (Data.TEXT_COMPONENT_CLASS == null || Data.LUNAR_FC_CLASS == null || Data.PRE_PRINT_CHAT_METHOD_IN_LUNAR_FC == null) {
            throw new NotReadyException("Classes and methods not ready yet\nPlease wait your game to finish loading");
        }
    }

    public void setPrintChatParam(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals("bridge$printChatMessage")) {
                this.printChatParam = method.getParameterTypes()[0];
            }
        }
    }

    public Object getBridgeInterfaceObject() {
        try {
//            Class<?> var0 = this.classLoader.loadClass(Data.LUNAR_FC_CLASS.replace("/", "."));
            /*Class<?> var0 = lunarFcClass;
            Method var1 = *//*var0.getMethod(Data.PRE_PRINT_CHAT_METHOD_IN_LUNAR_FC);*//* getBridgeMainInstanceMethod;*/
            return /*var1.invoke(null);*/ bridgeMainInstance;
        } catch (/*ClassNotFoundException | *//*NoSuchMethodException |*/ /*IllegalAccessException | InvocationTarget*/Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String targetClassName = "com.moonsworth.lunar.patcher.LunarMain";
        try {
            Class<?> clazz = Class.forName(targetClassName);
            clazz.getMethod("main", String[].class).invoke(null, (Object) mainArgs);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
