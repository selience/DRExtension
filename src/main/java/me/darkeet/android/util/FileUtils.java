package me.darkeet.android.util;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Jacky.Lee <loveselience@gmail.com>
 * @file FileUtils.java
 * @create 2013-7-17 下午05:19:08
 * @description TODO 封装常用的文件操作
 */
public final class FileUtils {

    /**
     * The number of bytes in a megabyte.
     */
    private static final long ONE_MB = 1048576;

    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = ONE_MB * 30;

    /**
     * 创建文件夹
     *
     * @param destFile
     * @return 文件是否创建成功
     */
    public static boolean createFile(File destFile) {
        if (destFile != null && !destFile.exists()) {
            return destFile.mkdirs();
        }
        return false;
    }

    /**
     * 创建文件
     *
     * @param folderPath
     * @param fileName
     * @return
     */
    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return new File(folderPath, fileName + fileName);
    }

    /**
     * 文件重命名
     *
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean renameFile(String oldName, String newName) {
        File f = new File(oldName);
        return f.renameTo(new File(newName));
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileNameWithExtension(String filePath) {
        if (Utils.isEmpty(filePath)) return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 根据文件的绝对路径获取文件名但不包含扩展名
     *
     * @param filePath
     * @return
     */
    public static String getFileNameNoExtension(String filePath) {
        if (Utils.isEmpty(filePath)) return "";
        int point = filePath.lastIndexOf('.');
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1, point);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
        if (Utils.isEmpty(fileName)) return "";
        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * 列出root目录下所有子目录
     *
     * @param root
     * @return 绝对路径
     */
    public static List<String> listPath(String root) {
        List<String> allDir = new ArrayList<String>();
        SecurityManager checker = new SecurityManager();
        File path = new File(root);
        checker.checkRead(root);
        // 过滤掉以.开始的文件夹
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory() && !f.getName().startsWith(".")) {
                    allDir.add(f.getAbsolutePath());
                }
            }
        }
        return allDir;
    }

    /**
     * 获取一个文件夹下的所有文件
     *
     * @param root
     * @return
     */
    public static List<File> listPathFiles(String root) {
        List<File> allDir = new ArrayList<File>();
        SecurityManager checker = new SecurityManager();
        File path = new File(root);
        checker.checkRead(root);
        File[] files = path.listFiles();
        for (File f : files) {
            if (f.isFile())
                allDir.add(f);
            else
                listPath(f.getAbsolutePath());
        }
        return allDir;
    }

    /**
     * 删除除指定文件之外的所有文件
     *
     * @param appCachePath     被删除的文件夹目录
     * @param excludedFileName 文件夹第一级目录下，不想被删除的文件的名称
     */
    public static void cleanCacheFile(final String appCachePath,
                                      final String excludedFileName) {
        try {
            File file = new File(appCachePath.trim());
            if (null != file && file.isDirectory()) {
                for (File file1 : file.listFiles()) {
                    if (file1.isDirectory()) {
                        deleteAllFile(file1.getAbsolutePath());
                    } else {
                        if (!excludedFileName.equals(file1.getName())) {
                            file1.delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有文件
     */
    public static void deleteAllFile(String path) {
        File file = new File(path);
        if (null != file && file.exists()) {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    String filePath = fileList[i].getPath();
                    deleteAllFile(filePath);
                }
            } else if (file.isFile()) {
                file.delete();
            }
        }
    }

    /**
     * 写文件到指定路径
     *
     * @param stream
     * @param destFile
     * @return
     */
    public static boolean writeFile(InputStream stream, File destFile) {
        if (destFile.exists() && destFile.isDirectory())
            throw new IllegalArgumentException("Destination '" + destFile + "' exists but is a directory");

        FileOutputStream fos = null;
        BufferedInputStream bis = null;

        try {
            bis = new BufferedInputStream(stream);
            fos = new FileOutputStream(destFile);

            int bytesRead;
            byte[] buffer = new byte[1024 * 4];
            while ((bytesRead = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fos);
            closeQuietly(bis);
            closeQuietly(stream);
        }

        return false;
    }

    /**
     * 获取文件夹或者文件大小，递归循环
     *
     * @param String path 路径或者文件
     * @return String 文件的大小，以BKMG来计量
     */
    public static String getPathSize(String path) {
        String flieSizesString = "";
        File file = new File(path.trim());
        long fileSizes = 0;
        if (null != file && file.exists()) {
            if (file.isDirectory()) { // 如果路径是文件夹的时候
                fileSizes = getFileFolderTotalSize(file);
            } else if (file.isFile()) {
                fileSizes = file.length();
            }
        }
        flieSizesString = formatFileSizeToString(fileSizes);
        return flieSizesString;
    }

    /**
     * 获取文件夹或者文件大小，循环迭代
     *
     * @param fileDir path 路径或者文件
     * @return String 文件的大小，以BKMG来计量
     */
    private static long getFileFolderTotalSize(File fileDir) {
        long totalSize = 0;
        File fileList[] = fileDir.listFiles();
        for (int fileIndex = 0; fileIndex < fileList.length; fileIndex++) {
            if (fileList[fileIndex].isDirectory()) {
                totalSize = totalSize
                        + getFileFolderTotalSize(fileList[fileIndex]);
            } else {
                totalSize = totalSize + fileList[fileIndex].length();
            }
        }
        return totalSize;
    }

    /**
     * 格式化文件长度大小
     */
    private static String formatFileSizeToString(long fileSize) {// 转换文件大小
        String fileSizeString = "";
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (fileSize == 0) {
            fileSizeString = "0KB";
        } else if (fileSize < 1024) {
            fileSizeString = decimalFormat.format((double) fileSize) + "B";
        } else if (fileSize < (1 * 1024 * 1024)) {
            fileSizeString = decimalFormat.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < (1 * 1024 * 1024 * 1024)) {
            fileSizeString = decimalFormat.format((double) fileSize / (1 * 1024 * 1024)) + "MB";
        } else {
            fileSizeString = decimalFormat.format((double) fileSize / (1 * 1024 * 1024 * 1024)) + "GB";
        }
        return fileSizeString;
    }

    /**
     * Copies a file to a new location preserving the file date.
     *
     * @param srcFile  an existing file to copy, must not be {@code null}
     * @param destFile the new file, must not be {@code null}
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException          if source or destination is invalid
     * @throws IOException          if an IO error occurs during copying
     * @see #copyFileToDirectory(File, File)
     */
    public static void copyFile(final File srcFile, final File destFile) throws IOException {
        if (srcFile == null) throw new NullPointerException("Source must not be null");
        if (destFile == null) throw new NullPointerException("Destination must not be null");
        if (srcFile.exists() == false)
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        if (srcFile.isDirectory())
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath()))
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        final File parentFile = destFile.getParentFile();
        if (parentFile != null) {
            if (!parentFile.mkdirs() && !parentFile.isDirectory())
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
        }
        if (destFile.exists() && destFile.canWrite() == false)
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        doCopyFile(srcFile, destFile, true);
    }

    /**
     * Copies a file to a directory preserving the file date.
     *
     * @param srcFile an existing file to copy, must not be {@code null}
     * @param destDir the directory to place the copy in, must not be {@code null}
     * @throws NullPointerException if source or destination is null
     * @throws IOException          if source or destination is invalid
     * @throws IOException          if an IO error occurs during copying
     * @see #copyFile(File, File, boolean)
     */
    public static void copyFileToDirectory(final File srcFile, final File destDir) throws IOException {
        if (destDir == null) throw new NullPointerException("Destination must not be null");
        if (destDir.exists() && destDir.isDirectory() == false)
            throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
        final File destFile = new File(destDir, srcFile.getName());
        copyFile(srcFile, destFile);
    }

    /**
     * Internal copy file method.
     *
     * @param srcFile          the validated source file, must not be {@code null}
     * @param destFile         the validated destination file, must not be {@code null}
     * @param preserveFileDate whether to preserve the file date
     * @throws IOException if an error occurs
     */
    private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate)
            throws IOException {
        if (destFile.exists() && destFile.isDirectory())
            throw new IOException("Destination '" + destFile + "' exists but is a directory");

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input = fis.getChannel();
            output = fos.getChannel();
            final long size = input.size();
            long pos = 0;
            long count = 0;
            while (pos < size) {
                count = size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : size - pos;
                pos += output.transferFrom(input, pos, count);
            }
        } finally {
            closeQuietly(output);
            closeQuietly(fos);
            closeQuietly(input);
            closeQuietly(fis);
        }

        if (srcFile.length() != destFile.length())
            throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }

    /**
     * Unconditionally close a <code>Closeable</code>.
     */
    private static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
        }
    }
}
