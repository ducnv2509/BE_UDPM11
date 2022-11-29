package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepo extends JpaRepository<Cart, Long> {

}
