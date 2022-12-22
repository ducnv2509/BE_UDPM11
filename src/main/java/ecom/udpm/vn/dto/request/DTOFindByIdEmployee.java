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
public class DTOFindByIdEmployee {
    private Integer id;
    private String address;
    private String email;
    private String full_name;
    private String phone;
    private String code;
    private boolean gender;
    private Date create_at;
    private boolean is_delete;
    private Date update_at;
    private String username;
    private int role_id;
    private int id_account;

}
