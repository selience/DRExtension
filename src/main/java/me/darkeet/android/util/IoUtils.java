
package me.darkeet.android.util;

import java.io.Reader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

/**
 * 提供IO流相关操作
 */
public class IoUtils {
    private static final int BUFFER_SIZE = 8192;


    /**
     * 读取所有的输入流{@link InputStream}到字节数组
     *
     * @param in 读取的输入流{@link InputStream}
     * @return 返回读取的字节数组
     */
    public static byte[] readAllBytes(InputStream in) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            copy(in, out);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(out);
            closeSilently(in);
        }
        return null;
    }

    /**
     * 从读取器{@link Reader}读取内容到字符串{@link String}
     *
     * @param reader 需要读取内容的读取器对象{@link Reader}
     * @return 返回读取的内容
     */
    public static String readAllChars(Reader reader) {
        try {
            char[] buffer = new char[BUFFER_SIZE];
            int read;
            StringBuilder builder = new StringBuilder();
            while ((read = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(reader);
        }
        return null;
    }

    /**
     * 读取输入流{@link InputStream}内容到字符串{@link String}，编码默认UTF-8；
     * 注意：读取过程中输入流{@link InputStream}不能关闭；
     *
     * @param in 读取的输入流{@link InputStream}
     * @return 返回输入流的内容
     */
    public static String readFully(InputStream in) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, read, "UTF-8"));
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(in);
        }
        return null;
    }

    /**
     * 拷贝输入流{@link InputStream}内容到输出流{@link OutputStream}，注意：输入流和输出流不能关闭；
     *
     * @param in  读取的输入流{@link InputStream}
     * @param out 写入的输出流{@link OutputStream}
     * @return 返回读取的字节大小
     * @throws IOException 读取或写入过程发生异常抛出
     */
    public static long copy(InputStream in, OutputStream out) throws IOException {
        long res = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
            out.flush();
            res += read;
        }
        return res;
    }

    /**
     * 关闭所有输入流{@link InputStream}或输出流{@link OutputStream}对象
     */
    public static void closeSilently(Closeable... toClose) {
        for (Closeable closeable : toClose) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
