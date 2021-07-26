package cool.zhouxin.q_1010000040378204.v6.compose;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouxin
 * @since 2021/7/25 16:48
 */
abstract class AbstractCompose implements ComposeContainer {

    protected List items = new ArrayList();

    public void add(List items) {
        this.items = items;
    }

    @Override
    public List get() {
        return this.items;
    }
}
