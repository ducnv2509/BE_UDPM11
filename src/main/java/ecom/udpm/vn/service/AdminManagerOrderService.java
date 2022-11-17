package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.response.OrderPurchaseItem;
import ecom.udpm.vn.dto.response.product.OrderReturnItemResponse;
import ecom.udpm.vn.dto.response.product.OrderReturnResponse;
import ecom.udpm.vn.entity.OrderPurchase;

import java.util.List;

public interface AdminManagerOrderService {
    List<OrderPurchase> showOrderCustomer();

    List<OrderPurchaseItem> showOrderItemByIdOrder(Long idOrder);

    void updateMultiOrderCustomer(List<Long> listId, Integer statusId);

    OrderPurchase updateOrderCustomer(Long id, Integer statusId);

    List<OrderReturnResponse> showOrdeReturn();

    List<OrderReturnItemResponse> showOrderReturnItemByIdOrder(Long idOrder);

    Object updateOrderReturn(Long id, Integer statusId);
}
