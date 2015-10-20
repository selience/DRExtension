package me.darkeet.android.json.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Name: AbstractSerializer
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/20 18:46
 * Desc: 增加json对文件读写操作
 */
public abstract class AbstractSerializer<GenericType> implements Serializer<GenericType> {

    public <T extends GenericType> T parseObject(final InputStream stream, final Class<T> cls) {
        try {
            final String string = JSONFileIO.readObject(stream);
            return parseObject(string, cls);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends GenericType> T[] parseArray(final InputStream stream, final Class<T> cls) {
        try {
            final String string = JSONFileIO.readObject(stream);
            return parseArray(string, cls);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends GenericType> List<T> parseList(final InputStream stream, final Class<T> cls) {
        try {
            final String string = JSONFileIO.readObject(stream);
            return parseList(string, cls);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T extends GenericType> boolean toFile(final OutputStream stream, final T object, final Class<T> cls) {
        try {
            final String string = toJsonString(object, cls);
            JSONFileIO.writeObject(stream, string);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T extends GenericType> boolean toFile(final OutputStream stream, final T[] object, final Class<T> cls) {
        try {
            final String string = toJsonString(object, cls);
            JSONFileIO.writeObject(stream, string);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T extends GenericType> boolean toFile(final OutputStream stream, final List<T> object, final Class<T> cls) {
        try {
            final String string = toJsonString(object, cls);
            JSONFileIO.writeObject(stream, string);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
