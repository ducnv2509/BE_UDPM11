package ecom.udpm.vn.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StaffException extends RuntimeException {
    private String message;
}
