package com.lrm.util;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 */
@Component("hibernateValidatorUtils")
public class HibernateValidatorUtils {
    /**
     *
     * 校验页面字段信息
     * @param object
     * @param
     * @return
     */
    public boolean checkUserForm(Object object,   RedirectAttributes attributes){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> validate = validator.validate(object);
        Iterator<ConstraintViolation<Object>> iterator = validate.iterator();
        Map<String,String> map = new HashMap<>();

        while (iterator.hasNext()){
            ConstraintViolation<Object> next = iterator.next();
            //字段信息
            String property = next.getPropertyPath().toString();
            //错误信息
            String message = next.getMessage();
            map.put(property,message);
        }
        attributes.addFlashAttribute("error",map);
        return validate.size()==0;
    }
}
