package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.request.ProductVariantDTO;
import ecom.udpm.vn.entity.ProductVariant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface IProductVariantService {
    List<ProductVariant> findProductByName(String name);


    List<ProductVariantDTO> findAllProductVariantDTO(Integer pageNumber, Integer pageSize, String searchValue);


    Integer countTotalPage(String searchValue);
}
