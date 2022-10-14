package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.request.ProductAddRequest;
import ecom.udpm.vn.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("api/products")
@AllArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping()
    public ResponseEntity create(@RequestBody @Valid ProductAddRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(productService.save(request, bindingResult));
    }

}
