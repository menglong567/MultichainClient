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
}
