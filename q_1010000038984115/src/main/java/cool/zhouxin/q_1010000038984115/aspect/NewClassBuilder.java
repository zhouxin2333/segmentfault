package cool.zhouxin.q_1010000038984115.aspect;

import cool.zhouxin.q_1010000038984115.utils.StringUtils;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2021/1/17 16:43
 */
public class NewClassBuilder {

    private static final Map<String, Class> classCache = new HashMap<>();

    public static Class getNewClass(String classPath, String[] fieldNameArray) {
        return getNewClass(classPath, Arrays.stream(fieldNameArray),
                Function.identity(), t -> Object.class);
    }

    public static Class getNewClass(String classPath, List<Field> fieldList) {
        return getNewClass(classPath, fieldList.stream(),
                Field::getName, Field::getType);
    }

    public static <T> Class getNewClass(String classPath,
                                        Stream<T> fieldStream,
                                        Function<T, String> getFieldNameFun,
                                        Function<T, Class> getFieldClassFun) {
        Class newClass = classCache.get(classPath);
        if (newClass != null) return newClass;
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(classPath);
        CtClass anInterface = pool.makeInterface(IBaseVO.class.getName());
        ctClass.addInterface(anInterface);
        try{
            Iterator<T> iterator = fieldStream.iterator();
            while (iterator.hasNext()) {
                T next = iterator.next();
                String fieldName = getFieldNameFun.apply(next);
                CtClass fieldClass = pool.get(getFieldClassFun.apply(next).getName());
                CtField ctField = new CtField(fieldClass, fieldName, ctClass);
                ctField.setModifiers(Modifier.PRIVATE);

                ctClass.addField(ctField);

                // 添加setter
                String firstUpperCaseName = StringUtils.toFirstUpperCase(fieldName);
                CtMethod setterMethod = new CtMethod(CtClass.voidType, "set".concat(firstUpperCaseName), new CtClass[]{fieldClass}, ctClass);
                setterMethod.setBody("{ $0.".concat(fieldName).concat("=$1; }"));
                setterMethod.setModifiers(Modifier.PUBLIC);
                ctClass.addMethod(setterMethod);

                // 添加getter
                CtMethod getterMethod = new CtMethod(fieldClass, "get".concat(firstUpperCaseName), new CtClass[]{}, ctClass);
                getterMethod.setBody("{ return $0.".concat(fieldName).concat("; }"));
                getterMethod.setModifiers(Modifier.PUBLIC);
                ctClass.addMethod(getterMethod);
            }
            // 如果不想要生成class文件，也可以把下面这一行注释掉
//            ctClass.writeFile();
            newClass = ctClass.toClass();
            ctClass.detach();
            anInterface.detach();
            classCache.put(classPath, newClass);
            return newClass;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
