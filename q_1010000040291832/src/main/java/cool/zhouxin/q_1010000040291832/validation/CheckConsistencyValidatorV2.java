package cool.zhouxin.q_1010000040291832.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author zhouxin
 * @since 2021/7/9 17:08
 */
public class CheckConsistencyValidatorV2 implements ConstraintValidator<CheckConsistencyV2, Object> {

    private String fieldName1;
    private String fieldName2;

    /**
     * 这个方法也是{@link ConstraintValidator}中的方法
     * 因为咱们注解中有额外的属性，所以需要这个方法先来初始化我们需要的值
     */
    @Override
    public void initialize(CheckConsistencyV2 constraintAnnotationV2) {
        this.fieldName1 = constraintAnnotationV2.fieldName1();
        this.fieldName2 = constraintAnnotationV2.fieldName2();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        // 这里BeanWrapper只是spring的一种内部接入Java Bean的方式
        // 你也可以选自己喜欢的方式，比如Apache的BeanUtils，我这里就懒得引包了
        BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        Object value1 = beanWrapper.getPropertyValue(this.fieldName1);
        Object value2 = beanWrapper.getPropertyValue(this.fieldName2);
        return Objects.equals(value1, value2);
    }
}
