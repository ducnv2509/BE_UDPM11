package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.request.ProductVariantDTO;
import ecom.udpm.vn.service.IProductVariantService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
@CrossOrigin("*")
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority('admin','employee')")
public class ProductVariantController {
    private final IProductVariantService productVariantService;

    @GetMapping("/findProductVariant")
    public List<ProductVariantDTO> findProductVariantToImportInvoice(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String searchValue) {
        return productVariantService.findAllProductVariantDTO(pageNumber, pageSize, searchValue);
    }

    @GetMapping("/count-total")
    public Integer countToImportInvoice(@RequestParam String searchValue) {
        return productVariantService.countTotalPage(searchValue);
    }


}
