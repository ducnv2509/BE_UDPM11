package ecom.udpm.vn.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class VariantRequest {

    private Integer id;
    private String name;
    private BigDecimal wholesalePrice;
    private BigDecimal importPrice;
    private BigDecimal salePrice;
    private String option1;
    private String option2;
    private String option3;
    private Integer quantity;
    private Integer imageId;
    private Integer position;
    private Instant createdOn;
    private Instant modifiedOn;
    private Integer productId;
}
