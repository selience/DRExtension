package me.darkeet.android.json.serializer;

import java.util.List;
import android.support.annotation.Nullable;

/**
 * Name: JsonSerializer
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/20 11:25
 * Desc: Json序列化和反序列化操作
 */
public interface Serializer<GenericType> {

    @Nullable
    public <T extends GenericType> String toJsonString(@Nullable final T object, final Class<T> cls);

    @Nullable
    public <T extends GenericType> String toJsonString(@Nullable final T[] object, final Class<T> cls);

    @Nullable
    public <T extends GenericType> String toJsonString(@Nullable final List<T> object, final Class<T> cls);

    @Nullable
    public <T extends GenericType> T parseObject(@Nullable final String string, final Class<T> cls);

    @Nullable
    public <T extends GenericType> T[] parseArray(@Nullable final String string, final Class<T> cls);

    @Nullable
    public <T extends GenericType> List<T> parseList(@Nullable final String string, final Class<T> cls);
}
