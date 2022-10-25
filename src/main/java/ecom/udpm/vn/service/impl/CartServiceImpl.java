package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.Cart;
import ecom.udpm.vn.entity.CartItems;
import ecom.udpm.vn.exception.CartException;
import ecom.udpm.vn.exception.StaffException;
import ecom.udpm.vn.repository.ICartItemsRepo;
import ecom.udpm.vn.repository.ICartRepo;
import ecom.udpm.vn.repository.IProductVariantRepo;
import ecom.udpm.vn.service.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements ICartService {
    private final ICartRepo iCartRepo;
    private final ICartItemsRepo iCartItemsRepo;
    private final IProductVariantRepo iProductVariantRepo;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Cart createCart(Integer id) {
        String query = "call createCart(?)";
        System.out.println("in");
        return (Cart) jdbcTemplate.query(query, new BeanPropertyRowMapper(Cart.class), id).get(0);
    }

    @Override
    public List addItems(Long id, Long id_pr, Integer quantity) {
        String query = "call createCart(?,?,?)";
        String query_quantity = "call convertTo0(?,?)";
        CartItems quantity_item = (CartItems) jdbcTemplate.query(query_quantity, new BeanPropertyRowMapper(CartItems.class), id, id_pr).get(0);
        System.out.println("BUG2" + quantity_item.getQuantity());
        System.out.println("BUG 3 " + this.iCartItemsRepo.getQuantity(id.intValue()));
        if ((quantity_item.getQuantity() + quantity) > this.iCartItemsRepo.getQuantity(id_pr.intValue())) {
            throw new CartException("Số lượng trong kho không đủ");
        }
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(CartItems.class), id, id_pr, quantity);
    }
}
