package cool.zhouxin.q_1010000037633610.utils;

import lombok.*;

/**
 * @author zhouxin
 * @since 2020/10/28 17:27
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class BaseRange<T extends Comparable> implements Range<T> {

    @NonNull
    private T min;
    @NonNull
    private T max;
    private MatchType matchType;

    public static <R extends Comparable> BaseRange<R> of(MatchType matchType) {
        BaseRange baseRange = new BaseRange();
        baseRange.setMatchType(matchType);
        return baseRange;
    }

    public static <R extends Comparable> BaseRange<R> of(R min, R max) {
        return new BaseRange(min, max);
    }

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
