package me.darkeet.android.utils;

import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import me.darkeet.android.log.DebugLog;

/**
 * Name: EncryptUtils
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/29 12:22
 * Desc: 提供各种数据加密方法
 */
public class EncryptUtils {

    private EncryptUtils() {
    }

    /**
     * 提供md5加密
     *
     * @param key
     * @return
     */
    public static String md5ForString(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToMd5String(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }

        return cacheKey;
    }

    private static String bytesToMd5String(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            final int b = bytes[i] & 255;
            if (b < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(b));
        }
        return sb.toString();
    }

    /**
     * 提供Hash加密
     *
     * @param key
     * @return
     */
    public static String hashKeyForString(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }

        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 提供AES加密
     *
     * @param input
     * @param key
     * @return
     */
    public static String encrypt(String input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            DebugLog.e("encrypt", "encrypt failed", e);
        }
        return new String(Base64.encode(crypted, Base64.DEFAULT));
    }

    /**
     * 提供AES解密
     *
     * @param input
     * @param key
     * @return
     */
    public static String decrypt(String input, String key) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decode(input, Base64.DEFAULT));
        } catch (Exception e) {
            DebugLog.e("decrypt", "decrypt failed", e);
        }
        return new String(output);
    }
}
