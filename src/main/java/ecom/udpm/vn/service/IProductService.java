package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.model.Metadata;
import ecom.udpm.vn.dto.request.ProductAddRequest;
import ecom.udpm.vn.dto.response.product.ListProductResponse;
import org.springframework.validation.BindingResult;

import java.util.Map;


public interface IProductService {

    ProductAddRequest save(ProductAddRequest request, BindingResult bindingResult);

//    ProductAddRequest save(ProductAddRequest request, BindingResult bindingResult);

    ListProductResponse getProducts(Metadata metadata);


    //    void getOption
    Map<String, Object> getOptionProduct(Integer id);

    Map<String, Object> getProductByOption(String op1, String op2, String op3, Integer id);

    Map<String, Object> getDetailProduct(Integer id);

}
