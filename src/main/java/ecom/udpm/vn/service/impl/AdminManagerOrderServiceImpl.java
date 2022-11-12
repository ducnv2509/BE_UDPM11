package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.OrderPurchase;
import ecom.udpm.vn.exception.StaffException;
import ecom.udpm.vn.repository.AdminManagerOrderRepo;
import ecom.udpm.vn.service.AdminManagerOrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminManagerOrderServiceImpl implements AdminManagerOrderService {
    @Autowired
    AdminManagerOrderRepo adminManagerOrderRepo;
    @Override
    public List<OrderPurchase> showOrderCustomer() {
        return this.adminManagerOrderRepo.findAll();
    }

    @Override
    public void updateMultiOrderCustomer(List<Long> listId, Integer statusId) {
        listId.forEach(id -> this.adminManagerOrderRepo.findById(id).orElseThrow(() -> new StaffException(("id not found: " + id))));
        this.adminManagerOrderRepo.updateOrderMultipleByStatus(statusId, listId);
    }

    @Override
    public OrderPurchase updateOrderCustomer(Long id, Integer statusId) {
        OrderPurchase op = new OrderPurchase();
        op.setId(id);
        op.setStatus(statusId);
        return this.adminManagerOrderRepo.save(op);
    }
}
