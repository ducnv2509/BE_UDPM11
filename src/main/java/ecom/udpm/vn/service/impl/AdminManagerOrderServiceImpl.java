package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.response.OrderPurchaseItem;
import ecom.udpm.vn.entity.Cart;
import ecom.udpm.vn.entity.OrderPurchase;
import ecom.udpm.vn.exception.StaffException;
import ecom.udpm.vn.repository.AdminManagerOrderRepo;
import ecom.udpm.vn.repository.IOderPurchaseItemRepo;
import ecom.udpm.vn.service.AdminManagerOrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminManagerOrderServiceImpl implements AdminManagerOrderService {
    private final AdminManagerOrderRepo adminManagerOrderRepo;
    private final IOderPurchaseItemRepo oderPurchaseItemRepo;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<OrderPurchase> showOrderCustomer() {
        return this.adminManagerOrderRepo.findAll();
    }

    @Override
    public List<OrderPurchaseItem> showOrderItemByIdOrder(Long idOrder) {
        this.adminManagerOrderRepo.findById(idOrder).orElseThrow(() -> new StaffException(("id not found: " + idOrder)));
        String query = "select o.id,o.quantity,o.price,o.total_price,p.image,p.name,p.option1,p.option2,p.option3\n" +
                "from order_purchase_items o \n" +
                "join product_variant p on o.id_product = p.id\n" +
                "where o.id_order = ?";
        List<OrderPurchaseItem> getOrderItemsByIdOrder = (List<OrderPurchaseItem>) jdbcTemplate.query(query, new BeanPropertyRowMapper(OrderPurchaseItem.class), idOrder);
        return getOrderItemsByIdOrder;
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
