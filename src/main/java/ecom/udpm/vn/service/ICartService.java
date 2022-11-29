package ecom.udpm.vn.service;


import ecom.udpm.vn.entity.Cart;

import java.util.List;

public interface ICartService {
    Cart createCart(Integer id);
    List addItems(Long id, Long idPr, Integer quantity);
}
