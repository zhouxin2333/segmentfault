package cool.zhouxin.q_1010000040378204.utils;

import java.util.function.Function;

/**
 * @author zhouxin
 * @since 2021/7/26 17:47
 */
public class StringUtils {

    public static String toFirstUpperCase(String str) {
        return toSomeCase(str, String::toUpperCase);
    }

    /**
     * 首字符小写
     *
     * @param str
     * @return
     */
    public static String toFirstLowerCase(String str) {
        return toSomeCase(str, String::toLowerCase);
    }

    private static String toSomeCase(String str, Function<String, String> fun) {
        return fun.apply(str.substring(0, 1)) + str.substring(1, str.length());
    }
}
