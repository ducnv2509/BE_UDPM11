package ecom.udpm.vn.dto.response.ImportInvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ImportResponse {
    private String code;
    private String supplierCode;
    private String inventoryName;
    private BigDecimal totalPrice;
    private Boolean isDone;
    private Boolean isPaid;
    private Boolean isImport;
    private Boolean isReturn;
    private String userName;
    private String deliveryDate;
}
