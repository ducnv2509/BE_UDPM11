package ecom.udpm.vn.repository;

import ecom.udpm.vn.entity.InventoriesProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IInventoriesProductVariantRepo extends JpaRepository<InventoriesProductVariant, Integer> {

    InventoriesProductVariant findByInventoryIdAndProductVariantId(Integer inventoryId, Integer productId);


    @Modifying
    @Transactional
    @Query(value = "delete from inventories_product_variant where inventory_id = ?1 and product_variant_id = ?2", nativeQuery = true)
    void deleteProductVariant(Integer idInventory, Integer idProductVariant);


}
