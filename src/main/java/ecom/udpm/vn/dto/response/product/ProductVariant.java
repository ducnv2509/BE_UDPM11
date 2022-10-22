package ecom.udpm.vn.dto.response.product;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductVariant {
    private Integer id;
    private String name;
    private String option1;
    private String option2;
    private String option3;
    private String wholesale_price;
    private String sale_price;
    private String import_price;
    private String code;
    private String image;
    private Integer quantity;
    private Integer product_id;
}
