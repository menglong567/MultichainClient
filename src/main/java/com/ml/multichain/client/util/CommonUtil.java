package com.ml.multichain.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * @author mengl
 */
public class CommonUtil {
    private static final CommonUtil instance = new CommonUtil();
    private static Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
    private static Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    private CommonUtil() {
    }

    public static CommonUtil getInstance() {
        return instance;
    }

    public boolean isInteger(String str) {
        return pattern.matcher(str).matches();
    }

    /**
     * @param str
     * @return
     */
    public boolean isValidLong(String str) {
        try {
            long _v = Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param str
     * @return
     */
    public boolean isValidFloat(String str) {
        try {
            Float _v = Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param str
     * @return
     */
    public boolean isValidDouble(String str) {
        try {
            Double _v = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * 判断所有的IP地址
     *
     * @param IP
     * @return
     */
    public boolean validIPAddressAll(String IP) {

        if (!IP.contains(".") && !IP.contains(":")) {
            return false;
        }
        //IPV4
        if (IP.contains(".")) {
            if (IP.endsWith(".")) {
                return false;
            }
            String[] arr = IP.split("\\.");
            if (arr.length != 4) {
                return false;
            }

            for (int i = 0; i < 4; i++) {
                if (arr[i].length() == 0 || arr[i].length() > 3) {
                    return false;
                }
                for (int j = 0; j < arr[i].length(); j++) {
                    if (arr[i].charAt(j) >= '0' && arr[i].charAt(j) <= '9') {
                        continue;
                    }
                    return false;
                }
                if (Integer.valueOf(arr[i]) > 255 || (arr[i].length() >= 2 && String.valueOf(arr[i]).startsWith("0"))) {
                    return false;
                }
            }
            return true;
        }

        //IPV6
        if (IP.contains(":")) {
            if (IP.endsWith(":") && !IP.endsWith("::")) {
                return false;
            }
            //如果包含多个“::”，一个IPv6地址中只能出现一个“::”
            if (IP.indexOf("::") != -1 && IP.indexOf("::", IP.indexOf("::") + 2) != -1) {
                return false;
            }

            //如果含有一个“::”
            if (IP.contains("::")) {
                String[] arr = IP.split(":");
                if (arr.length > 7 || arr.length < 1) {//"1::"是最短的字符串
                    return false;
                }
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("")) {
                        continue;
                    }
                    if (arr[i].length() > 4) {
                        return false;
                    }
                    for (int j = 0; j < arr[i].length(); j++) {
                        if ((arr[i].charAt(j) >= '0' && arr[i].charAt(j) <= '9') || (arr[i].charAt(j) >= 'A' && arr[i].charAt(j) <= 'F')
                                || (arr[i].charAt(j) >= 'a' && arr[i].charAt(j) <= 'f')) {
                            continue;
                        }
                        return false;
                    }
                }
                return true;
            }

            //如果不含有“::”
            if (!IP.contains("::")) {
                String[] arr = IP.split(":");
                if (arr.length != 8) {
                    return false;
                }
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].length() > 4) {
                        return false;
                    }
                    for (int j = 0; j < arr[i].length(); j++) {
                        if ((arr[i].charAt(j) >= '0' && arr[i].charAt(j) <= '9') || (arr[i].charAt(j) >= 'A' && arr[i].charAt(j) <= 'F')
                                || (arr[i].charAt(j) >= 'a' && arr[i].charAt(j) <= 'f')) {
                            continue;
                        }
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
