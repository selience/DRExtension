package me.darkeet.android.json.compat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JSONParse {

    public static <T extends JSONParcelable> T parseObject(final String string, final String clsName) {
        try {
            final JSONObject json = new JSONObject(string);
            final JSONParcelable.Creator<T> creator = getCreator(clsName);
            return createObject(creator, json);
        } catch (final JSONException e) {
        }
        return null;
    }

    public static <T extends JSONParcelable> T[] parseArray(final String string, final String clsName) {
        try {
            final JSONArray json = new JSONArray(string);
            final JSONParcelable.Creator<T> creator = getCreator(clsName);
            return createArray(creator, json);
        } catch (final JSONException e) {
        }
        return null;
    }

    public static <T extends JSONParcelable> List<T> parseList(final String string, final String clsName) {
        try {
            final JSONArray json = new JSONArray(string);
            final JSONParcelable.Creator<T> creator = getCreator(clsName);
            return createArrayList(creator, json);
        } catch (final JSONException e) {
        }
        return null;
    }


    public static <T extends JSONParcelable> String toJSONString(final T parcelable) {
        return toJsonString(toJSONObject(parcelable));
    }

    public static <T extends JSONParcelable> String toJSONString(final T[] array) {
        return toJsonString(toJSONArray(array));
    }

    public static <T extends JSONParcelable> String toJSONString(final List<T> list) {
        return toJsonString(toJSONArray(list));
    }


    public static <T extends JSONParcelable> JSONObject toJSONObject(final T parcelable) {
        if (parcelable == null) return null;
        final JSONObject json = new JSONObject();
        parcelable.writeToParcel(new JSONParcel(json));
        return json;
    }

    public static <T extends JSONParcelable> JSONArray toJSONArray(final T[] array) {
        if (array == null) return null;
        final JSONArray json = new JSONArray();
        for (final T parcelable : array) {
            json.put(toJSONObject(parcelable));
        }
        return json;
    }

    public static <T extends JSONParcelable> JSONArray toJSONArray(final List<T> list) {
        if (list == null) return null;
        final JSONArray json = new JSONArray();
        for (final T parcelable : list) {
            json.put(toJSONObject(parcelable));
        }
        return json;
    }


    public static <T extends JSONParcelable> T createObject(final JSONParcelable.Creator<T> creator,
                                                            final JSONObject json) {
        if (creator == null) throw new NullPointerException("JSON_CREATOR must not be null!");
        if (json == null) return null;
        return creator.createFromParcel(new JSONParcel(json));
    }

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

    private static <T extends JSONParcelable> JSONParcelable.Creator<T> getCreator(final String name) {
        try {
            final Class<?> cls = Class.forName(name);
            return (JSONParcelable.Creator<T>) cls.getField("JSON_CREATOR").get(null);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static String toJsonString(final JSONArray json) {
        if (json == null)
            return null;
        return json.toString();
    }

    private static String toJsonString(final JSONObject json) {
        if (json == null)
            return null;
        return json.toString();
    }
}
