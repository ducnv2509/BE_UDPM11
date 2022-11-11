package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.OrderPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface AdminManagerOrderRepo extends JpaRepository<OrderPurchase, Long> {

    @Modifying
    @Transactional
    @Query("update OrderPurchase op set op.status =:status_id where op.id in(:listId)")
    void updateOrderMultipleByStatus(Integer status_id, List<Long> listId);
}
