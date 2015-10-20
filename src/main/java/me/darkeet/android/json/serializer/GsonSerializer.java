package me.darkeet.android.json.serializer;

import java.util.List;
import com.google.gson.Gson;
import java.lang.reflect.Array;
import android.support.annotation.Nullable;
import me.darkeet.android.json.gson.GenericGsonType;

/**
 * Name: GsonSerializer
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/20 16:38
 * Desc: 用于处理Gson序列化与反序列化
 */
public class GsonSerializer extends AbstractSerializer<Object> {

    @Nullable
    @Override
    public <T> String toJsonString(@Nullable T object, Class<T> cls) {
        if (object == null) return null;
        return new Gson().toJson(object);
    }

    @Nullable
    @Override
    public <T> String toJsonString(@Nullable T[] object, Class<T> cls) {
        if (object == null) return null;
        return new Gson().toJson(object);
    }

    @Nullable
    @Override
    public <T> String toJsonString(@Nullable List<T> object, Class<T> cls) {
        if (object == null) return null;
        return new Gson().toJson(object);
    }

    @Nullable
    @Override
    public <T> T parseObject(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        return new Gson().fromJson(string, cls);
    }

    @Nullable
    @Override
    public <T> T[] parseArray(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        final List<T> list = parseList(string, cls);
        return list.toArray((T[]) Array.newInstance(cls, list.size()));
    }

    @Nullable
    @Override
    public <T> List<T> parseList(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        return new Gson().fromJson(string, new GenericGsonType<List, T>(List.class, cls));
    }
}
