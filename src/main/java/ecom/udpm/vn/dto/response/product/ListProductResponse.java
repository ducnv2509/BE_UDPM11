package ecom.udpm.vn.dto.response.product;

import ecom.udpm.vn.dto.model.Metadata;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ListProductResponse {
    private List<ProductResponse> products;
    private Metadata metadata;

    public ListProductResponse(List<ProductResponse> products, Metadata metadata) {
        this.setProducts(products);
        this.setMetadata(metadata);
    }
}
