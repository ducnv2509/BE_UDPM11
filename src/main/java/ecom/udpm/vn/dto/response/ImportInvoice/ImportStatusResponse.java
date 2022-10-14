package ecom.udpm.vn.dto.response.ImportInvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ImportStatusResponse {
    private String accountName;
    private String fullName;
    private String phoneNumber;
    private String statusName;
    private String statusDesc;
    private String createdAt;
}
