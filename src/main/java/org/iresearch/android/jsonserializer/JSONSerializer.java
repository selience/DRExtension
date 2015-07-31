package org.iresearch.android.jsonserializer;

import android.os.Build;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.nio.charset.Charset;
import android.annotation.TargetApi;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class JSONSerializer {
    private static boolean debugMode = false;

    public static <T extends JSONParcelable> T[] createArray(final JSONParcelable.Creator<T> creator,
            final JSONArray json) {
        if (creator == null) throw new NullPointerException("JSON_CREATOR must not be null!");
        if (json == null) return null;
        final int size = json.length();
        final T[] array = creator.newArray(size);
        for (int i = 0; i < size; i++) {
            array[i] = creator.createFromParcel(new JSONParcel(json.optJSONObject(i)));
        }
        return array;
    }

    public static <T extends JSONParcelable> ArrayList<T> createArrayList(final JSONParcelable.Creator<T> creator,
            final JSONArray json) {
        if (creator == null) throw new NullPointerException("JSON_CREATOR must not be null!");
        if (json == null) return null;
        final int size = json.length();
        final ArrayList<T> list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            list.add(creator.createFromParcel(new JSONParcel(json.optJSONObject(i))));
        }
        return list;
    }

    public static <T extends JSONParcelable> T createObject(final JSONParcelable.Creator<T> creator,
            final JSONObject json) {
        if (creator == null) throw new NullPointerException("JSON_CREATOR must not be null!");
        if (json == null) return null;
        return creator.createFromParcel(new JSONParcel(json));
    }

    public static <T extends JSONParcelable> byte[] getByteArray(final T parcelable) {
        final JSONObject json = toJSONObject(parcelable);
        final String string = jsonToString(json);
        if (string == null) return null;
        return string.getBytes(Charset.defaultCharset());
    }

    public static <T extends JSONParcelable> byte[] getByteArray(final T[] array) {
        final JSONArray json = toJSONArray(array);
        final String string = jsonToString(json);
        if (string == null) return null;
        return string.getBytes(Charset.defaultCharset());
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setIsDebugMode(final boolean debug) {
        debugMode = debug;
    }

    public static <T extends JSONParcelable> byte[] toByteArray(final T parcelable) throws IOException {
        final String jsonString = jsonToString(toJSONObject(parcelable));
        if (jsonString == null) return null;
        return jsonString.getBytes(Charset.defaultCharset());
    }

    public static <T extends JSONParcelable> byte[] toByteArray(final T[] array) throws IOException {
        final String jsonString = jsonToString(toJSONArray(array));
        if (jsonString == null) return null;
        return jsonString.getBytes(Charset.defaultCharset());
    }

    public static <T extends JSONParcelable> JSONArray toJSONArray(final T[] array) {
        if (array == null) return null;
        final JSONArray json = new JSONArray();
        for (final T parcelable : array) {
            json.put(toJSONObject(parcelable));
        }
        return json;
    }

    public static <T extends JSONParcelable> String toJSONArrayString(final T[] array) {
        return jsonToString(toJSONArray(array));
    }

    public static <T extends JSONParcelable> JSONObject toJSONObject(final T parcelable) {
        if (parcelable == null) return null;
        final JSONObject json = new JSONObject();
        parcelable.writeToParcel(new JSONParcel(json));
        return json;
    }

    public static <T extends JSONParcelable> String toJSONObjectString(final T parcelable) {
        return jsonToString(toJSONObject(parcelable));
    }

    static String jsonToString(final JSONArray json) {
        if (json == null) return null;
        if (debugMode) {
            try {
                return json.toString(4);
            } catch (final JSONException e) {
                e.printStackTrace();
            }
            return json.toString();
        } else
            return json.toString();
    }

    static String jsonToString(final JSONObject json) {
        if (json == null) return null;
        if (debugMode) {
            try {
                return json.toString(4);
            } catch (final JSONException e) {
                e.printStackTrace();
            }
            return json.toString();
        } else
            return json.toString();
    }

}
