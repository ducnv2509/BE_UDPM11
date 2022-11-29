package ecom.udpm.vn.dto.response;

import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPurchaseItem {
    private Integer id;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total_price;
    private String image;
    private String name;
    private String option1;
    private String option2;
    private String option3;
}
