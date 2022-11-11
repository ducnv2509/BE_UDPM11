package ecom.udpm.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartRequest2 {
    private Long id;
    private String opt1;
    private String opt2;
    private String opt3;
    private Long product_id;
    private Integer quantity;
}
