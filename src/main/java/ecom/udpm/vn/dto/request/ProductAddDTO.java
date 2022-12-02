package ecom.udpm.vn.dto.request;

import ecom.udpm.vn.entity.Category;
import ecom.udpm.vn.entity.Product;
import ecom.udpm.vn.entity.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductAddDTO {
    private Product product;
    private List<ProductVariant> variants;
    private List<Category> categories;
}