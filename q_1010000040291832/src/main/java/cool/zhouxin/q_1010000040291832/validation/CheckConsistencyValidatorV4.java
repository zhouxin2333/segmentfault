package cool.zhouxin.q_1010000040291832.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2021/7/9 17:08
 */
public class CheckConsistencyValidatorV4 implements ConstraintValidator<CheckConsistencyV4, Object> {

    // 这是一个小缓存，因为Class中哪些Field有ConsistencyTag注解是固定的
    private static final Map<Class, List<Field>> cache = new HashMap<>();

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        List<Field> consistencyFields = cache.computeIfAbsent(object.getClass(), this::getAllConsistencyFields);

        // 若这里为空或者有@ConsistencyTag注解的注解的字段不是偶数个，那就不处理直接返回true
        if (consistencyFields.isEmpty() || consistencyFields.size() % 2 != 0) return true;

        Map<Annotation, List<ConsistencyContext>> annotationListMap = consistencyFields.stream()
                .map(field -> this.toContext(field, object))
                .collect(Collectors.groupingBy(ConsistencyContext::getAnnotation));

        // 这里是展示所有的报错的写法
        List<Annotation> annotations = annotationListMap.entrySet().stream()
                .filter(entry -> this.doNotEqual(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        boolean isValid = annotations.isEmpty();
        if (!isValid) {
            context.disableDefaultConstraintViolation();

            annotations.stream()
                       .map(AnnotationUtils::getAnnotationAttributes)
                       .map(map -> map.get(ConsistencyTag.METHOD_KEY).toString())
                       .forEach(message -> context.buildConstraintViolationWithTemplate(message).addConstraintViolation());
        }

        // 这里是展示只返回其中一个报错的写法
//        Optional<Annotation> annotationOptional = annotationListMap.entrySet().stream()
//                .filter(entry -> this.doNotEqual(entry.getValue()))
//                .map(Map.Entry::getKey)
//                .findFirst();
//
//        boolean isValid = !annotationOptional.isPresent();
//        if (!isValid) {
//            context.disableDefaultConstraintViolation();
//
//            Annotation annotation = annotationOptional.get();
//            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
//            String message = annotationAttributes.get(ConsistencyTag.METHOD_KEY).toString();
//            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
//        }
        return isValid;
    }

    private List<Field> getAllConsistencyFields(Class tClass) {
        List<Field> consistencyFields = new ArrayList<>();
        ReflectionUtils.doWithFields(tClass, consistencyFields::add,
                field -> AnnotatedElementUtils.isAnnotated(field, ConsistencyTag.class));
        return consistencyFields;
    }

    private boolean doNotEqual(List<ConsistencyContext> contexts) {
        return !(contexts.size() == 2 && Objects.equals(contexts.get(0).getValue(), contexts.get(1).getValue()));
    }

    private ConsistencyContext toContext(Field field, Object object) {
        Annotation[] annotations = field.getAnnotations();

        Annotation consistencyTagAnnotation = Stream.of(annotations)
                .filter(annotation -> AnnotatedElementUtils.isAnnotated(
                        AnnotatedElementUtils.forAnnotations(annotation), ConsistencyTag.class))
                .findFirst()
                .get();
        ReflectionUtils.makeAccessible(field);
        Object value = ReflectionUtils.getField(field, object);
        return ConsistencyContext.builder()
                                 .field(field)
                                 .value(value)
                                 .annotation(consistencyTagAnnotation)
                                 .build();

    }

    @Data
    @Builder
    static class ConsistencyContext {
        private Field field;
        private Object value;
        private Annotation annotation;
    }
}
