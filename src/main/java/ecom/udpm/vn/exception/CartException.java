package ecom.udpm.vn.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartException extends RuntimeException {
    private String message;
}