package ecom.udpm.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductVariant  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "image")
    private String image;

    @Column(name = "wholesale_price", nullable = false, precision = 20, scale = 2,columnDefinition = " default (0)")
    private BigDecimal wholesalePrice;

    @Column(name = "sale_price", nullable = false, precision = 20, scale = 2, columnDefinition = " default (0)")
    private BigDecimal salePrice;
    @Column(name = "import_price", nullable = false, precision = 20, scale = 2, columnDefinition = " default (0)")

    private BigDecimal importPrice;
    @Column(name = "code", nullable = false, length = 100)
    private String code;

    private String option1;
    private String option2;
    private String option3;

    private Integer totalQuantity; // ->> from sum of inventories
    @NotNull
    @Size(max = 250)
    private String name;
    @NotNull
    private Long productId;

    @Column(name = "position")
    private Boolean position;

    @CreatedDate
    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Column(name = "is_delete")
    private Boolean isDelete = false;
}
