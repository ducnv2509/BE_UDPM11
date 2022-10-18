package ecom.udpm.vn.dto.response;

import ecom.udpm.vn.dto.request.ProductVariantDTO;
import ecom.udpm.vn.entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryResponse {
    private Inventory inventory;
    private List<ProductVariantDTO> productVariants;
    private Integer totalProductVariant;
    private Integer countProductVariant;

}
