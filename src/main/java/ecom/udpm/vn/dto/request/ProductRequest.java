package ecom.udpm.vn.dto.request;

import ecom.udpm.vn.entity.Category;
import ecom.udpm.vn.entity.Option;
import ecom.udpm.vn.entity.Product;
import ecom.udpm.vn.entity.ProductVariant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequest {

    private Product product;
    private List<ProductVariant> variants;
    private List<Category> categories;
    private List<Option> options;
}
