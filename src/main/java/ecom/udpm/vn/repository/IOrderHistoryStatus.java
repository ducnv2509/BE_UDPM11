package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.OrderByStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderHistoryStatus extends JpaRepository<OrderByStatusHistory, Long> {
}
