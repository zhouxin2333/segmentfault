package cool.zhouxin.q_1010000040378204.delete_modify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhouxin
 * @since 2021/8/17 17:28
 */
@Component
public class ServiceImpl implements Service {

    private Map<DeleteModifyType, DeleteModifyExecutor> map;

    @Autowired
    public void init(List<DeleteModifyExecutor> executors) {
        this.map = executors.stream()
                       .collect(Collectors.toMap(
                               DeleteModifyExecutor::type,
                               Function.identity()));
    }

    @Override
    public void delete(Param param) {
        DeleteModifyExecutor executor = this.getExecutor(param);
        executor.delete(param);
    }

    @Override
    public void modify(Param param) {
        DeleteModifyExecutor executor = this.getExecutor(param);
        executor.modify(param);
    }

    private DeleteModifyExecutor getExecutor(Param param) {
        DeleteModifyExecutor deleteModifyExecutor = this.map.entrySet()
                .stream()
                .filter(entry -> this.match(param, entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst().get();
        return deleteModifyExecutor;
    }

    private boolean match(Param param, DeleteModifyType type) {
        return type.getRichengType() == null ? true : type.getRichengType().equals(param.getRichengType())
                && type.getZuzhiType() == null ? true : type.getZuzhiType().equals(param.getZuzhiType())
                && type.getDataType() == null ? true : type.getDataType().equals(param.getDataType());
    }
}
