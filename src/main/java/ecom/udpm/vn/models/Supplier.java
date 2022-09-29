package ecom.udpm.vn.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private Date createdDate;
    private Date updatedDate;
    private Long createdBy;
    private Long updatedBy;
    private Boolean status;
}
