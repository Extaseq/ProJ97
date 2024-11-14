package com.nichga.proj97;

public enum Permission {
    READ(1),
    WRITE(2),
    DELETE(4),
    UPDATE(8),
    ALL(15);

    private final int permission;

    private Permission(int permission) {
        this.permission = permission;
    }

    public int getPermission() {
        return permission;
    }

    public static boolean hasPermission(int userPermission, Permission p) {
        return (userPermission & p.getPermission()) != 0;
    }

    public static void addPermission(int userPermission, Permission p) {
        userPermission |= p.getPermission();
    }

    public static void removePermission(int userPermission, Permission p) {
        userPermission &= ~p.getPermission();
    }
}
