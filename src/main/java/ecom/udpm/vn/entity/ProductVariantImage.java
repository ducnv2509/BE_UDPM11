package ecom.udpm.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "product_variant_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantImage {

    @Id
    private Integer id;
    @Lob
    private String source;

    private Integer productVariantId;

    @CreatedDate
    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Column(name = "is_delete")
    private Boolean isDelete = false;
}
