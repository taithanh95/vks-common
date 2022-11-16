package com.bitsco.vks.common.crypt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Truong Nguyen
 * Date: 22-May-18
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class RSA {
    private static final Logger LOGGER = LogManager.getLogger("Common");
    public static String SHA1withRSA = "SHA1withRSA";
    public static String RSA = "RSA";

    public static PrivateKey getPrivateKeyFromString(String key) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.toByteArrayBase64(key));

            return keyFactory.generatePrivate(privateKeySpec);
        } catch (IOException ex) {
            LOGGER.error("IOException when getPrivateKeyFromString: ", ex);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("NoSuchAlgorithmException when getPrivateKeyFromString: ", ex);
        } catch (InvalidKeySpecException ex) {
            LOGGER.error("InvalidKeySpecException when getPrivateKeyFromString: ", ex);
        }
        return null;
    }

    public static PublicKey getPublicKeyFromString(String key) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.toByteArrayBase64(key));

            return keyFactory.generatePublic(publicKeySpec);
        } catch (IOException ex) {
            LOGGER.error("IOException when getPublicKeyFromString: ", ex);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("NoSuchAlgorithmException when getPublicKeyFromString: ", ex);
        } catch (InvalidKeySpecException ex) {
            LOGGER.error("InvalidKeySpecException when getPublicKeyFromString: ", ex);
        }
        return null;
    }

    public static byte[] encrypt(String msg, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] encrypt(String msg, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
    }

    public static String encryptPassFromPublicKey(String pass, String publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return Base64.toStringBase64(encrypt(pass, getPublicKeyFromString(publicKey)));
    }

    public static String encryptPassFromPrivateKey(String pass, String privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        return Base64.toStringBase64(encrypt(pass, getPrivateKeyFromString(privateKey)));
    }

    public static String getSignatureFromPrivateKey(String dataSign, String privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, SignatureException {
        return Base64.toStringBase64(signData(dataSign.getBytes(StandardCharsets.UTF_8), getPrivateKeyFromString(privateKey)));
    }

    public static byte[] decrypt(byte[] msg, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(msg);
    }

    public static byte[] decrypt(byte[] msg, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(msg);
    }

    public static byte[] signData(byte[] toBeSigned, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (privateKey == null) {
            return null;
        }
        Signature rsa = Signature.getInstance(SHA1withRSA);
        rsa.initSign(privateKey);
        rsa.update(toBeSigned);

        return rsa.sign();
    }

    public static boolean verifyData(byte[] signature, byte[] data, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance(SHA1withRSA);
        sign.initVerify(publicKey);
        sign.update(data);

        return sign.verify(signature);
    }

    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, IOException, InvalidKeyException, SignatureException {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZZYyP2chxY90cYDeWRGPaAyke01sxKML1j4uV00MTQWIfxdAUM8KZDimzBNKu1TkTLw7RT3dZTQBoDp9Vvd72C7W2Bd62ZFxzhqcC6PybwHG1CnAYRg5ldtvDD0/0gDA++qCvC6RIMlCGj6gwW7Dlj7wmvSQTuWrlIfls9b4iowIDAQAB";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJlljI/ZyHFj3RxgN5ZEY9oDKR7TWzEowvWPi5XTQxNBYh/F0BQzwpkOKbME0q7VORMvDtFPd1lNAGgOn1W93vYLtbYF3rZkXHOGpwLo/JvAcbUKcBhGDmV228MPT/SAMD76oK8LpEgyUIaPqDBbsOWPvCa9JBO5auUh+Wz1viKjAgMBAAECgYACI7BSh0qOupryNXvyN8T3XMjsJkEusHRFfJIm2fxpxdZoFn+KodIFj5xGvKJx6KK0HCNBMO0xoIkwVqajtcRoyRZXHLC/6K2d4pVVOWG8KZLKWKJ5rz7NKAiyjUH7Rrb2PblDG1LIuHfGl3zz4s+6VEpK/E6aDGNiqbRMjuAFRQJBALndZddjwZUis8Cmq+MVgVhx/i0jCT/j079KXHQ8S3CzikqeZWyReUqAj95L1ZAJpXMJ2dswUDTcTTJj1AAaj10CQQDTR7d9FIzNl4C+UwmpenmqA1WwOfpdruDwPPHZKHky4eEf4HInkMly6gE3/UWbr3ZmDYneON1MYJUGGRhQGln/AkBJqnVt3AzixiFGbwjbdS+R1YRvcV5+IxdOzvM74pXn8lESaBs835+aJsBe0+GuOBSKNtJQ7KItgU9bdHOXIu/FAkEAsenbXo22f83LUPm10ixbVUoXlx1LciY4LrTzZ01IugsxutkS57RpnsXkntQ9EHuxtQBDj4jM+Wr/MrJhIdr+2wJBAKZ66wd3shhoRk5yJa7alziGI+wZ9RqZhpn+ke9HYlylxIeH1upamMm0V5oave3+HXaGjHEldk/ks2n1ZcbwgU8=";
        //------------------------TEST decrypt/encrypt passs------------------------
        String pass = "123456";
//        String passEn = encryptPassFromPublicKey(pass, publicKey);
        String passEn = "EpM9AytyrURH3PK2FhCVpbty/rCbm3tsNjLsr0w8ixRqJi/jhl/kJL5pQCzIeIVxEYYvCaft8cwWoh0kuk7QXhpne2LnINDugHpluXdE4L/I3nAyGxUx9CvpmMPrzbNcVjm+gSzCVqvrImSJ16yj5jeSNollN1oq5/MnU13KOnk=";
        System.out.println(passEn);
        String passDe = new String(decrypt(Base64.toByteArrayBase64(passEn), getPrivateKeyFromString(privateKey)));
        System.out.println("|" + passDe + "|");
        System.out.println("passDe.getBytes().length = " + passDe.getBytes().length);
        System.out.println(Arrays.toString(passDe.getBytes()));
        System.out.println(passDe.equals(pass));
        System.out.println(MD5.matched(passDe + "XHNRG59T8EMZ", "0728787720C9427FDC77EF1B7E2172C6"));
        //------------------------TEST verify data sign------------------------
//        String dataSign = "EPOSGET-TRANSACTION151704006BFEBFBFF000506E3.92WC8F2.CN7016366S0187.86282SRKS0996669079";
//        String sgn = "D3bfr3olvtm0AmXP1w2jFWWC3LIgA861Qo/atYBeLEYJySwuRE0Bx5UDa8V0G9zjBhtB67D/zH5CHc8HkwJG0wZp3SPYncpxH7Cd5TtdqoUHa7LrWzo/WlsliJ7CGZvKF4bBjJ0TKahJh38+ZVTwm4ShlVxRSkbzNXnOGhUz8Yg=";
//        System.out.println(sgn);
//        System.out.println(verifyData(Base64.toByteArrayBase64(sgn), dataSign.getBytes(Constant.UTF_8), getPublicKeyFromString(publicKey)));
    }
}
