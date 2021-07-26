package cool.zhouxin.q_1010000040378204.v5.compose;

import cool.zhouxin.q_1010000040378204.utils.ExceptionEnv;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2021/7/25 16:30
 */
class ComposeFactoryBean implements FactoryBean {

    private Class tClass;
    private String fullComposeClassName;

    // 创建一个默认的ClassPool，用于准备创建class
    private static final ClassPool pool = ClassPool.getDefault();
    // 创建出来的class名字
    private static final String COMPOSE_CLASS_NAME = "DefaultCompose";
    // 创建出来的class完整名字，暂时用了ComposeFactoryBean的包路径
    private static final String FULL_COMPOSE_CLASS_NAME_PREFIX = ComposeFactoryBean.class.getPackage().getName() + "." + COMPOSE_CLASS_NAME;
    /**
     * {@link AbstractCompose#items}的字段名，用于实现目标接口方法时可以引用
     */
    private static final String FIELD_NAME = "items";
    // $0是javassist的语法规则，代表this，这里items是父类的，所以完整的意思即为：super.items
    private static final String THIS_FIELD_NAME = "$0." + FIELD_NAME;
    /**
     * 创建出来的class作为缓存，因为多例的情况下，{@link #getObject()}会调用多次
     */
    private Class cacheClass = null;

    public ComposeFactoryBean(Class tClass) {
        this.tClass = tClass;
        this.fullComposeClassName = FULL_COMPOSE_CLASS_NAME_PREFIX + "4" + this.tClass.getSimpleName();
    }

    @Override
    public Class<?> getObjectType() {
        return this.tClass;
    }

    /**
     * 这里最终生成的DefaultCompose4XXX Bean是多例
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Object getObject() throws Exception {
        if (this.cacheClass == null) {
            this.cacheClass = this.createComposeClass();
        }
        Object compose = this.cacheClass.newInstance();
        return compose;
    }

    private Class createComposeClass() throws Exception {

        // 根据类名从CtClass池中获取一个CtClass对象
        CtClass ctClass = pool.makeClass(this.fullComposeClassName);

        // 给当前ctClass设置实现接口
        CtClass composeInterfaceCtClass = pool.getCtClass(this.tClass.getName());
        ctClass.setInterfaces(new CtClass[]{composeInterfaceCtClass});

        // 给当前ctClass设置实现父类
        CtClass defaultObserverContainerSuperCtClass = pool.getCtClass(AbstractCompose.class.getName());
        ctClass.setSuperclass(defaultObserverContainerSuperCtClass);

        // 添加目标接口的实现方法
        addTargetClassMethods(ctClass);

        // 如果想看生成的类，可以把下面的注释放开即可，正式使用建议不要打开
//        ctClass.writeFile();

        // ctClass转换为Class
        Class resultClass = ctClass.toClass();
        return resultClass;
    }

    private void addTargetClassMethods(CtClass ctClass) {
        // 取出目标接口中所有的 本地方法(不包含父接口的方法) 进行循环，
        // 并且ctClass仿照循环方法做实现，达到Override的效果
        ReflectionUtils.doWithLocalMethods(this.tClass,
                method -> addTargetClassMethod(ctClass, method));
    }

    private void addTargetClassMethod(CtClass ctClass, Method method) {
        // 创建新方法的方法名称，因为要实现Override，所以新方法名称和tClass的方法一致，下面也一样
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
     * for(int i = 0; super.items.size(); i++) {
     *     super.items.get(i).对应的方法名（对应的参数，例如$1,$2）
     * }
     * @param methodName
     * @param parameterCount
     * @return
     */
    private String createObserverMethodBody(String methodName, int parameterCount) {
        String parameterBody = IntStream.rangeClosed(1, parameterCount).mapToObj(String::valueOf)
                .map(i -> "$" + i)
                .collect(Collectors.joining(",", "(", ")"));
        StringBuffer stringBuffer = new StringBuffer();
        return stringBuffer.append("{ for(int i =0; i < ")
                .append(THIS_FIELD_NAME)
                .append(".size(); i++) {")
                .append(this.tClass.getName())
                .append(" item = ")
                .append(THIS_FIELD_NAME)
                .append(".get(i);")
                .append("item.")
                .append(methodName)
                .append(parameterBody)
                .append(";}}")
                .toString();
    }


    private CtClass[] toCtClassArray(Class<?>[] parameterTypes) {
        return Stream.of(parameterTypes)
                .map(tClass -> ExceptionEnv.handler(() -> pool.get(tClass.getName())))
                .toArray(CtClass[]::new);
    }
}
