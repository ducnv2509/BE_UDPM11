package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.response.OrderPurchaseItem;
import ecom.udpm.vn.entity.OrderByStatusHistory;
import ecom.udpm.vn.entity.OrderPurchase;
import ecom.udpm.vn.exception.StaffException;
import ecom.udpm.vn.repository.AdminManagerOrderRepo;
import ecom.udpm.vn.repository.IOderPurchaseItemRepo;
import ecom.udpm.vn.repository.IOrderHistoryStatus;
import ecom.udpm.vn.service.AdminManagerOrderService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminManagerOrderServiceImpl implements AdminManagerOrderService {
    private final AdminManagerOrderRepo adminManagerOrderRepo;
    private final IOderPurchaseItemRepo oderPurchaseItemRepo;
    private final IOrderHistoryStatus orderHistoryStatus;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<OrderPurchase> showOrderCustomer() {
        return this.adminManagerOrderRepo.findAll();
    }

    @Override
    public List<OrderPurchase> showOrderCustomerByStatus(Integer status_id) {
        return null;
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
        String query = "insert into order_by_status_history(order_purchase_id, status_id, created_at)\n" +
                "    VALUES ( ?, ?, \n" +
                "            NOW());";
        listId.forEach(
                id -> jdbcTemplate.update(query, id, statusId)
        );
        this.adminManagerOrderRepo.updateOrderMultipleByStatus(statusId, listId);
    }

    @Override
    public OrderPurchase updateOrderCustomer(Long id, Integer statusId) {
        OrderPurchase op = new OrderPurchase();
        op.setId(id);
        op.setStatus(statusId);
        return this.adminManagerOrderRepo.save(op);
    }

//    select * from return_invoice
//
//    select * from return_item_invoice where id_return = ?
//
//    call updateStatusReturnInvoiceByAdmin(?,?)
//    public void showOrderReturn() {
//     jdbcTemplate.query("select * from order", new BeanPropertyRowMapper(OrderPurchaseItem.class), idOrder);
//
//    }
}