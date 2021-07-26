package cool.zhouxin.q_1010000040378204.v6.compose;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author zhouxin
 * @since 2021/7/26 15:38
 */
class ComposeProxyFactoryBean implements FactoryBean {

    private Class tClass;
    // 当前拦截目标方法后需要做的增强操作
    private ComposeMethodAdvice advice;

    public ComposeProxyFactoryBean(Class tClass, ComposeMethodAdvice advice) {
        this.tClass = tClass;
        this.advice = advice;
    }

    @Override
    public Class<?> getObjectType() {
        return this.tClass;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Object getObject() throws Exception {
        Enhancer en = new Enhancer();
        en.setInterfaces(new Class[]{this.tClass});
        en.setSuperclass(AbstractCompose.class);
        en.setCallback(new ComposeMethodInterceptor(this.tClass, this.advice));
        Object composeProxy = en.create();
        return composeProxy;
    }
}
