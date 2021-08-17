package cool.zhouxin.q_1010000040378204.delete_modify;

import org.springframework.stereotype.Component;

/**
 * @author zhouxin
 * @since 2021/8/17 17:27
 */
@Component
public class ADeleteModifyExecutor implements DeleteModifyExecutor {

    @Override
    public DeleteModifyType type() {
        return DeleteModifyType.A;
    }

    @Override
    public void delete(Param param) {

    }

    @Override
    public void modify(Param param) {

    }
}
