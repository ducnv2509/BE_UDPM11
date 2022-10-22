package ecom.udpm.vn.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetAllProduct {
    private Integer id;
    private String name, image, wholesale_price;
}
