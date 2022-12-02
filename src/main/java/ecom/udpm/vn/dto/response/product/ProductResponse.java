package ecom.udpm.vn.dto.response.product;

import ecom.udpm.vn.entity.Category;
import ecom.udpm.vn.entity.Product;
import ecom.udpm.vn.entity.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {
    private Product product;
    private List<ProductVariant> variants;
    private List<Category> categories;

//    public void addVariant(VariantResponse variantResponse) {
//        this.variants.add(variantResponse);
//    }
//    public void addOption(OptionResponse optionResponse) {
//        this.options.add(optionResponse);
//    }
//    public void addImage(ProductVariantImage image) {
//        this.images.add(image);
//    }
//    public void addCategory(CategoryResponse category) {
//        this.categories.add(category);
//    }

}
