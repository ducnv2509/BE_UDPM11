package ecom.udpm.vn.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AlreadyExistsException extends RuntimeException{
    private String message;
}
