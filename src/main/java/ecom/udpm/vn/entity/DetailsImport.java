package ecom.udpm.vn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "details_imports")
@Getter
@Setter
public class DetailsImport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "import_id", insertable = false, updatable = false)
    private Import anImport;
    private Integer import_id;

    @ManyToOne
    @JoinColumn(name = "product_variant_id", insertable = false, updatable = false)
    private ProductVariant productVariant;
    private Integer product_variant_id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false, precision = 20, scale = 2)
    private BigDecimal totalPrice= BigDecimal.ZERO;

    @Column(name = "import_price", precision = 20, scale = 2)
    private BigDecimal importPrice;
}