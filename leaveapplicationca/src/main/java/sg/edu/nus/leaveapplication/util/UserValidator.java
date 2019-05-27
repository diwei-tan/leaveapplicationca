package sg.edu.nus.leaveapplication.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.nus.leaveapplication.model.Credentials;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Credentials.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Credentials user = (Credentials) o;
        //validation on username
        if(user.getUsername().isEmpty()) {
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        } else if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        } else if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }
        //validation on password
        if(user.getPassword().isEmpty()) {
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        }else if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        //validation that confirm password is same as password
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        //validation for employee name
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee.name", "NotEmpty");
        //validation for leaveEntitled
        if(user.getEmployee().getLeaveEntitled()<0 || user.getEmployee().getLeaveEntitled()%1!=0) {
        	errors.rejectValue("employee.leaveEntitled", "CannotBeNegativeOrDecimal");
        }
        //validation for compensation hours
        if(user.getEmployee().getCompensationhours()<0 || user.getEmployee().getCompensationhours()%1!=0) {
        	errors.rejectValue("employee.compensationhours", "CannotBeNegativeOrDecimal");
        }
        //validation for phone number
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employee.contact", "NotEmpty");
        //validation for email
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(!user.getEmployee().getEmail().matches(regex)) {
        	errors.rejectValue("employee.email", "InvalidEmail");
        }
        
    }
}