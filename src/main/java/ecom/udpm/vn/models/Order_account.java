package ecom.udpm.vn.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Order_account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long account_id;
    private int city_id;
    private int district_id;
    private int commune_id;
    private String address_detail;
    private Boolean type;
    private Boolean status;
}
