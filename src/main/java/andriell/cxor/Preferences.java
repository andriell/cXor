package andriell.cxor;


import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class Preferences {
    public static String LAST_USED_DIMENSION = "last_used_dimension";
    public static String LAST_USED_FOLDER_DATA = "last_used_folder_data";
    public static String LAST_USED_FOLDER_KEY = "last_used_folder_key";
    public static String LAST_USED_FOLDER_SAVE = "last_used_folder_save";

    private static java.util.prefs.Preferences prefs;

    static {
        prefs = java.util.prefs.Preferences.userNodeForPackage(Main.class);
    }
    public static void put(String key, String value)
    {
        prefs.put(key, value);
    }

    public static void putSerializable(String key, Serializable value)
    {
        prefs.putByteArray(key, SerializationUtils.serialize(value));
    }

    public static String get(String key, String def)
    {
        return prefs.get(key, def);
    }

    public static Serializable getSerializable(String key, Serializable def)
    {
        prefs.getByteArray(key, null);
        if (prefs.getByteArray(key, null) == null) {
            return def;
        }
        return SerializationUtils.deserialize(prefs.getByteArray(key, null));
    }
}
