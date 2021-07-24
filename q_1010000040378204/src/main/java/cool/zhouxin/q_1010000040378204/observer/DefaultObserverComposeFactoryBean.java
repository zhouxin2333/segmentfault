package cool.zhouxin.q_1010000040378204.observer;

import cool.zhouxin.q_1010000040378204.utils.ExceptionEnv;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2021/7/24 19:03
 */
@Component
public class DefaultObserverComposeFactoryBean implements FactoryBean {

    private static final String CLASS_NAME = "DefaultObserverCompose";
    private static final String FULL_CLASS_NAME = DefaultObserverComposeFactoryBean.class.getPackage().getName() + "." + CLASS_NAME;
    private static final ClassPool pool = ClassPool.getDefault();
    private static final String OBSERVERS_FIELD_NAME = "executors";
    private static final String THIS_OBSERVERS_FIELD_NAME = "$0." + OBSERVERS_FIELD_NAME;
    private Class<ObserverCompose> cacheClass = null;

    @Override
    public Class<?> getObjectType() {
        return ObserverCompose.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Object getObject() throws Exception {
        if (this.cacheClass == null) {
            this.cacheClass = this.createClass();
        }
        ObserverCompose observerCompose = this.cacheClass.newInstance();
        return observerCompose;
    }

    private Class<ObserverCompose> createClass() throws Exception {

        CtClass ctClass = pool.makeClass(FULL_CLASS_NAME);

        CtClass observerComposeInterfaceCtClass = pool.getCtClass(ObserverCompose.class.getName());
        ctClass.setInterfaces(new CtClass[]{observerComposeInterfaceCtClass});

        CtClass defaultObserverContainerSuperCtClass = pool.getCtClass(DefaultObserverContainer.class.getName());
        ctClass.setSuperclass(defaultObserverContainerSuperCtClass);

        // 添加Observer的实现方法
        addObserverMethod(ctClass);

        Class<ObserverCompose> tClass = (Class<ObserverCompose>) ctClass.toClass();
        return tClass;
    }

    private static void addObserverMethod(CtClass ctClass) {
        ReflectionUtils.doWithLocalMethods(Observer.class,
                method -> addObserverMethod(ctClass, method));
    }

    private static void addObserverMethod(CtClass ctClass, Method method) {
        String methodName = method.getName();
        CtClass[] parameterTypeCtClassArray = toCtClassArray(method.getParameterTypes());
        CtClass returnTypeCtClass = ExceptionEnv.handler(() -> pool.get(method.getReturnType().getName()));
        CtMethod overrideMethod = new CtMethod(returnTypeCtClass, methodName, parameterTypeCtClassArray, ctClass);
        overrideMethod.setModifiers(Modifier.PUBLIC);

        int parameterCount = method.getParameterCount();
        String methodBody = createObserverMethodBody(methodName, parameterCount);
        ExceptionEnv.handler(
                () -> overrideMethod.setBody(methodBody),
                () -> ctClass.addMethod(overrideMethod));
    }


    private static String createObserverMethodBody(String methodName, int parameterCount) {
        String parameterBody = IntStream.rangeClosed(1, parameterCount).mapToObj(String::valueOf)
                .map(i -> "$" + i)
                .collect(Collectors.joining(",", "(", ")"));
        StringBuffer stringBuffer = new StringBuffer();
        return stringBuffer.append("{ for(int i =0; i < ")
                .append(THIS_OBSERVERS_FIELD_NAME)
                .append(".size(); i++) {")
                .append(ObserverExecutor.class.getName())
                .append(" executor = ")
                .append(THIS_OBSERVERS_FIELD_NAME)
                .append(".get(i);")
                .append("executor.")
                .append(methodName)
                .append(parameterBody)
                .append(";}}")
                .toString();
    }


    private static CtClass[] toCtClassArray(Class<?>[] parameterTypes) {
        return Stream.of(parameterTypes)
                .map(tClass -> ExceptionEnv.handler(() -> pool.get(tClass.getName())))
                .toArray(CtClass[]::new);
    }
}
