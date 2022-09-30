package ecom.udpm.vn.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class OrderByStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_purchase_id;

    @OneToMany
    @JoinColumn(name = "status_id")
    private List<Status> status;
    private Date created_at;
}
