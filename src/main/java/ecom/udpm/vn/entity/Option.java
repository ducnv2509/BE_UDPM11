package ecom.udpm.vn.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;

public class Option {

    @Id
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Integer position;

    @CreatedDate
    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    private Integer productId;

}
