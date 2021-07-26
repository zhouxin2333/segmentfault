package cool.zhouxin.q_1010000040378204.v6.compose;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouxin
 * @since 2021/7/26 16:52
 */
@Component
class DefaultComposeMethodAdvice implements ComposeMethodAdvice {

    private Map<Class, ComposeItemExceptionHandler> exceptionHandlerMap = new HashMap<>();
    @Autowired(required = false)
    private List<ComposeItemExceptionHandler> exceptionHandlers = new ArrayList<>();


    @Override
    public Object invoke(List items, Object o, Method method, Object[] objects) {
        for (Object item : items) {
            try{
                method.invoke(item, objects);
            }catch (Exception e) {
                this.getExceptionHandler(o).process(e, item, method, objects);
            }
        }
        return null;
    }

    private ComposeItemExceptionHandler getExceptionHandler(Object target) {
        Class<?> targetClass = AopUtils.getTargetClass(target);
        return exceptionHandlerMap.computeIfAbsent(targetClass, key -> this.findExceptionHandler(key));
    }

    private ComposeItemExceptionHandler findExceptionHandler(Class tClass) {
        return this.exceptionHandlers.stream()
                                     .filter(exceptionHandler -> exceptionHandler.match(tClass))
                                     .findFirst()
                                     .orElse(DefaultComposeItemExceptionHandler.INSTANCE);
    }
}
