package ecom.udpm.vn.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Order_purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long account_id;
    private String account_name;
    private Long address_id;
    private String note;
    private Double total_price;
    private int total_quantity;
    private int status;
    private int type;
    private String status_by;
    private Date created_time;
    private String code;
}
