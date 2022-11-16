package com.bitsco.vks.common.crypt;

import com.bitsco.vks.common.constant.Constant;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 22-May-18
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class MD5 {
    public static byte[] encrypt(byte[] value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(Constant.CRYPT.MD5.ALGORITHM);
        md.update(value);
        return md.digest();
    }

    public static byte[] byte2Hex(byte[] input) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        for (byte anInput : input) {
            bOut.write(Constant.CRYPT.MD5.HEX_ARRAY[anInput >>> 4 & 0x0F]);
            bOut.write(Constant.CRYPT.MD5.HEX_ARRAY[anInput & 0x0F]);
        }
        return bOut.toByteArray();
    }

    public static String byte2HexString(byte[] input) {
        return new String(byte2Hex(input));
    }

    public static byte[] hexString2Byte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static boolean matched(String src, String dest) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return Arrays.equals(hexString2Byte(dest), encrypt(src.getBytes(StandardCharsets.UTF_8)));
    }

    public static String encrypt(String val) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return byte2HexString(encrypt(val.getBytes(StandardCharsets.UTF_8)));
    }

    public static boolean isVerified(String src, String val) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return encrypt(src).equals(val);
    }
}
