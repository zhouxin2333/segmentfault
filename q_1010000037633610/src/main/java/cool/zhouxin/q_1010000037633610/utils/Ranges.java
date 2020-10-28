package cool.zhouxin.q_1010000037633610.utils;

import lombok.*;

/**
 * @author zhouxin
 * @since 2020/10/28 17:06
 */
public class Ranges {

    public static <T extends Comparable> BaseRange<T> of(MatchType matchType) {
        BaseRange baseRange = new BaseRange();
        baseRange.setMatchType(matchType);
        return baseRange;
    }

    public static <T extends Comparable> Range<T> ofCloseLeftCloseRight(T min, T max) {
        return of(min,max).addMatchType(MatchType.CLOSE_LEFT_CLOSE_RIGHT);
    }

    public static <T extends Comparable> Range<T> ofCloseLeftOpenRight(T min, T max) {
        return of(min,max).addMatchType(MatchType.CLOSE_LEFT_OPEN_RIGHT);
    }

    public static <T extends Comparable> Range<T> ofOpenLeftOpenRight(T min, T max) {
        return of(min,max).addMatchType(MatchType.OPEN_LEFT_OPEN_RIGHT);
    }

    public static <T extends Comparable> Range<T> ofOpenLeftCloseRight(T min, T max) {
        return of(min,max).addMatchType(MatchType.OPEN_LEFT_CLOSE_RIGHT);
    }

    public static <T extends Comparable> BaseRange<T> of(T min, T max) {
        return new BaseRange(min, max);
    }

    public static <T extends Comparable> Range<T> ofAlwaysMatch(String formatString) {
        return new AlwaysMatchRange(formatString);
    }

    public static <T extends Comparable> Range<T> ofAlwaysMatch() {
        return new AlwaysMatchRange("");
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class AlwaysMatchRange<T extends Comparable> implements Range<T> {

        private String formatString;

        @Override
        public boolean isMatch(T t) {
            return true;
        }

        @Override
        public String formatString() {
            return formatString;
        }

        @Override
        public T getMin() {
            return null;
        }

        @Override
        public T getMax() {
            return null;
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    public static class BaseRange<T extends Comparable> implements Range<T> {

        @NonNull
        private T min;
        @NonNull
        private T max;
        private MatchType matchType;

        public Range<T> addMin(T min) {
            this.setMin(min);
            return this;
        }

        public Range<T> addMax(T max) {
            this.setMax(max);
            return this;
        }

        public Range<T> addMatchType(MatchType matchType) {
            this.setMatchType(matchType);
            return this;
        }
    }
}
