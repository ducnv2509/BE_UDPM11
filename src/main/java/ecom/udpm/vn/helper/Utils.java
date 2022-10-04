package ecom.udpm.vn.helper;

import ecom.udpm.vn.exception.InvalidInputException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Log4j2
@Component
public class Utils {

    public Timestamp getDate() {
        long millis = System.currentTimeMillis();
        return new Timestamp(millis);
    }
    public InvalidInputException invalidInputException(BindingResult bindingResult){
        List<String> result = new ArrayList<>();
        bindingResult
                .getFieldErrors()
                .forEach(f -> {
                    result.add(f.getField() + ": " + f.getDefaultMessage());
                    log.info(f.getField() + ": " + f.getDefaultMessage());
                });
        throw new InvalidInputException(result);
    }
    public BindingResult getListResult(BindingResult bindingResult, Object request){
        List<FieldError> bindingResultToKeep = bindingResult
                .getFieldErrors()
                .stream().filter(f -> !f.getField().equals("code")).collect(Collectors.toList());
        BindingResult result = new BeanPropertyBindingResult(request, "T");
        bindingResultToKeep.forEach(result::addError);
        return  result;
    }
}
