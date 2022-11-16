/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitsco.vks.common.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : TruongNQ
 * @date created : Apr 7, 2018
 * @describe :
 */
public class StringCommon {

    private static final char[] NOSIGN = {'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'd', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'i', 'i', 'i', 'i', 'i', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'y', 'y', 'y', 'y', 'y', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'D', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'I', 'I', 'I', 'I', 'I', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'U', 'U', 'U', 'U', 'U', 'U', 'U', 'U', 'U', 'U', 'U', 'Y', 'Y', 'Y', 'Y', 'Y'};

    /**
     * @param str
     * @return
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().equals("");
    }

    public static String padleft(String s, int len, char c)
            throws Exception {
        s = s.trim();
        if (s.length() > len) {
            throw new Exception(new StringBuilder()
                    .append("invalid len ").append(s.length()).append("/")
                    .append(len).toString());
        }
        StringBuffer d = new StringBuffer(len);
        int fill = len - s.length();
        while (fill-- > 0) {
            d.append(c);
        }
        d.append(s);
        return d.toString();
    }

    public static String toNoSign(String s) {
        String result = "";
        if (isNullOrBlank(s)) {
            return result;
        }
        for (int i = 0; (s != null) && (i < s.length()); i++) {
            char c = s.charAt(i);
            int pos;
            if ((pos = "áàảãạăắằẳẵặâấầẩẫậđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬĐÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ".indexOf(c)) != -1) {
                result = new StringBuilder().append(result).append(NOSIGN[pos]).toString();
            } else {
                result = new StringBuilder().append(result).append(c).toString();
            }
        }
        result = result.replaceAll("[^-a-zA-Z0-9~!@#$%^&*()_+={}\\[\\]|\\\\/?<>,\"':;. ]+", "");
        return result;
    }

    /**
     * Validate {@code str} not null, not empty and matches {@code regex}
     *
     * @param str
     * @param regex
     * @return {@code str} matches {@code regex}
     * @throws IllegalArgumentException if {@code regex} is null
     */
    public static boolean isValidPattern(String str, String regex) {

        if (regex == null) {
            throw new IllegalArgumentException("Regex pattern must not be null");
        }

        if (!StringUtils.isEmpty(str)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        }

        return false;
    }

    /**
     * @param str
     * @return reverse of String input
     */
    public static String reverse(String str) {
        if (isNullOrBlank(str))
            return str;
        else
            return new StringBuffer(str).reverse().toString();
    }

    public static String getKeyWordFromMessageResource(String msg) {
        if (isNullOrBlank(msg))
            return msg;
        else
            return msg.replaceAll(" ", ".")
                    .replace("{0}", "param1")
                    .replace("{1}", "param2")
                    .replace("{2}", "param3")
                    .replace("{3}", "param4")
                    .replace("{4}", "param5")
                    .replace("{5}", "param6")
                    .toLowerCase()
                    ;
    }

    public static String addLikeRightAndLeft(String source) {
        if (isNullOrBlank(source))
            return source;
        else
            return "%" + source.trim() + "%";
    }

    public static String addLikeRight(String source) {
        return isNullOrBlank(source) ? source : (source.trim() + "%");
    }

    public static String addLikeLeft(String source) {
        return isNullOrBlank(source) ? source : ("%" + source.trim());
    }

    public static boolean isStringContainsWhiteSpace(String value) {
        if (value == null) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (Character.isWhitespace(value.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public static String deleteLastUnderscore(String input) {
        if (isNullOrBlank(input))
            return input;
        else if (input.endsWith("_"))
            return deleteLastUnderscore(input.substring(0, input.length() - 1));
        else
            return input;
    }

    public static String removeBeginSpecialChar(String s) {

        StringBuilder result = new StringBuilder("");
        boolean faceNumberOrCharacter = false;
        for (int i = 0; (s != null) && (i < s.length()); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c)) {
                faceNumberOrCharacter = true;
            }
            if (faceNumberOrCharacter) {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String removeEndSpecialChar(String s) {

        StringBuilder result = new StringBuilder(s);
        boolean faceNumberOrCharacter = false;
        for (int i = s.length() - 1; i > 0; i--) {
            char c = s.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c)) {
                faceNumberOrCharacter = true;
            }
            if (!faceNumberOrCharacter) {
                result.deleteCharAt(i);
            }
        }
        return result.toString();
    }

    public static String removeSpecialCharAtBeginAndEnd(String s) {
        return removeEndSpecialChar(removeBeginSpecialChar(s));
    }

    public static String removeSpecialCharAndToNoSign(String s) {
        if (isNullOrBlank(s)) return "";
        String result = toNoSign(s);
        return " " + removeSpecialCharAtBeginAndEnd(result).toUpperCase() + " ";
    }

    public static void main(String[] args) {
        System.out.println(toNoSign("Phường Nguyễn Trung Trực"));
        System.out.println(removeSpecialCharAndToNoSign("Phường Nguyễn Trung Trực"));
    }
    //phucnv start 12/4/2020 add

    public static String convertToString(Object obj1) {
        if (obj1 == null) {
            return "";
        }

        return obj1.toString();
    }
    public static boolean safeEqual(String obj1, String obj2) {
        if (obj1 == obj2) {
            return true;
        }
        return ((obj1 != null) && (obj2 != null) && obj1.equals(obj2));
    }
    //phucnv end 12/4/2020
}
