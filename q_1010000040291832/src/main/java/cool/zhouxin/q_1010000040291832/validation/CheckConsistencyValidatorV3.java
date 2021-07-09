package cool.zhouxin.q_1010000040291832.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhouxin
 * @since 2021/7/9 17:08
 */
public class CheckConsistencyValidatorV3 implements ConstraintValidator<CheckConsistencyV3, Object> {

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        List<Field> consistencyFields = new ArrayList<>();
        ReflectionUtils.doWithFields(object.getClass(), consistencyFields::add,
                field -> field.isAnnotationPresent(ConsistencyGroup.class));
        // 若这里为空或者有@ConsistencyGroup注解的字段不是2个，那就不处理直接返回true
        if (consistencyFields.isEmpty() || consistencyFields.size() != 2) return true;

        List<Object> valueList = consistencyFields.stream()
                                                  .peek(ReflectionUtils::makeAccessible)
                                                  .map(field -> ReflectionUtils.getField(field, object))
                                                  .collect(Collectors.toList());
        return Objects.equals(valueList.get(0), valueList.get(1));
    }
}
