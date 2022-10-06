package ecom.udpm.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @CreatedDate
    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;


    private String thumbnail;

    @JoinColumn(name = "supplier_id")
    private Integer supplierId;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;
}