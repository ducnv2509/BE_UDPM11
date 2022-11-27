package ecom.udpm.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPurchaseCreateRequest {
    private Long id_account;
    private String address;
    private String note;
}
