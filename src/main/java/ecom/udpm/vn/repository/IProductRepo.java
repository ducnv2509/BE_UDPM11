package ecom.udpm.vn.repository;

import ecom.udpm.vn.dto.response.product.ProductVariant;
import ecom.udpm.vn.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select * from  product  order by id DESC limit 1", nativeQuery = true)
    Product getTop();

    @Query(value = "select sum(ipv.quantity) as quantity, pv.productId ,pv.id, pv.name, pv.image, pv.wholesalePrice from ProductVariant  pv join InventoriesProductVariant  ipv on pv.id = ipv.product_variant_id where pv.productId=:id")
    ProductVariant getDetailProduct(Integer id);

}
