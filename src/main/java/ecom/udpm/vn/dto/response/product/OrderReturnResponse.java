package ecom.udpm.vn.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderReturnResponse {
    private String name;
    private String image;
    private String optionProduct;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
