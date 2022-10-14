package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.request.ProductVariantDTO;
import ecom.udpm.vn.dto.response.InventoryResponse;
import ecom.udpm.vn.entity.InventoriesProductVariant;
import ecom.udpm.vn.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IInventoryService {
    Page<Inventory> findAllBypPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String name, String code);

    void delete (Integer id);

    List<Inventory> findAllActiveInventory();
    Inventory update(Integer id, Inventory inventory, BindingResult bindingResult);


    void updateStatusInventory(Integer id);

    InventoryResponse getProductVariantByInventoryId(Integer id, String name);


    InventoriesProductVariant changeMinQuantity(Integer inventoryId, Integer productVariantId, Integer minQuantity);

    List<ProductVariantDTO> findInventoriesQuantity(Integer id);

}