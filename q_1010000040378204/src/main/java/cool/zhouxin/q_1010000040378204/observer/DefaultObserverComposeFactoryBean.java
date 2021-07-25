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
 * 1. 该类利用Javassist字节码编程创建一个名为{@link #CLASS_NAME}的class，并且装入类加载器
 * 2. 创建出来的class其父类是{@link DefaultObserverContainer}，实现接口{@link Observer}
 * 3. 实现的接口{@link Observer}方法，默认为把{@link DefaultObserverContainer}中所有的
 *    {@link DefaultObserverContainer#executors}全部循环然后执行其对应的{@link Observer}方法
 * @author zhouxin
 * @since 2021/7/24 19:03
 */
@Component
class DefaultObserverComposeFactoryBean implements FactoryBean {
    // 创建出来的class名字
    private static final String CLASS_NAME = "DefaultObserverCompose";
    // 创建出来的class完整名字，暂时用了DefaultObserverComposeFactoryBean的包路径
    private static final String FULL_CLASS_NAME = DefaultObserverComposeFactoryBean.class.getPackage().getName() + "." + CLASS_NAME;
    // 创建一个默认的ClassPool，用于准备创建class
    private static final ClassPool pool = ClassPool.getDefault();
    /**
     * {@link DefaultObserverContainer#executors}的字段名，用于实现{@link Observer}方法时可以引用
     */
    private static final String OBSERVERS_FIELD_NAME = "executors";
    // $0是javassist的语法规则，代表this，这里executors是父类，所以完整的意思即为：super.executors
    private static final String THIS_OBSERVERS_FIELD_NAME = "$0." + OBSERVERS_FIELD_NAME;
    /**
     * 创建出来的class作为缓存，因为多例的情况下，{@link #getObject()}会调用多次
     */
    private Class<ObserverCompose> cacheClass = null;

    /**
     * 返回该Bean的类型
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return ObserverCompose.class;
    }

    /**
     * 多例
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }

    /**
     * 返回一个DefaultObserverCompose的实例
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        if (this.cacheClass == null) {
            this.cacheClass = this.createClass();
        }
        ObserverCompose observerCompose = this.cacheClass.newInstance();
        return observerCompose;
    }

    /**
     * 下面是Javassist创建一个class的过程，如果对于语法不是很了解，可能看起来很模糊
     * @return
     * @throws Exception
     */
    private Class<ObserverCompose> createClass() throws Exception {

        // 根据类名从CtClass池中获取一个CtClass对象
        CtClass ctClass = pool.makeClass(FULL_CLASS_NAME);

        // 给当前ctClass设置实现接口
        CtClass observerComposeInterfaceCtClass = pool.getCtClass(ObserverCompose.class.getName());
        ctClass.setInterfaces(new CtClass[]{observerComposeInterfaceCtClass});

        // 给当前ctClass设置实现父类
        CtClass defaultObserverContainerSuperCtClass = pool.getCtClass(DefaultObserverContainer.class.getName());
        ctClass.setSuperclass(defaultObserverContainerSuperCtClass);

        // 添加Observer的实现方法
        addObserverMethod(ctClass);

        // ctClass转换为Class
        Class<ObserverCompose> tClass = (Class<ObserverCompose>) ctClass.toClass();
        return tClass;
    }

    private static void addObserverMethod(CtClass ctClass) {
        // 取出Observer中所有的方法进行循环，并且ctClass仿照循环方法做实现，达到Override的效果
        ReflectionUtils.doWithLocalMethods(Observer.class,
                method -> addObserverMethod(ctClass, method));
    }

    /**
     *
     * @param ctClass
     * @param method Observer的某一个方法
     */
    private static void addObserverMethod(CtClass ctClass, Method method) {
        // 创建新方法的方法名称，因为要实现Override，所以新方法名称和Observer的方法一致，下面也一样
        String methodName = method.getName();
        // 创建新方法的方法参数
        CtClass[] parameterTypeCtClassArray = toCtClassArray(method.getParameterTypes());
        // 创建新方法的返回参数
        CtClass returnTypeCtClass = ExceptionEnv.handler(() -> pool.get(method.getReturnType().getName()));
        // 创建新方法CtMethod
        CtMethod overrideMethod = new CtMethod(returnTypeCtClass, methodName, parameterTypeCtClassArray, ctClass);
        // 设置新方法为public
        overrideMethod.setModifiers(Modifier.PUBLIC);

        // 计算原方法有多少个参数，方便下面填充新方法的方法体
        int parameterCount = method.getParameterCount();
        // 创建新方法的方法体
        String methodBody = createObserverMethodBody(methodName, parameterCount);
        ExceptionEnv.handler(
                () -> overrideMethod.setBody(methodBody),
                () -> ctClass.addMethod(overrideMethod));
    }

    /**
     * 最后的效果即为：
     * for(int i = 0; super.executors.size(); i++) {
     *     super.executors.get(i).对应的方法名（对应的参数，例如$1,$2）
     * }
     * @param methodName
     * @param parameterCount
     * @return
     */
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
