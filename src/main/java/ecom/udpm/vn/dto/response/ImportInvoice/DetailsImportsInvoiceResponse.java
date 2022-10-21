package ecom.udpm.vn.dto.response.ImportInvoice;

import ecom.udpm.vn.entity.Import;
import ecom.udpm.vn.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DetailsImportsInvoiceResponse {
    private Import anImport;
    private Supplier supplier;
    private String inventoryName;
}
