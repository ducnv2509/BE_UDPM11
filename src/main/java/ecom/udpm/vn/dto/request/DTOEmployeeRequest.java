package ecom.udpm.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DTOEmployeeRequest {
    private String password;
    private String address;
    private String email;
    private String fullName;
    private String phone;
    private int roleId;
    private boolean gender;

}
