package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.OrderPurchaseItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOderPurchaseItemRepo extends JpaRepository<OrderPurchaseItems, Long> {
//    @Query("select o from OrderPurchaseItems o  " +
//            "where o.id_order = (select p.id from OrderPurchase p od by p.id desc)")
//    Integer getQuantity(Integer id);
//
//    List<OrderPurchaseItems> getNewListOrderItems();
}
