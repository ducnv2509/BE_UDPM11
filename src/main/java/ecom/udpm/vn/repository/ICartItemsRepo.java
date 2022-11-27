package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartItemsRepo extends JpaRepository<CartItems, Long> {
    @Query("Select ct from CartItems ct join Cart c on ct.id_cart = c.id where c.account_id =:id")
    List<CartItems> getAllCartItemByIdAccount(Integer id);

    @Query("select ip.quantity from InventoriesProductVariant ip where ip.product_variant_id =:id")
    Integer getQuantity(Integer id);


    @Query("select ip.quantity from CartItems ip join Cart  c on ip.id_cart = c.id where c.account_id =:id_C and ip.id_product =:id_P")
    Integer getQuantityCartItem(Integer id_C, Long id_P);
}
