package me.darkeet.android.json.serializer;

import java.util.List;
import java.util.Arrays;
import java.io.IOException;
import java.lang.reflect.Array;
import android.support.annotation.Nullable;
import com.bluelinelabs.logansquare.LoganSquare;

/**
 * Name: LoganSerializer
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/20 11:31
 * Desc:
 */
public class LoganSquareSerializer extends AbstractSerializer<Object> {

    @Nullable
    @Override
    public <T> String toJsonString(@Nullable T object, Class<T> cls) {
        if (object == null) return null;
        try {
            return LoganSquare.mapperFor(cls).serialize(object);
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public <T> String toJsonString(@Nullable T[] object, Class<T> cls) {
        if (object == null) return null;
        try {
            return LoganSquare.mapperFor(cls).serialize(Arrays.asList(object));
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public <T> String toJsonString(@Nullable List<T> list, Class<T> cls) {
        if (list == null) return null;
        try {
            return LoganSquare.serialize(list, cls);
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public <T> T parseObject(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        try {
            return LoganSquare.mapperFor(cls).parse(string);
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public <T> T[] parseArray(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        try {
            final List<T> list = LoganSquare.mapperFor(cls).parseList(string);
            //noinspection unchecked
            return list.toArray((T[]) Array.newInstance(cls, list.size()));
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    @Override
    public <T> List<T> parseList(@Nullable String string, Class<T> cls) {
        if (string == null) return null;
        try {
            return LoganSquare.mapperFor(cls).parseList(string);
        } catch (IOException e) {
            return null;
        }
    }
}
