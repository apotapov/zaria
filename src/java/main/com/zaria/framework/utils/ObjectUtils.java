package com.zaria.framework.utils;

public class ObjectUtils {

    public static boolean safeEquals(final Object object1, final Object object2) {
        if (object1 == object2) {
            return true;
        }
        if (object1 == null || object2 == null) {
            return false;
        }
        return object1.equals(object2);
    }

    public static int safeHashCode(final Object... objects) {
        int hash = 1;
        if (objects != null) {
            int len = objects.length;
            for (int i = 0; i < len; i++) {
                Object object = objects[i];
                hash = hash * 31 + (object == null ? 0 : object.hashCode());
            }
        }
        return hash;
    }

}
