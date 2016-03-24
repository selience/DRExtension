package me.darkeet.android.json.serializer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import me.darkeet.android.util.IoUtils;

/**
 * Name: JSONFileIO
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/20 17:48
 * Desc:文件里读写操作
 */
public class JSONFileIO {

    public static String readObject(final File file) {
        try {
            return readObject(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writeObject(final File file, final String json) {
        try {
            writeObject(new FileOutputStream(file), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readObject(final InputStream stream) throws IOException {
        if (stream == null) throw new FileNotFoundException();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.defaultCharset()));
        final StringBuffer buf = new StringBuffer();
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
                buf.append('\n');
            }
            reader.close();
            return buf.toString();
        } finally {
            IoUtils.closeSilently(stream);
        }
    }

    public static void writeObject(final OutputStream stream, final String json)
            throws IOException {
        if (stream == null || json == null) return;
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, Charset.defaultCharset()));
        try {
            writer.write(json);
            writer.flush();
        } finally {
            IoUtils.closeSilently(writer);
        }
    }
}
