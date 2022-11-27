package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.OrderPurchaseItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOderPurchaseItemRepo extends JpaRepository<OrderPurchaseItems, Long> {
//    @Query("select o from OrderPurchaseItems o  " +
//            "where o.id_order = (select p.id from OrderPurchase p order by p.id desc)")
//    Integer getQuantity(Integer id);
//
    @Query("select o from OrderPurchaseItems o join ProductVariant p on o.id_product = p.id")
    List<OrderPurchaseItems> findAllByIdOrder(Long idOrder);
}
