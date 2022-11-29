package ecom.udpm.vn.exception;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InvalidInputException extends RuntimeException{
    private List<String> message;

    public String getMessage() {
        return String.join(", ", message);
    }
}
