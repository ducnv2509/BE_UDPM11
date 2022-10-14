package ecom.udpm.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "inventories_product_variant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoriesProductVariant {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EmbeddedId
    private InventoriesProductVariantId id;


    @MapsId("inventoryId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "inventory_id", insertable = false, updatable = false, nullable = false)
    private Inventory inventory;

    @Column(name = "inventory_id", insertable = false, updatable = false, nullable = false)
    private Integer inventory_id;


    @MapsId("productVariantId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;


    @Column(name = "product_variant_id", insertable = false, updatable = false, nullable = false)
    private Integer product_variant_id;


    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "min_quantity", nullable = true)
    private Integer min_quantity;

    public InventoriesProductVariant(InventoriesProductVariantId id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public InventoriesProductVariant(Integer inventoryId, Integer id, Integer quantity) {
    }
}