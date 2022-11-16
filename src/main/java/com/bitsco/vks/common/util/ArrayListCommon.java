/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitsco.vks.common.util;

import java.util.List;

/**
 * @author : TruongNQ
 * @date created : Apr 7, 2018
 * @describe :
 */
public class ArrayListCommon {

    public static boolean isNullOrEmpty(List lst) {
        return lst == null || lst.isEmpty();
    }

    public static boolean isNullOrEmpty(Object[] arrObj) {
        return arrObj == null || arrObj.length == 0;
    }

    public static Object[] convertList2Array(List list) {
        return list.toArray(new Object[list.size()]);
    }
}
