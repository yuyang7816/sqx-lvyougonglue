package com.sqx.app.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengming
 * @since 2024/9/13
 */
public class CommaSeparatedStringToListConverter {

    /**
     * 逗号分隔的字符串转化为list
     * @param source 源字符串
     * @return list
     */
    public static List<String> convert(String source) {
        if (StringUtils.isBlank(source)) {
            return new ArrayList<>();
        }

        return Arrays.stream(source.split(",")).collect(Collectors.toList());
    }
}
