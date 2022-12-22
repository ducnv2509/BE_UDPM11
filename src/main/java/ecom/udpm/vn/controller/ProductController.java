package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.request.ProductAddDTO;
import ecom.udpm.vn.dto.request.ProductAddRequest;
import ecom.udpm.vn.dto.request.ProductFilter;
import ecom.udpm.vn.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/products")
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority('admin','employee')")
public class ProductController {

    private final IProductService productService;

    @PostMapping()
    public ResponseEntity create(@RequestBody @Valid ProductAddRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(productService.save(request, bindingResult));
    }

    @GetMapping()
    public ResponseEntity getAllProduct() {
        return ResponseEntity.ok(this.productService.getAllProduct());
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity getProductByOption(@RequestParam String op1, @RequestParam String op2, @RequestParam String op3, @PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductByOption(op1, op2, op3, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOptionProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getOptionProduct(id));
    }

    @PostMapping("/count")
    public ResponseEntity countProductByFilter(@RequestBody @Valid ProductFilter filter, BindingResult bindingResult) {

        return ResponseEntity.ok(productService.countProductByFilter(filter, bindingResult));

    }

    @PostMapping("/filter")
    public ResponseEntity filterProducts(@RequestBody @Valid ProductFilter filter, BindingResult bindingResult) {
        return ResponseEntity.ok(productService.productFilter(filter, bindingResult));

    }
    @GetMapping("/admin/{id}")
    public ResponseEntity findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        productService.deleteById(id);
    }

    @DeleteMapping()
    public void deleteProducts(@RequestBody List<Long> listId) {
        productService.deleteProductsById(listId);
    }


    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Valid ProductAddDTO entity, BindingResult bindingResult) {
        return ResponseEntity.ok(productService.update(entity, bindingResult));
    }


    @DeleteMapping("/variants/{id}")
    public void deleteVariant(@PathVariable Long id) {
        productService.deleteVariantById(id);
    }

    @PostMapping("/variants")
    public void deleteVariant(@RequestBody List<Long>listId) {
        productService.deleteVariantsById(listId);
    }

}
