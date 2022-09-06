package com.holub.kyle.util;

import java.util.Map;

public class MapUtils {
    public static boolean isNotNullOrEmptyMap(Map <? , ?> map) {
        return !isNullOrEmptyMap(map);
    }

    public static boolean isNullOrEmptyMap(Map<? , ?> map) {
        return (map == null || map.isEmpty());
    }
}
