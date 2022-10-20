package ecom.udpm.vn.repository;

import ecom.udpm.vn.dto.request.ProductVariantDTO;
import ecom.udpm.vn.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVariantRepo extends JpaRepository<ProductVariant,Long> {

    @Query(value = "select * from  product_variant  order by id DESC limit 1",nativeQuery = true)
    ProductVariant getTop();

    @Query(value = "select * from product inner join product_variant pv on product.id = pv.product_id where position = true\n", nativeQuery = true)
    ProductVariantDTO findByIdProduct();
}
