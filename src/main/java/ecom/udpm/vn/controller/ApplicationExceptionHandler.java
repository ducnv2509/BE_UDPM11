package ecom.udpm.vn.controller;

import ecom.udpm.vn.constant.CodeMessage;
import ecom.udpm.vn.exception.ErrorMessage;
import ecom.udpm.vn.exception.StaffException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(value = {
            StaffException.class
    })
    protected ResponseEntity<ErrorMessage> handleStaffNotFoundException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.builder().code(CodeMessage.STAFF_EXCEPTION).message(exception.getMessage()).build());
    }
}
