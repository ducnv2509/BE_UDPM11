package ecom.udpm.vn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceJasperReport {

    private Long id;
    private Integer row_num;
    private String account_name;
    private Date created_time;
    private String address_id;
    private String name;
    private Integer quantity;
    private Double price;
    private Integer total_quantity;
    private Double total_price;
    private Double fee_money;
    private String phone;
}
