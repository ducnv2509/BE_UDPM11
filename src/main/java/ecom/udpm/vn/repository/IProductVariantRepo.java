package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductVariantRepo extends JpaRepository<ProductVariant,Long> {

    @Query(value = "call get_productvariant_byname(?1,?2)", nativeQuery = true)
    List<ProductVariant> listProductVariantByName(Long id, String name);
}
