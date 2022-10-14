package ecom.udpm.vn.dto.response.ImportInvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ImportInvoiceBySupplier {
    private String code;
    private String inventoryName;
    private BigDecimal totalPrice;
    private Boolean isDone;
    private Boolean isPaid;
    private Boolean isImport;
    private Boolean isReturn;
    private Timestamp last;
    private Timestamp first;
}
