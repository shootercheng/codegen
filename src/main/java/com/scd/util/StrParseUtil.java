package com.scd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author James
 */
public class StrParseUtil {

    /**
     * 根据分隔符 解析字符串
     * @param value
     * @param separator
     * @return
     */
    public static List<String> parseStrBySeparator(String value, String separator) {
        List<String> resultList = new TrimList<>();
        int index;
        while ((index = value.indexOf(separator)) != -1) {
            resultList.add(value.substring(0, index));
            value = value.substring(index + 1);
        }
        resultList.add(value);
        return resultList;
    }

    @SuppressWarnings("unchecked")
    private static class TrimList<E> extends ArrayList<E> {
        @Override
        public boolean add(E e) {
            boolean result;
            if (e instanceof String) {
                String stre = (String) e;
                stre = stre.trim();
                result = super.add((E) stre);
            } else {
                result =super.add(e);
            }
            return result;
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
