package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.model.Metadata;
import ecom.udpm.vn.dto.request.ProductAddDTO;
import ecom.udpm.vn.dto.request.ProductAddRequest;
import ecom.udpm.vn.dto.request.ProductFilter;
import ecom.udpm.vn.dto.response.product.GetAllProduct;
import ecom.udpm.vn.dto.response.product.ListProductResponse;
import ecom.udpm.vn.dto.response.product.ProductFilterResponse;
import ecom.udpm.vn.dto.response.product.ProductResponse;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface IProductService {

    ProductAddRequest save(ProductAddRequest request, BindingResult bindingResult);

//    ProductAddRequest save(ProductAddRequest request, BindingResult bindingResult);

    ListProductResponse getProducts(Metadata metadata);

    List<GetAllProduct> getAllProduct();


    //    void getOption
    Map<String, Object> getOptionProduct(Integer id);

    Map<String, Object> getProductByOption(String op1, String op2, String op3, Integer id);

    Map<String, Object> getDetailProduct(Integer id);


    List<ProductFilterResponse> productFilter(ProductFilter filter, BindingResult bindingResult);

    Integer countProductByFilter(ProductFilter filter, BindingResult bindingResult);

    void deleteVariantById(Long id);

    void deleteVariantsById(List<Long> listId);

    void deleteProductsById(List<Long> listId);

    @Transactional(rollbackOn = SQLException.class)
    ProductResponse update(ProductAddDTO request, BindingResult bindingResult);
    ProductResponse findById(Long integer);

    void deleteById(Long id);
}
