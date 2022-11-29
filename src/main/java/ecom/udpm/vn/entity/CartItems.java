package ecom.udpm.vn.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
@ToString
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_cart;
    private Integer id_product;
    private Integer quantity;
    private Double price;
    private Double total;
}
