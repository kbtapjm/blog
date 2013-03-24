package kr.co.blog.common.validation;

import kr.co.blog.domain.User;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * User 데이터 검증  
 * @author kbtapjm
 *
 */
public class UserValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return (User.class.isAssignableFrom(clazz));
    }
    
    public void validate(Object target, Errors errors) {
        User user = (User)target;
        ValidationUtils.rejectIfEmpty(errors, "userName", "field.required");
        
        if(user.getBirthday().length() != 8) {
            errors.rejectValue("birthday", "field.min", new Object[]{8}, null);
        }
    }
    
    
}
