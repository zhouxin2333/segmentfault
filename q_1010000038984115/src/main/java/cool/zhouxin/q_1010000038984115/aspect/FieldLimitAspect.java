package cool.zhouxin.q_1010000038984115.aspect;

import cool.zhouxin.q_1010000038984115.utils.StringUtils;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2021/1/16 19:47
 */
@Aspect
@Component
public class FieldLimitAspect {

    @Autowired
    private List<ReturnTypeHandler> handlers;

    @Pointcut(value = "@annotation(fieldLimit)")
    public void pointcut(FieldLimit fieldLimit) {
    }

    @Around(value = "pointcut(fieldLimit)")
    public Object around(ProceedingJoinPoint joinPoint, FieldLimit fieldLimit) throws Throwable {
        // 获取该方法返回结果
        Object result = joinPoint.proceed();
        // 若没有限制的字段，则直接返回原结果
        String[] fieldLimitNameArray = fieldLimit.value();
        if (fieldLimitNameArray.length == 0) return result;

        // 获取执行方法的返回类型
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Type genericReturnType = method.getGenericReturnType();
        ResolvableType resolvableType = ResolvableType.forType(genericReturnType);
        Optional<ReturnTypeHandler> returnTypeHandlerOptional = handlers.stream()
                .filter(handler -> handler.isNeedProcess(resolvableType)).findFirst();
        // 没有处理的Handler，则直接返回
        if (!returnTypeHandlerOptional.isPresent()) return result;
        ReturnTypeHandler returnTypeHandler = returnTypeHandlerOptional.get();

        // 若方法返回为空，就不用处理了
        if (returnTypeHandler.isEmpty(result)) return result;

        String classPath = this.buildClassName(method);
        Class<?> resultClass = returnTypeHandler.getRawClass(result);
        // 这是需要返回的字段set
        Set<String> fieldLimitNameSet = Arrays.stream(fieldLimitNameArray).collect(Collectors.toSet());
        // 这是返回的原始实体类应该有的字段在经过了需要返回的set过滤
        List<Field> fieldLimitFields = Arrays.stream(resultClass.getDeclaredFields())
                .filter(field -> fieldLimitNameSet.contains(field.getName()))
                .collect(Collectors.toList());
        // 如果过滤后没有任何一个字段，则直接返回原结果
        if (fieldLimitFields.isEmpty()) return result;
        Class newClass = NewClassBuilder.getNewClass(classPath, fieldLimitFields);

        result = returnTypeHandler.newInstance(newClass, result);
        return result;
    }

    private String buildClassName(Method method) {
        String declaringClassName = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String returnTypeClassName = method.getReturnType().getSimpleName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String className = Stream.concat(Stream.of(declaringClassName, methodName, returnTypeClassName),
                Arrays.stream(parameterTypes).map(Class::getSimpleName)).collect(Collectors.joining("$"));
        return className;
    }
}
