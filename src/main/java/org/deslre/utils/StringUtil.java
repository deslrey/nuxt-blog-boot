package org.deslre.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: StringUtil
 * Description: TODO
 * Author: Deslrey
 * Date: 2024-11-29 11:29
 * Version: 1.0
 */
public class StringUtil extends StringUtils {

    /**
     * 生成随机数
     */
    public static String getRandomNumber(Integer count) {
        return RandomStringUtils.random(count, false, true);
    }

    public static boolean isEmpty(String str) {

        if (null == str || str.isEmpty() || "null".equals(str) || "\u0000".equals(str))
            return true;
        else
            return str.trim().isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        if (null == str || str.isEmpty() || "null".equals(str) || "\u0000".equals(str))
            return false;
        else return !str.trim().isEmpty();
    }

    public static <T> boolean isNull(T clazz) {
        return clazz == null;
    }

    public static <T> boolean isNotNull(T clazz) {
        return !isNull(clazz);
    }

    public static String encodeByMd5(String originalString) {
        return isEmpty(originalString) ? null : DigestUtils.md5Hex(originalString);
    }

    public static boolean pathIsOk(String filePath) {
        if (isEmpty(filePath)) {
            return false;
        }
        return !filePath.contains("../") && !filePath.contains(".\\");
    }

    public static String getFileSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return fileName.substring(index);
    }

    public static String getFileNameNoSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return fileName;
        }
        fileName = fileName.substring(0, index);
        return fileName;
    }

    public static String getRandomString(Integer count) {
        return RandomStringUtils.random(count, true, true);
    }

}
