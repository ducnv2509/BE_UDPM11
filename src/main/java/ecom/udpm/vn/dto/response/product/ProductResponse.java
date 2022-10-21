package ecom.udpm.vn.dto.response.product;

import ecom.udpm.vn.dto.response.CategoryResponse;
import ecom.udpm.vn.dto.response.OptionResponse;
import ecom.udpm.vn.dto.response.VariantResponse;
import ecom.udpm.vn.entity.ProductVariantImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {
    private Integer id;
    private String name;
    private String code;
    private String description;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String thumbnail;
    private BigDecimal inventoryQuantity;

    private List<ProductVariantImage> images = new ArrayList<>();
    private List<VariantResponse> variants = new ArrayList<>();
    private List<OptionResponse> options = new ArrayList<>();
    private List<CategoryResponse> categories = new ArrayList<>();


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
