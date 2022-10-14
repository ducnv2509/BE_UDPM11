package ecom.udpm.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "details_return_import")
public class DetailsReturnImport {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "return_import_id", insertable = false, updatable = false)
    private ReturnImport returnImport;
    private Integer return_import_id;

    @Basic
    @Column(name = "details_import_id", nullable = false)
    private Integer detailsImportId;
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Basic
    @Column(name = "refund_reason", nullable = true, length = 250)
    private String refundReason;

}
