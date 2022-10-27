package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.OrderPurchaseItems;
import ecom.udpm.vn.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OderServiceImpl implements IOrderService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List createOrder(Long id_account, String address, String note) {
        String query = "call addOrderPurchase(?,?,?)";
        System.out.println("in");
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(OrderPurchaseItems.class), id_account, address, note);
    }
}
