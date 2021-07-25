package cool.zhouxin.q_1010000040378204.v5.compose;

import java.util.List;

/**
 * @author zhouxin
 * @since 2021/7/25 16:48
 */
abstract class AbstractCompose {

    protected List items;

    public void add(List items) {
        this.items = items;
    }
}
