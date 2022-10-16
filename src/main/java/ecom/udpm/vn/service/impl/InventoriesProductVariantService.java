package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.request.InventoriesProductVariantRequest;
import ecom.udpm.vn.entity.DetailsImport;
import ecom.udpm.vn.entity.InventoriesProductVariant;
import ecom.udpm.vn.repository.IInventoriesProductVariantRepo;
import ecom.udpm.vn.service.IInventoriesProductVariantService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoriesProductVariantService implements IInventoriesProductVariantService {

    private final IInventoriesProductVariantRepo inventoriesProductVariantRepo;


    @Override
    public InventoriesProductVariant findByInventoryIdAndProductVariantId(Integer inventoryId, Integer productVariantId) {
        return inventoriesProductVariantRepo.findByInventoryIdAndProductVariantId(inventoryId, productVariantId);
    }

    @Override
    public void importProductVariantToInventory(List<DetailsImport> list, Integer inventoryId) {
        ModelMapper modelMapper = new ModelMapper();
        if (inventoryId == null) {
            throw new IllegalArgumentException("id not found");
        }
        for (DetailsImport detailsImport : list) {
            Integer productVariantId = detailsImport.getProduct_variant_id();
            InventoriesProductVariant inventoriesProductVariant = inventoriesProductVariantRepo
                    .findByInventoryIdAndProductVariantId(inventoryId, productVariantId);
            if (inventoriesProductVariant == null) {
                InventoriesProductVariantRequest request = new InventoriesProductVariantRequest(inventoryId, productVariantId, detailsImport.getQuantity());
                InventoriesProductVariant in = modelMapper.map(request, InventoriesProductVariant.class);
                inventoriesProductVariantRepo.save(in);
            } else {
                inventoriesProductVariant.setQuantity(inventoriesProductVariant.getQuantity() + detailsImport.getQuantity());
                inventoriesProductVariantRepo.save(inventoriesProductVariant);
            }
        }
    }

}
