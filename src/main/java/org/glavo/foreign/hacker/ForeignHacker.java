package org.glavo.foreign.hacker;

import sun.misc.Unsafe;

import java.io.PrintWriter;
import java.lang.reflect.Field;

public final class ForeignHacker {
    private ForeignHacker() {
    }

    public static boolean enableForeignAccess() {
        return enableForeignAccess(null);
    }

    public static boolean enableForeignAccess(PrintWriter errorHandle) {
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            final Unsafe unsafe = (Unsafe) theUnsafe.get(null);
            final Field foreignRestrictedAccess =
                    Class.forName("jdk.internal.foreign.Utils").getDeclaredField("foreignRestrictedAccess");
            unsafe.putObject(
                    unsafe.staticFieldBase(foreignRestrictedAccess),
                    unsafe.staticFieldOffset(foreignRestrictedAccess),
                    "permit"
            );
            return true;
        } catch (Throwable ex) {
            if (errorHandle != null) {
                ex.printStackTrace(errorHandle);
            }
            return false;
        }
    }
}
