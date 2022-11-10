package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.OrderPurchase;

import java.util.List;

public interface AdminManagerOrderService {
    List<OrderPurchase> showOrderCustomer();

    void updateMultiOrderCustomer(List<Long> listId, Integer statusId);

    OrderPurchase updateOrderCustomer(Long id, Integer statusId);
}
