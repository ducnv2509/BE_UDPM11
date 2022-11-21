package ecom.udpm.vn.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderReturnResponse {
    private Long id;
    private Long account_id;
    private String account_name;
    private String note;
    private Long id_order_purchase;
    private Date createDate;
    private int statusReturn;
    private BigDecimal totalPriceReturn;
    private BigDecimal totalQuantityReturn;
}
