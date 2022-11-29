package ecom.udpm.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "return_import")
public class ReturnImport {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @CreatedDate
    @Column(name = "create_date", nullable = true)
    private Timestamp createDate;
    @Basic
    @Column(name = "import_id", nullable = false)
    private int importId;

    @Column(name = "account_id", nullable = false)
    private int accountId;

    @OneToMany(mappedBy = "returnImport", cascade = CascadeType.ALL)
    private List<DetailsReturnImport> detailsReturnImports;

}
