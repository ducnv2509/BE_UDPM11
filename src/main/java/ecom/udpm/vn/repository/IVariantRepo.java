package ecom.udpm.vn.repository;

import ecom.udpm.vn.dto.request.ProductVariantDTO;
import ecom.udpm.vn.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVariantRepo extends JpaRepository<ProductVariant,Long> {

    @Query(value = "select * from  product_variant  order by id DESC limit 1",nativeQuery = true)
    ProductVariant getTop();

    @Query(value = "select * from product inner join product_variant pv on product.id = pv.product_id where position = true\n", nativeQuery = true)
    ProductVariantDTO findByIdProduct();

    @Query("select p from ProductVariant p where p.productId = :id and p.isDelete= false ")
    List<ProductVariant> findAllByProductId(@Param("id") Long id);

}
