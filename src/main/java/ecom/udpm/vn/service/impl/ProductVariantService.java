package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.request.ProductVariantDTO;
import ecom.udpm.vn.entity.ProductVariant;
import ecom.udpm.vn.service.IProductVariantService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductVariantService implements IProductVariantService {
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;


    @Override
    public List<ProductVariant> findProductByName(String name) {
        return null;
    }

    @Override
    public List<ProductVariantDTO> findAllProductVariantDTO(Integer pageNumber, Integer pageSize, String searchValue) {
        String query = "call db_udpm11_v1.filter_product_variant(?,?,?)";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(ProductVariantDTO.class), pageNumber, pageSize, searchValue);
    }

    @Override
    public Integer countTotalPage(String searchValue) {
        Query queryTotal = entityManager.createNativeQuery
                ("select count(*) as total\n" +
                        "from product_variant\n" +
                        "         inner join product p on product_variant.product_id = p.id\n" +
                        "where p.is_delete = false and product_variant.name like concat('%',?1,'%');").setParameter(1, searchValue);
        Long countResult = queryTotal.getSingleResult() != null ? Long.parseLong(queryTotal.getSingleResult().toString()) : 0;
        if (countResult % 7 == 0) {
            return (int) ((countResult / 7));
        }
        return (int) ((countResult / 7) + 1);
    }
}
