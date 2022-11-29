package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.*;
import ecom.udpm.vn.repository.*;
import ecom.udpm.vn.service.IReturnImportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReturnImportService implements IReturnImportService {
    private final IReturnImportRepo returnImportRepo;

    private final IDetailImportRepo detailImportRepo;
    private final IImportRepo importRepo;

    private final IProductVariantRepo productVariantRepo;
    private final IDetailImportReturnRepo detailImportReturnRepo;
    private final IInventoriesProductVariantRepo inventoriesProductVariantRepo;


    @Override
    public ReturnImport save(ReturnImport returnImport) {
        return returnImportRepo.save(returnImport);
    }

    @Override
    public void saveAllDetails(List<DetailsReturnImport> returnImport, Integer inventoryId, Integer importReturnId) {
        for (DetailsReturnImport detailsReturnImport : returnImport) {
            DetailsImport detailsImport = detailImportRepo.findById(detailsReturnImport.getDetailsImportId()).orElseThrow(() -> new IllegalArgumentException(("id not found: " + detailsReturnImport.getDetailsImportId())));
            ProductVariant productVariant = productVariantRepo.findById((detailsImport.getProduct_variant_id())).orElseThrow(() -> new IllegalArgumentException(("id not found: " + detailsImport.getProduct_variant_id())));
            InventoriesProductVariant inventoriesProductVariant = inventoriesProductVariantRepo
                    .findByInventoryIdAndProductVariantId(inventoryId, productVariant.getId());
            if (inventoriesProductVariant != null) {
                if (inventoriesProductVariant.getQuantity() - detailsReturnImport.getQuantity() >= 0) {
                    inventoriesProductVariant.setQuantity(inventoriesProductVariant.getQuantity() - detailsReturnImport.getQuantity());
                    inventoriesProductVariantRepo.save(inventoriesProductVariant);
                    detailsReturnImport.setReturn_import_id(importReturnId);
                    detailImportReturnRepo.save(detailsReturnImport);
                } else {
                    ReturnImport im = returnImportRepo.findById(importReturnId).orElseThrow();
                    returnImportRepo.delete(im);
                }
            }
        }
    }

}
