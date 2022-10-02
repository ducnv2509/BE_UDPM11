package ecom.udpm.vn.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "supplier")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "code", length = 100)
    @Size(max = 100,message = "code can not be more then 100 character")
    private String code;

    @Lob
    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name can not be null")
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    @Email(message = "Email not valid")
    @Size(max = 100,message = "code can not be more then 100 character")
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    @NotEmpty(message = "Phone can not be null")
    @Size(max = 20,message = "Phone number cant be more than 20")
    private String phone;

    @Lob
    @Column(name = "address", nullable = false)
    @NotEmpty(message = "Address can not be null")
    private String address;

    @CreatedDate
    @Column(name = "create_at", nullable = false)
    private Timestamp createdAt;
    @LastModifiedDate
    @Column(name = "update_at", nullable = false)
    private Timestamp updateAt;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;

//
//    @JoinColumn(name = "account_id",insertable = false,updatable = false, nullable = false)
//    private Integer accountId;
    @Column(name = "status_transaction", nullable = false)
    private Boolean statusTransaction = true;
}
