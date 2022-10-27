package ecom.udpm.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartRequest {

    private Integer id;
    private Integer account_id;
    private String account_name;
    private Integer total_quantity;
    private Double total_price;
}
