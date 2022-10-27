package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.OrderPurchaseItems;

import java.util.List;

public interface IOrderService {
   List<OrderPurchaseItems> createOrder(Long id_account , String address, String note);

}
