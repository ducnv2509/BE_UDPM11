package ecom.udpm.vn.dto.response.ImportInvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DetailsReturnImportResponse {
    private Integer detailsImportId;
    private String code;
    private String name;
    private Integer quantity;
    private BigDecimal importPrice;
    private BigDecimal totalPrice;
}
