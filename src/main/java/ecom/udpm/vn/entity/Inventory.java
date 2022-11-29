package ecom.udpm.vn.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "inventories")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Inventory   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false, length = 100)
    @NotNull
    @NotBlank
    private String code;

    @Lob
    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @NotNull
    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;


    @Column(name = "size", nullable = false)
    private Boolean size = false;

    @Column(name = "create_at", nullable = false)
    @CreatedDate
    private Timestamp createAt;

    @Column(name = "update_at")
    @LastModifiedDate
    private Timestamp updateAt;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete= false;



}