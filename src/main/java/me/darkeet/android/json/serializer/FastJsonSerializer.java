package me.darkeet.android.json.serializer;

import java.util.List;
import java.lang.reflect.Array;
import com.alibaba.fastjson.JSON;
import android.support.annotation.Nullable;

/**
 * Name: JsonSerialzer
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/20 11:44
 * Desc: 用于处理fastJson序列化与反序列化
 */
public class FastJsonSerializer extends AbstractSerializer<Object> {

    @Nullable
    @Override
    public <T> String toJsonString(@Nullable T object, Class<T> cls) {
        if (object == null) return null;
        return JSON.toJSONString(object);
    }

    @Nullable
    @Override
    public <T> String toJsonString(@Nullable T[] object, Class<T> cls) {
        if (object == null) return null;
        return JSON.toJSONString(object);
    }

    @Nullable
    @Override
    public <T> String toJsonString(@Nullable List<T> list, Class<T> cls) {
        if (list == null) return null;
        return JSON.toJSONString(list);
    }

    @Nullable
    @Override
    public <T> T parseObject(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        return JSON.parseObject(string, cls);
    }

    @Nullable
    @Override
    public <T> T[] parseArray(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        final List<T> list = JSON.parseArray(string, cls);
        return list.toArray((T[]) Array.newInstance(cls, list.size()));
    }

    @Nullable
    @Override
    public <T> List<T> parseList(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        return JSON.parseArray(string, cls);
    }
}
