package ecom.udpm.vn.entity;

import ecom.udpm.vn.dto.response.ImportInvoice.ImportStatusResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "imports_status")
@Getter
@Setter
@NamedNativeQuery(
        name = "getFeaturedImportStatusDTO",
        query = "\n" +
                "select a.username               as 'accountName',\n" +
                "       e.full_name              as 'fullName',\n" +
                "       e.phone                  as 'phoneNumber',\n" +
                "       s.name                   as 'statusName',\n" +
                "       s.description            as 'statusDesc',\n" +
                "       imports_status.create_at as 'createdAt'\n" +
                "from imports_status\n" +
                "         inner join imports i on imports_status.import_id = i.id\n" +
                "         inner join status s on imports_status.status_id = s.id\n" +
                "         inner join accounts a on imports_status.account_id = a.id\n" +
                "        inner join employees e on a.id = e.account_id\n" +
                "where import_id = ?\n" +
                "order by createdAt desc;",
        resultSetMapping = "FeaturedImportStatus"
)
@SqlResultSetMapping(
        name = "FeaturedImportStatus",
        classes = {
                @ConstructorResult(
                        targetClass = ImportStatusResponse.class,
                        columns = {
                                @ColumnResult(name = "accountName", type = String.class),
                                @ColumnResult(name = "fullName", type = String.class),
                                @ColumnResult(name = "phoneNumber", type = String.class),
                                @ColumnResult(name = "statusName", type = String.class),
                                @ColumnResult(name = "statusDesc", type = String.class),
                                @ColumnResult(name = "createdAt", type = String.class),
                        }
                )
        }
)
public class ImportsStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @JoinColumn(name = "import_id", nullable = false)
    private Integer importId;

    @JoinColumn(name = "status_id", nullable = false)
    private Integer statusId;


    @JoinColumn(name = "account_id", insertable = false, nullable = false, updatable = false)
    private Integer account_id;

    @CreatedDate
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;


}