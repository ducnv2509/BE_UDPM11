package ecom.udpm.vn.service.impl;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecom.udpm.vn.dto.model.Metadata;
import ecom.udpm.vn.dto.request.ProductAddRequest;
import ecom.udpm.vn.dto.request.ProductVariantDTO;
import ecom.udpm.vn.dto.response.product.ListProductResponse;
import ecom.udpm.vn.dto.response.product.ProductResponse;
import ecom.udpm.vn.entity.*;
import ecom.udpm.vn.repository.ICategoryProduct;
import ecom.udpm.vn.repository.IProductRepo;
import ecom.udpm.vn.repository.IVariantRepo;
import ecom.udpm.vn.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService implements IProductService {

    private final IProductRepo productRepo;

    private final IVariantRepo variantRepo;

    private final ObjectMapper objectMapper;
    private final ICategoryProduct categoryProductRepo;

    @Override
    @Transactional(rollbackOn = SQLException.class)
    public ProductAddRequest save(ProductAddRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new RuntimeException("Input Invalid");
        Product product = request.getProduct();
        product.setCode(getNewCode());
        request.setProduct(productRepo.save(product));

        for (ProductVariant variant : request.getVariants()) {
            variant.setProductId(request.getProduct().getId());
            variant.setCode(getNewVariantCode());
            variant.setIsDelete(false);
            variantRepo.save(variant);
        }
        for (Category category : request.getCategories()) {
            CategoriesProductId id = new CategoriesProductId(request.getProduct().getId(), category.getId());
            CategoriesProduct categoriesProduct = new CategoriesProduct(id, request.getProduct(), category);
            categoryProductRepo.save(categoriesProduct);
        }
        return request;
    }

    @Override
    public ListProductResponse getProducts(Metadata metadata) {
        return null;
    }


    public String getNewCode() {
        String newCode = "SP";
        Product product = productRepo.getTop();
        if (product == null) return "SP1";
        newCode = newCode + (product.getId() + 1);
        return newCode;
    }
    public String getNewVariantCode() {
        String newCode = "SPV";
        ProductVariant variant = variantRepo.getTop();

        if (variant == null) return "SPV1";

        newCode = newCode + (variant.getId() + 1);
        return newCode;
    }
}
