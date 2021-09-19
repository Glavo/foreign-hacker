package org.glavo.foreign.hacker;

import sun.misc.Unsafe;

import java.io.PrintWriter;
import java.lang.module.ModuleDescriptor;
import java.lang.reflect.Field;

public final class ForeignHacker {
    private ForeignHacker() {
    }

    private static class ModuleLike {
        private ModuleLayer layer;
        private String name;
        private ClassLoader loader;
        private ModuleDescriptor descriptor;
        private volatile boolean enableNativeAccess;
    }

    private static final Unsafe unsafe;

    static {
        Unsafe u;
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            u = (Unsafe) theUnsafe.get(null);
        } catch (Throwable throwable) {
            u = null;
        }
        unsafe = u;
    }

    public static boolean enableForeignAccess(Module module) {
        return enableForeignAccess(module, null);
    }

    public static boolean enableForeignAccess(Module module, PrintWriter errorHandle) {
        if (unsafe == null) {
            return false;
        }
        try {
            if (Runtime.version().feature() == 16) {
                final Field foreignRestrictedAccess =
                        Class.forName("jdk.internal.foreign.Utils").getDeclaredField("foreignRestrictedAccess");
                unsafe.putObject(
                        unsafe.staticFieldBase(foreignRestrictedAccess),
                        unsafe.staticFieldOffset(foreignRestrictedAccess),
                        "permit"
                );
            } else if (Runtime.version().feature() >= 17) {
                unsafe.putBoolean(
                        module,
                        unsafe.objectFieldOffset(ModuleLike.class.getDeclaredField("enableNativeAccess")),
                        true
                );
            }
            return true;
        } catch (Throwable ex) {
            if (errorHandle != null) {
                ex.printStackTrace(errorHandle);
            }
            return false;
        }
    }
}
