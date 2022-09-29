package ecom.udpm.vn.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String full_name;
    private String phone;
    private String username;
    private String password;
    private String email;
    private String address;
    private Boolean gender;
    private Date dob;
    private Boolean status;
    private Date created_date;
    private Date modify_date;
    private Long created_by;
    private Long modify_by;
    private Long role_id;
}
