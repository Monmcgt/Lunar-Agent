package me.monmcgt.code.bedwarsstatslunarinject.apis.responses;

import java.util.Arrays;

public class PlayerDB$Response {
    public String code;
    public String message;
    public Data data;
    public boolean success;

    @Override
    public String toString() {
        return "PlayerDB$Response{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }

    public static class Data {
        public Player player;

        @Override
        public String toString() {
            return "Data{" +
                    "player=" + player +
                    '}';
        }

        public static class Player {
            public Meta meta;
            public String username;
            public String id;

            @Override
            public String toString() {
                return "Data{" +
                        "meta=" + meta +
                        ", username='" + username + '\'' +
                        ", id='" + id + '\'' +
                        '}';
            }

            public static class Meta {
                public NameHistory[] name_history;

                @Override
                public String toString() {
                    return "Meta{" +
                            "name_history=" + Arrays.toString(name_history) +
                            '}';
                }

                public static class NameHistory {
                    public String name;
                    public String changedToAt;

                    @Override
                    public String toString() {
                        return "NameHistory{" +
                                "name='" + name + '\'' +
                                ", changedToAt='" + changedToAt + '\'' +
                                '}';
                    }
                }
            }
        }
    }
}
