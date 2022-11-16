package com.bitsco.vks.common.crypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 22-May-18
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class Base64 {
    public static String toStringBase64(byte[] byteArray) {
        return (new BASE64Encoder().encodeBuffer(byteArray)).replaceAll(String.valueOf((char) 0xd), "");
    }

    public static byte[] toByteArrayBase64(String string) throws IOException {
        return new BASE64Decoder().decodeBuffer(string);
    }

    public static String decodeBase64(String encode) throws IOException {
        return new String(new BASE64Decoder().decodeBuffer(encode));
    }

    public static String encodeBase64(String encode) throws IOException {
        return new String(new BASE64Encoder().encodeBuffer(encode.getBytes()));
    }
}
