package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.response.OrderPurchaseItem;
import ecom.udpm.vn.entity.OrderPurchase;
import ecom.udpm.vn.entity.OrderPurchaseItems;

import java.util.List;

public interface AdminManagerOrderService {
    List<OrderPurchase> showOrderCustomer();

    List<OrderPurchaseItem> showOrderItemByIdOrder(Long idOrder);

    void updateMultiOrderCustomer(List<Long> listId, Integer statusId);

    OrderPurchase updateOrderCustomer(Long id, Integer statusId);
}
