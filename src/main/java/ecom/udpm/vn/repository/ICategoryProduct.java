package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.CategoriesProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface ICategoryProduct extends JpaRepository<CategoriesProduct,Long> {


    @Modifying
    @Query(value = "delete from categories_products where product_id =?1",nativeQuery = true)
    void deleteAllByProductId(Long id);
}
