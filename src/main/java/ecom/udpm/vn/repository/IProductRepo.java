package ecom.udpm.vn.repository;

import ecom.udpm.vn.dto.response.product.ProductVariant;
import ecom.udpm.vn.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select * from  product  order by id DESC limit 1", nativeQuery = true)
    Product getTop();

    @Query(value = "select sum(ipv.quantity) as quantity, pv.productId ,pv.id, pv.name, pv.image, pv.wholesalePrice from ProductVariant  pv join InventoriesProductVariant  ipv on pv.id = ipv.product_variant_id where pv.productId=:id")
    ProductVariant getDetailProduct(Integer id);

//    @Query(value = "Select p.id , pv.image, p.name, pv.wholesalePrice from Product  p join  ProductVariant  pv on p.id = pv.productId where pv.position = true")
//    List<ProductVariant> getAllProduct();

    @Query(value = "call count_product_by_filter(:key,:sortBy,:isDesc,:page,:size,:isDelete)",nativeQuery = true)
    Integer countProductByFilter(@Param("key") String key, @Param("sortBy") String sortBy, @Param("isDesc") Boolean isDesc,
                                 @Param("page") Integer page, @Param("size") Integer size, @Param("isDelete") Boolean isDelete);

}
