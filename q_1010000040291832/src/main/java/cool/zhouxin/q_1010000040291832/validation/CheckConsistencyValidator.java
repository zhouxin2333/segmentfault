package cool.zhouxin.q_1010000040291832.validation;

import cool.zhouxin.q_1010000040291832.pojo.UserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author zhouxin
 * @since 2021/7/9 17:08
 */
public class CheckConsistencyValidator implements ConstraintValidator<CheckConsistency, UserRequest> {

    @Override
    public boolean isValid(UserRequest request, ConstraintValidatorContext context) {
//        context.disableDefaultConstraintViolation();
//        context.buildConstraintViolationWithTemplate("密码不一致啦").addConstraintViolation();
        return Objects.equals(request.getPassword(), request.getPasswordAgain());
    }
}
