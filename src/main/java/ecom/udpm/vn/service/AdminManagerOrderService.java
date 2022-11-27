package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.response.OrderPurchaseItem;
import ecom.udpm.vn.dto.response.product.OrderReturnItemResponse;
import ecom.udpm.vn.dto.response.product.OrderReturnResponse;
import ecom.udpm.vn.entity.OrderPurchase;

import java.util.List;

public interface AdminManagerOrderService {
    List<OrderPurchase> showOrderCustomer();
    List<OrderPurchase> showOrderCustomerByStatus(Integer status_id);

    List<OrderPurchaseItem> showOrderItemByIdOrder(Long idOrder);
    List<OrderPurchase> searchOrdersByStatus(String query, int status);
    List<OrderPurchase> searchOrdersAll(String query);
//    select * from order_purchase where (created_time between '2022-11-16 00:00:00' and '2022-11-17 23:59:00') and status = 7
    void updateMultiOrderCustomer(List<Long> listId, Integer statusId, String action_by);

    OrderPurchase updateOrderCustomer(Long id, Integer statusId);

    List<OrderReturnResponse> showOrdeReturn();

    List<OrderReturnResponse> searchOrdersReturn(String querySearch);

    List<OrderReturnItemResponse> showOrderReturnItemByIdOrder(Long idOrder);

    Object updateOrderReturn(Long id, Integer statusId);
}
