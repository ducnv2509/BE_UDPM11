package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.response.OrderPurchaseItem;
import ecom.udpm.vn.dto.response.product.OrderReturnItemResponse;
import ecom.udpm.vn.dto.response.product.OrderReturnResponse;
import ecom.udpm.vn.entity.OrderPurchase;
import ecom.udpm.vn.exception.StaffException;
import ecom.udpm.vn.repository.AdminManagerOrderRepo;
import ecom.udpm.vn.repository.IOderPurchaseItemRepo;
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
    public List<OrderPurchase> searchOrdersByStatus(String querySearch, int status) {
        String query = "select * from order_purchase where (account_name like concat('%', ?, '%') or address_id like  concat('%', ?, '%') or phone_customer like concat('%', ?, '%')) and status = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(OrderPurchase.class), querySearch, querySearch, querySearch, status);
    }

    @Override
    public List<OrderPurchase> searchOrdersAll(String querySearch) {
         String query = "select * from order_purchase where (account_name like concat('%', ?, '%') or address_id like concat('%', ?, '%') or phone_customer like concat('%', ?, '%'))";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(OrderPurchase.class),querySearch, querySearch, querySearch);
    }

    @Override
    public void updateMultiOrderCustomer(List<Long> listId, Integer statusId, String _action_by) {
        listId.forEach(id -> this.adminManagerOrderRepo.findById(id).orElseThrow(() -> new StaffException(("id not found: " + id))));
        String query = " insert into order_by_status_history(order_purchase_id, status_id, created_at, action_by)\n" +
                "    VALUES (?, ?,\n" +
                "            NOW(), ?);";
        for (Long ids:
            listId ) {
            jdbcTemplate.update(query, ids, statusId, _action_by);
        }
        this.adminManagerOrderRepo.updateOrderMultipleByStatus(statusId, listId);
    }

    @Override
    public OrderPurchase updateOrderCustomer(Long id, Integer statusId) {
        OrderPurchase op = new OrderPurchase();
        op.setId(id);
        op.setStatus(statusId);
        return this.adminManagerOrderRepo.save(op);
    }

    @Override
    public List<OrderReturnResponse> showOrdeReturn() {
        return  jdbcTemplate.query("select * from return_invoice", new BeanPropertyRowMapper(OrderReturnResponse.class) );
    }

    @Override
    public List<OrderReturnItemResponse> showOrderReturnItemByIdOrder(Long idOrderReturn) {
        String sql = "select pr.name,pr.image,concat(pr.option1,',',pr.option2,',',pr.option3,',') as optionProduct,oitem.quantity,oitem.price,oitem.quantity*oitem.price as totalPrice\n" +
                "from return_item_invoice reitem\n" +
                "    join order_purchase_items oitem on reitem.id_purchase_item = oitem.id\n" +
                "    join product_variant pr on pr.id = oitem.id_product\n" +
                "    where id_return = ?";
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(OrderReturnItemResponse.class), idOrderReturn );
    }

    @Override
    public OrderReturnResponse updateOrderReturn(Long id, Integer statusId) {
        String sql = "call updateStatusReturnInvoiceByAdmin(?,?)";
        return (OrderReturnResponse) jdbcTemplate.query(sql, new BeanPropertyRowMapper(OrderReturnResponse.class), statusId ,id).get(0);
    }
}
