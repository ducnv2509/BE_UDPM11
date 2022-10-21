package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.DetailsImport;
import ecom.udpm.vn.entity.InventoriesProductVariant;

import java.util.List;

public interface IInventoriesProductVariantService {

   InventoriesProductVariant findByInventoryIdAndProductVariantId(Integer inventoryId, Integer productVariantId);

   void importProductVariantToInventory(List<DetailsImport> list, Integer inventoryId);


}
