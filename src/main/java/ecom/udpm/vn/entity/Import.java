package ecom.udpm.vn.entity;

import ecom.udpm.vn.dto.response.ImportInvoice.DetailsReturnImportResponse;
import ecom.udpm.vn.dto.response.ImportInvoice.ImportResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "imports")
@Getter
@Setter
@NamedNativeQuery(
        name = "getFeaturedInventoryDTO",
        query = "select imports.code,\n" +
                "       s.code        as 'supplierCode',\n" +
                "       i.name        as 'inventoryName',\n" +
                "       total_price   as 'totalPrice',\n" +
                "       is_done       as 'isDone',\n" +
                "       is_paid       as 'isPaid',\n" +
                "       is_import     as 'isImport',\n" +
                "       is_return     as 'isReturn',\n" +
                "       a.username    as 'userName',\n" +
                "       delivery_date as 'deliveryDate'\n" +
                "from imports\n" +
                "         inner join accounts a on imports.account_id = a.id\n" +
                "         inner join inventories i on imports.inventory_id = i.id\n" +
                "         inner join supplier s on imports.supplier_id = s.id\n" +
                "order by imports.id desc",
        resultSetMapping = "FeaturedInventory"
)
@NamedNativeQuery(
        name = "getImportReturnDTO",
        query = "select di.id as detailsImportId,\n" +
                "       pv.code,\n" +
                "       pv.name,\n" +
                "       if(ri.quantity >=0, di.quantity - sum(ri.quantity),di.quantity)                      as quantity,\n" +
                "       di.import_price                                                                               as importPrice,\n" +
                "       if(ri.quantity >=0, di.total_price - (sum(ri.quantity) * di.import_price), di.total_price) as totalPrice,\n" +
                "       s.name\n" +
                "from details_imports di\n" +
                "         left join details_return_import ri on di.id = ri.details_import_id\n" +
                "         inner join product_variant pv on di.product_variant_id = pv.id\n" +
                "         inner join imports i on di.import_id = i.id\n" +
                "         inner join inventories i2 on i.inventory_id = i2.id\n" +
                "         inner join supplier s on i.supplier_id = s.id\n" +
                "where i.code = ? \n" +
                "group by di.id, " +  "pv.code, pv.name, di.import_price, s.name;",
        resultSetMapping = "FeaturedReturnImport"
)
@NamedNativeQuery(
        name = "getImportReturnDTOResponse",
        query = "select di.id                                            as detailsImportId,\n" +
                "       pv.code,\n" +
                "       pv.name,\n" +
                "       if(ri.quantity >=0 , sum(ri.quantity), 0) as quantity,\n" +
                "       di.import_price                                  as importPrice,\n" +
                "       if(ri.quantity >=0 , (di.total_price - ((di.quantity - sum(ri.quantity)) * di.import_price)),\n" +
                "          di.total_price)                               as totalPrice,\n" +
                "       s.name\n" +
                "from details_imports di\n" +
                "         left join details_return_import ri on di.id = ri.details_import_id\n" +
                "        inner join return_import r on ri.return_import_id = r.id\n" +
                "         inner join product_variant pv on di.product_variant_id = pv.id\n" +
                "         inner join imports i on di.import_id = i.id\n" +
                "         inner join inventories i2 on i.inventory_id = i2.id\n" +
                "         inner join supplier s on i.supplier_id = s.id\n" +
                "where r.id = ?\n" +
                "group by di.id ," +
                "pv.code, pv.name, di.import_price, s.name;",
        resultSetMapping = "FeaturedReturnImport"
)
@SqlResultSetMapping(
        name = "FeaturedReturnImport",
        classes = {
                @ConstructorResult(
                        targetClass = DetailsReturnImportResponse.class,
                        columns = {
                                @ColumnResult(name = "detailsImportId", type = Integer.class),
                                @ColumnResult(name = "code", type = String.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "quantity", type = Integer.class),
                                @ColumnResult(name = "importPrice", type = BigDecimal.class),
                                @ColumnResult(name = "totalPrice", type = BigDecimal.class)
                        }
                )
        }
)

@SqlResultSetMapping(
        name = "FeaturedInventory",
        classes = {
                @ConstructorResult(
                        targetClass = ImportResponse.class,
                        columns = {
                                @ColumnResult(name = "code", type = String.class),
                                @ColumnResult(name = "supplierCode", type = String.class),
                                @ColumnResult(name = "inventoryName", type = String.class),
                                @ColumnResult(name = "totalPrice", type = BigDecimal.class),
                                @ColumnResult(name = "isDone", type = Boolean.class),
                                @ColumnResult(name = "isPaid", type = Boolean.class),
                                @ColumnResult(name = "isImport", type = Boolean.class),
                                @ColumnResult(name = "isReturn", type = Boolean.class),
                                @ColumnResult(name = "userName", type = String.class),
                                @ColumnResult(name = "deliveryDate", type = String.class),
                        }
                )
        }
)
public class Import {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @JoinColumn(name = "account_id", insertable = false, updatable = false, nullable = false)
    private Integer accountId;

    @JoinColumn(name = "supplier_id", nullable = false)
    @NotNull(message = "supplierId can not be null")
    private Integer supplierId;

    @JoinColumn(name = "inventory_id", nullable = false)
    @NotNull(message = "inventoryId can not be null")
    private Integer inventoryId;


    @Column(name = "total_price", nullable = false, precision = 20, scale = 2)
    private BigDecimal totalPrice =BigDecimal.ZERO;

    @Column(name = "note" , length = 250)
    private String note;

    @Column(name = "code", length = 50)
    @Size(max = 100, message = "code can not be more then 50 character")
    private String code;


    @Column(name = "is_done")
    private Boolean isDone = false;

    @Column(name = "is_paid")
    private Boolean isPaid = false;

    @Column(name = "is_import")
    private Boolean isImport = false;

    @Column(name = "is_return")
    private Boolean isReturn = false;

    @Column(name = "delivery_date")
    private String deliveryDate;


    @OneToMany(mappedBy = "anImport", cascade = CascadeType.ALL)
    private List<DetailsImport> detailsImports;
}