package ecom.udpm.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InventoriesProductVariantRequest {
    private Integer inventoryId;
    private Integer productVariantId;
    private Integer quantity;
}
