package com.bitsco.vks.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author : TruongNQ
 * @date created : Apr 7, 2018
 * @describe : Expression des is undefined on line 13, column 25 in
 * Templates/Classes/Class.java.
 */
public class BeanCommon extends BeanUtils {

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static Map<String, Object> convertObjectToMap(Object source) {
        ObjectMapper oMapper = new ObjectMapper();
        return oMapper.convertValue(source, Map.class);
    }

    /**
     * Get value of the specified property from an object
     *
     * @param object       source object
     * @param propertyName property name
     * @return property value
     */
    public static Object getProperty(Object object, String propertyName) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        Object value = beanWrapper.getPropertyValue(propertyName);
        return value;
    }

    /**
     * Set value of the specified property for an object
     *
     * @param object        source object
     * @param propertyName  property name
     * @param propertyValue property value
     */
    public static void setProperty(Object object, String propertyName, Object propertyValue) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        beanWrapper.setPropertyValue(propertyName, propertyValue);
    }

}
