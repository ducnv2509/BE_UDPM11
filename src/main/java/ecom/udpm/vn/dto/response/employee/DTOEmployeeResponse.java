package ecom.udpm.vn.dto.response.employee;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DTOEmployeeResponse {
    private Integer id;
    private String address;
    private String email;
    private String full_name;
    private String phone;
    private String code;
    private boolean gender;
}
