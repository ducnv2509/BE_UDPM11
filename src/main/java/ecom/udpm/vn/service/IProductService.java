package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.request.ProductAddRequest;
import ecom.udpm.vn.dto.request.ProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


public interface IProductService {

    ProductAddRequest save(ProductAddRequest request, BindingResult bindingResult);

//    ProductAddRequest save(ProductAddRequest request, BindingResult bindingResult);

}
