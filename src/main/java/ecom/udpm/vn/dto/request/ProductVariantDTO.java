package ecom.udpm.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductVariantDTO {
    private Integer id;
    private String code;
    private String name;
    private String image;
    private Integer quantity;
    private BigDecimal importPrice;
    private Integer minQuantity;
    private Timestamp createAt;

}
