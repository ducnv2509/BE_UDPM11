package ecom.udpm.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_purchase")
public class OrderPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer account_id;
    private String account_name;
    private String phone_customer;
    private String address_id;
    private String note;
    private Double total_price;
    private Integer total_quantity;
    private Integer status;
    private Integer type;
    private Timestamp created_time;
    private String code;
}
