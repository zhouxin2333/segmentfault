package cool.zhouxin.q_1010000040378204.v6.compose;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhouxin
 * @since 2021/7/26 15:44
 */
class ComposeMethodInterceptor implements MethodInterceptor {

    // 当前拦截的目标方法所属的接口Class
    private Class tClass;
    // 当前拦截目标方法后需要做的增强操作
    private ComposeMethodAdvice advice;

    public ComposeMethodInterceptor(Class tClass, ComposeMethodAdvice advice) {
        this.tClass = tClass;
        this.advice = advice;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 是否跳过此次拦截
        boolean isSkip = this.isSkip(method);
        if (isSkip) return methodProxy.invokeSuper(o, objects);

        ComposeContainer container = ComposeContainer.class.cast(o);
        List items = container.get();

        return this.advice.invoke(items, o, method, objects);
    }

    /**
     * 只拦截和当前接口Class相关的方法
     * @param method
     * @return
     */
    private boolean isSkip(Method method) {
        return !method.getDeclaringClass().equals(this.tClass);
    }
}
