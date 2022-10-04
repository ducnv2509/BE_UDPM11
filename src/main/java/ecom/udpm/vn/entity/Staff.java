package ecom.udpm.vn.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "code", length = 100)
    @Size(max = 100, message = "code không thể nhiều hơn 100 kí tự")
    private String code;
    @Lob
    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Tên không thể bị trống")
    private String name;
    @Column(name = "phone", nullable = false, length = 20)
    @NotEmpty(message = "SĐT không thể bị trống")
    @Size(max = 20, message = "SĐT không thể hơn 20 kí tự")
    private String phone;
    @Lob
    @Column(name = "username", nullable = false)
    @NotEmpty(message = "Username không thể bị trống")
    private String username;
    @Lob
    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password không thể bị trống")
    private String password;
    @Lob
    @Column(name = "email", nullable = false, length = 100)
    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "email không thể hơn 100 kí tự")
    private String email;
    @Lob
    @Column(name = "address", nullable = false)
    @NotEmpty(message = "Địa chỉ không thể bị trống")
    private String address;

    @Column(name = "gender", nullable = false)
    private Boolean gender;
    @Column(name = "dob", nullable = false)
    @NotEmpty(message = "Ngày sinh không thể bị trống")
    private Date dob;
    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;
    @CreatedDate
    @Column(name = "create_at", nullable = false)
    private Timestamp createdAt;
    @LastModifiedDate
    @Column(name = "update_at", nullable = false)
    private Timestamp updateAt;
    private String created_by;
    private String modify_by;
    @Column(name = "role_id", nullable = false)
    @NotEmpty(message = "Rule không thể bị trống")
    private Long roleId;

    @Column(name = "status_account", nullable = false)
    private Boolean statusAccount = true;


}
