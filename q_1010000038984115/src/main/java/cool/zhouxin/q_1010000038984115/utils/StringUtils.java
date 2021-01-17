package cool.zhouxin.q_1010000038984115.utils;

import java.util.function.Function;

public class StringUtils {

    public static String toFirstUpperCase(String str) {
        return toSomeCase(str, String::toUpperCase);
    }

    public static String toFirstLowerCase(String str) {
        return toSomeCase(str, String::toLowerCase);
    }

    private static String toSomeCase(String str, Function<String, String> fun) {
        return fun.apply(str.substring(0, 1)) + str.substring(1, str.length());
    }
}
