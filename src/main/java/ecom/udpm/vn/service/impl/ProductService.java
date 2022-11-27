package ecom.udpm.vn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecom.udpm.vn.dto.model.Metadata;
import ecom.udpm.vn.dto.request.ProductAddDTO;
import ecom.udpm.vn.dto.request.ProductAddRequest;
import ecom.udpm.vn.dto.request.ProductFilter;
import ecom.udpm.vn.dto.response.product.*;
import ecom.udpm.vn.entity.*;
import ecom.udpm.vn.entity.ProductVariant;
import ecom.udpm.vn.repository.ICategoryProduct;
import ecom.udpm.vn.repository.ICategoryRepo;
import ecom.udpm.vn.repository.IProductRepo;
import ecom.udpm.vn.repository.IVariantRepo;
import ecom.udpm.vn.service.IProductService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class ProductService implements IProductService {

    private final IProductRepo productRepo;
    private final JdbcTemplate jdbcTemplate;
    private final IVariantRepo variantRepo;

    private final ObjectMapper objectMapper;
    private final ICategoryProduct categoryProductRepo;
    private final ICategoryRepo categoryRepo;


    @Override
    @Transactional(rollbackOn = SQLException.class)
    public ProductAddRequest save(ProductAddRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new RuntimeException("Input Invalid");
        Product product = request.getProduct();
//        request.getVariants().get(0).setPosition(true);
//        variantRepo.save(request.getVariants().get(0));
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

    @SneakyThrows
    @Override
    public List<GetAllProduct> getAllProduct() {
        String query = "call getAllProduct()";
        Integer id = null;
        String name = null;
        String price = null;
        String image = null;
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(GetAllProduct.class));
    }

    @Override
    public Map<String, Object> getOptionProduct(Integer id) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> json = new HashMap<>();
        String query = "call getOptionProduct(?)";
        String queryDetail = "call getDetailProduct(?)";
        List<OptionProduct> p = jdbcTemplate.query(query, new BeanPropertyRowMapper(OptionProduct.class), id);
        ecom.udpm.vn.dto.response.product.ProductVariant productVariant = (ecom.udpm.vn.dto.response.product.ProductVariant) jdbcTemplate.query(queryDetail, new BeanPropertyRowMapper(ecom.udpm.vn.dto.response.product.ProductVariant.class), id).get(0);
        String option1 = p.get(0).getOP1();
        String option2 = p.get(0).getOP2();
        String option3 = p.get(0).getOP3();

        json.put("name", productVariant.getName().split("-")[0]);
        json.put("image", productVariant.getImage());
        json.put("quantity", productVariant.getQuantity());
        json.put("id", productVariant.getId());
        json.put("product_id", productVariant.getProduct_id());
        json.put("price", productVariant.getWholesale_price());
        json.put("option1", productVariant.getOption1());
        json.put("option2", productVariant.getOption2());
        json.put("option3", productVariant.getOption3());

        map.put("InfoProduct", json);
        map.put("Option1", option1.split(","));
        map.put("Option2", option2.split(","));
        map.put("Option3", option3.split(","));
        return map;
    }

    @Override
    public Map<String, Object> getProductByOption(String op1, String op2, String op3, Integer id) {
        String query = "call getProductByOption(?, ?, ?, ?)";
        List<ecom.udpm.vn.dto.response.product.ProductVariant> p = jdbcTemplate.query(query, new BeanPropertyRowMapper(ecom.udpm.vn.dto.response.product.ProductVariant.class), op1, op2, op3, id);
        Map<String, Object> map = new HashMap<>();
        map.put("id", p.get(0).getId());
        map.put("name", p.get(0).getName().split("-")[0]);
        map.put("option1", p.get(0).getOption1());
        map.put("option2", p.get(0).getOption2());
        map.put("option3", p.get(0).getOption3());
        map.put("wholesale_price", p.get(0).getWholesale_price());
        map.put("sale_price", p.get(0).getSale_price());
        map.put("code", p.get(0).getCode());
        map.put("image", p.get(0).getImage());
        return map;
    }

    @Override
    public Map<String, Object> getDetailProduct(Integer id) {
        return null;
    }

    @Override
    public List<ProductFilterResponse> productFilter(ProductFilter filter, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) throw new RuntimeException("Invalid input ");
        String query = "call filter_product(?,?,?,?,?,?)";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(ProductFilterResponse.class),
                filter.getKey(), filter.getSortBy(), filter.getIsDesc(), filter.getPage(), filter.getSize(), filter.getIsDelete());
    }

    @Override
    public Integer countProductByFilter(ProductFilter filter, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new RuntimeException("Invalid input ");
//        String query="call filter_product(?,?,?,?,?,?)";
//      Integer total=jdbcTemplate.query(query,new BeanPropertyRowMapper(Integer.class),
//                filter.getKey(),filter.getSortBy(),filter.getIsDesc(),filter.getPage(),filter.getSize(),filter.getIsDelete());
//
        return productRepo.countProductByFilter(filter.getKey(), filter.getSortBy(), filter.getIsDesc(), filter.getPage(), filter.getSize(), filter.getIsDelete());
    }

    @Override
    @Transactional(rollbackOn = SQLException.class)
    public void deleteVariantById(Long id) {
        var variant = variantRepo.findById(id).orElseThrow(() -> new RuntimeException("Id is not exist"));
        variant.setIsDelete(true);
        variantRepo.save(variant);
    }

    @Override
    @Transactional(rollbackOn = SQLException.class)
    public void deleteVariantsById(List<Long> listId) {
        var variants = variantRepo.findAllById(listId);
        for (ProductVariant variant : variants) {
            variant.setIsDelete(true);
        }
        variantRepo.saveAll(variants);


    }

    @Override
    @Transactional(rollbackOn = SQLException.class)
    public void deleteProductsById(List<Long> listId) {
        var products = productRepo.findAllById(listId);
        for (Product product : products) {
            product.setIsDelete(true);
            deleteVariantsByProductId(product.getId());
        }
        productRepo.saveAll(products);
    }

    public  void deleteVariantsByProductId(Long productId)
    {
        String query="update product_variants set is_delete=true where product_id=?";
        jdbcTemplate.update(query,productId);
    }
    @Override
    @Transactional(rollbackOn = SQLException.class)
    public ProductResponse update(ProductAddDTO request, BindingResult bindingResult) {
        request.getProduct().setUpdateAt(Timestamp.valueOf(LocalDateTime.now()));
        productRepo.save(request.getProduct());
        variantRepo.saveAll(request.getVariants());
        categoryProductRepo.deleteAllByProductId(request.getProduct().getId());
        for (Category category : request.getCategories()) {
            String q = "insert into categories_products values (?,?)";
            jdbcTemplate.update(q, category.getId(),request.getProduct().getId());
        }
        return findById(request.getProduct().getId());
    }
    @Override
    public ProductResponse findById(Long id) {
        ProductResponse reponse = new ProductResponse(productRepo.findById(id).get(), variantRepo.findAllByProductId(id)
                , categoryRepo.findAllByProductId(id));
        return reponse;
    }

    @Override
    public void deleteById(Long id) {
        productRepo.deleteById(id);
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
