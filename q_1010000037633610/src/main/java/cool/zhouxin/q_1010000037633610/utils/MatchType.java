package cool.zhouxin.q_1010000037633610.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiPredicate;

@AllArgsConstructor
@Getter
enum MatchType {
    OPEN_LEFT_CLOSE_RIGHT((t, range) -> t.compareTo(range.getMin()) > 0 && t.compareTo(range.getMax()) <= 0),
    OPEN_LEFT_OPEN_RIGHT((t, range) -> t.compareTo(range.getMin()) > 0 && t.compareTo(range.getMax()) < 0),
    CLOSE_LEFT_OPEN_RIGHT((t, range) -> t.compareTo(range.getMin()) >= 0 && t.compareTo(range.getMax()) < 0),
    CLOSE_LEFT_CLOSE_RIGHT((t, range) -> t.compareTo(range.getMin()) >= 0 && t.compareTo(range.getMax()) <= 0),

    ;
    private BiPredicate<Comparable, Range> predicate;
}
