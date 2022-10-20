package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends JpaRepository<Product,Long> {

    @Query(value = "select * from  product  order by id DESC limit 1",nativeQuery = true)
    Product getTop();


}
