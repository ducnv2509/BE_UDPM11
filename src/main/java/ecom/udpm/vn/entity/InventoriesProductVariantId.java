package ecom.udpm.vn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoriesProductVariantId implements Serializable {
    private static final long serialVersionUID = -7150393797126670443L;
    @Column(name = "inventory_id", nullable = false)
    private Integer inventoryId;

    @Column(name = "product_variant_id", nullable = false)
    private Long productVariantId;


    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InventoriesProductVariantId entity = (InventoriesProductVariantId) o;
        return Objects.equals(this.productVariantId, entity.productVariantId) &&
                Objects.equals(this.inventoryId, entity.inventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productVariantId, inventoryId);
    }

}