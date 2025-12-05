package com.pkgsub.subscriptionsystem.common.utils;

import org.springframework.util.ObjectUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static String getStackTrace(Throwable throwable) {
        if (!ObjectUtils.isEmpty(throwable)) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw, true)) {
                throwable.printStackTrace(pw);
                return sw.toString();
            }
        }
        return "";
    }
}