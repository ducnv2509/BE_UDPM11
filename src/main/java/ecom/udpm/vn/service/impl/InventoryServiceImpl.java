package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.request.ProductVariantDTO;
import ecom.udpm.vn.dto.response.InventoryResponse;
import ecom.udpm.vn.entity.InventoriesProductVariant;
import ecom.udpm.vn.entity.Inventory;
import ecom.udpm.vn.entity.ProductVariant;
import ecom.udpm.vn.helper.Utils;
import ecom.udpm.vn.repository.IInventoriesProductVariantRepo;
import ecom.udpm.vn.repository.IInventoryRepo;
import ecom.udpm.vn.repository.IProductVariantRepo;
import ecom.udpm.vn.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class InventoryServiceImpl implements IInventoryService {

    private final IInventoryRepo inventoryRepository;
    private final Utils utils;
    private final ModelMapper modelMapper;
    private final IProductVariantRepo productVariantsRepository;
    private final IInventoriesProductVariantRepo iInventoriesProductVariantRepo;

    public ProductVariantDTO toDto(ProductVariant productVariant) {
        ProductVariantDTO productVariantsDTO = modelMapper.map(productVariant, ProductVariantDTO.class);
        return productVariantsDTO;
    }

    @Override
    public Page<Inventory> findAllBypPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String name, String code) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        if (name != null) {
            return inventoryRepository.findByNameContaining(name,PageRequest.of(pageNumber - 1, pageSize,sort));
        }else if(code != null){
            return inventoryRepository.findByCodeContaining(code,PageRequest.of(pageNumber - 1, pageSize,sort));
        }
        else{
            return inventoryRepository.findAll(PageRequest.of(pageNumber - 1, pageSize, sort));
        }
    }


    @Override
    public List<Inventory> findAllActiveInventory() {
        return inventoryRepository.findAllActiveInventory();
    }


    @Override
    public InventoryResponse getProductVariantByInventoryId(Integer id, String name) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        List<ProductVariantDTO> results = new ArrayList<>();
        Integer totalProductVariant = 0;
        Integer countProductVariant = 0;
        Inventory inventory = inventoryRepository.findById(id.intValue()).orElseThrow(() -> new IllegalArgumentException("id not found:" + id));
        try {
            inventoryResponse.setInventory(inventory);
            List<ProductVariant> productVariants = productVariantsRepository.listProductVariantByName(Long.valueOf(id),name);
            for (ProductVariant item : productVariants) {
                ProductVariantDTO productVariantsDTO = toDto(item);
                productVariantsDTO.setQuantity(inventoryRepository.Quantity(id, item.getId()));
                productVariantsDTO.setMinQuantity(inventoryRepository.minQuantity(id, item.getId()));
                productVariantsDTO.setCreateAt(inventoryRepository.createAt(item.getId()));
                results.add(productVariantsDTO);
                countProductVariant = countProductVariant + 1;
                totalProductVariant = totalProductVariant + inventoryRepository.Quantity(id, item.getId());
            }
            inventoryResponse.setProductVariants(results);
            inventoryResponse.setCountProductVariant(countProductVariant);
            inventoryResponse.setTotalProductVariant(totalProductVariant);
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }
        return inventoryResponse;
    }

    @Override
    public Inventory update(Integer id, Inventory inventory, BindingResult bindingResult) {
        Inventory inventoryOld = inventoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found:" + id));
        if (bindingResult.hasErrors()) {
            throw utils.invalidInputException(bindingResult);
        } else {
            inventory.setId(id);
            inventory.setCreateAt(inventoryOld.getCreateAt());
            return inventoryRepository.save(inventory);
        }
    }

    @Override
    public Inventory findById(Integer id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found: " + id));
        return inventory;
    }

    @Override
    public InventoriesProductVariant changeMinQuantity(Integer inventoryId, Integer productVariantId, Integer minQuantity) {
        InventoriesProductVariant inventoriesProductVariant = iInventoriesProductVariantRepo.findByInventoryIdAndProductVariantId(inventoryId, Long.valueOf(productVariantId));
        inventoriesProductVariant.setMin_quantity(minQuantity);
        iInventoriesProductVariantRepo.save(inventoriesProductVariant);
        return inventoriesProductVariant;
    }

    @Override
    public List<ProductVariantDTO> findInventoriesQuantity(Integer id) {
        List<ProductVariantDTO> results = new ArrayList<>();
        List<ProductVariant> productVariants = productVariantsRepository.findAllById(inventoryRepository.findInventoriesQuantity(Long.valueOf(id)));
        for (ProductVariant item : productVariants) {
            ProductVariantDTO productVariantsDTO = toDto(item);
            productVariantsDTO.setQuantity(inventoryRepository.Quantity(id, item.getId()));
            results.add(productVariantsDTO);
        }
        return results;
    }
    @Override
    public void updateStatusInventory(Integer id){
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found: " + id));
        inventory.setSize(!inventory.getSize());
        inventoryRepository.save(inventory);
    }
    @Override
    public void delete(Integer id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found: " + id));
        inventory.setIsDelete(!inventory.getIsDelete());
        inventoryRepository.save(inventory);
    }
}