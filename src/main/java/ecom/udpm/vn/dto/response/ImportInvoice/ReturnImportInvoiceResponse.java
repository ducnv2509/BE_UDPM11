package ecom.udpm.vn.dto.response.ImportInvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReturnImportInvoiceResponse {
    private Integer id;
    private Timestamp createDate;
    private Integer importId;
    private List<DetailsReturnImportResponse> detailsReturnImportResponseList;
    private BigDecimal totalPrice;
    private Integer accountId;
    private String fullName;
    private String phoneNumber;
}
