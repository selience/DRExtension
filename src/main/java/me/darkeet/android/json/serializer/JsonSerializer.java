package me.darkeet.android.json.serializer;

import java.util.List;
import android.support.annotation.Nullable;
import me.darkeet.android.json.compat.JSONParcelable;
import me.darkeet.android.json.compat.JSONParse;

/**
 * Name: JsonSerializer
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/20 14:07
 * Desc: 用于处理json序列化与反序列化
 */
public class JsonSerializer extends AbstractSerializer<JSONParcelable> {

    @Nullable
    @Override
    public <T extends JSONParcelable> String toJsonString(@Nullable T object, Class<T> cls) {
        if (object == null) return null;
        return JSONParse.toJSONString(object);
    }

    @Nullable
    @Override
    public <T extends JSONParcelable> String toJsonString(@Nullable T[] object, Class<T> cls) {
        if (object == null) return null;
        return JSONParse.toJSONString(object);
    }

    @Nullable
    @Override
    public <T extends JSONParcelable> String toJsonString(@Nullable List<T> object, Class<T> cls) {
        if (object == null) return null;
        return JSONParse.toJSONString(object);
    }

    @Nullable
    @Override
    public <T extends JSONParcelable> T parseObject(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        return JSONParse.parseObject(string, cls.getName());
    }

    @Nullable
    @Override
    public <T extends JSONParcelable> T[] parseArray(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        return JSONParse.parseArray(string, cls.getName());
    }

    @Nullable
    @Override
    public <T extends JSONParcelable> List parseList(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        return JSONParse.parseList(string, cls.getName());
    }
}
