package com.sjkz1.showkeybinds.utils;

import java.util.HashMap;
import java.util.Map;

public class WordUtils
{
    private static final Map<String, String> ABBR_MAP = new HashMap<>();

    static
    {
        ABBR_MAP.put("left control", "lft ctrl");
        ABBR_MAP.put("left shift", "lft shft");
        ABBR_MAP.put("right control", "rgh ctrl");
        ABBR_MAP.put("right shift", "rgh shft");
        ABBR_MAP.put("enter", "ent");
        ABBR_MAP.put("delete", "del");
        ABBR_MAP.put("escape", "esc");
    }

    public static String convert(String word)
    {
        if (word == null) return null;
        return ABBR_MAP.getOrDefault(word.toLowerCase(), word.toLowerCase());
    }

    public static String capitalize(String word)
    {
        if (word == null || word.isEmpty()) return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }
}
