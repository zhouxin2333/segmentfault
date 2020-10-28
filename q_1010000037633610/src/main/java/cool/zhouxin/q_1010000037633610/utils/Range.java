package cool.zhouxin.q_1010000037633610.utils;

/**
 * @author zhouxin
 * @since 2020/10/28 17:06
 */
public interface Range<T extends Comparable> {

    T getMin();

    T getMax();

    default MatchType getMatchType() {
        return MatchType.CLOSE_LEFT_OPEN_RIGHT;
    }

    default boolean isMatch(T t) {
        return this.getMatchType().getPredicate().test(t, this);
    }

    default String formatString() {
        return String.format("[%s,%s]", this.getMin().toString(), this.getMax().toString());
    }
}
