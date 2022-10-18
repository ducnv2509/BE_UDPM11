package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.entity.DetailsImport;
import ecom.udpm.vn.repository.IDetailImportRepo;
import ecom.udpm.vn.service.IDetailsImportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DetailsImportService implements IDetailsImportService {

    private final IDetailImportRepo detailImportRepo;

    @Override
    public List<DetailsImport> save(List<DetailsImport> detailsImportList, Integer importId) {
        for (DetailsImport detailsImport : detailsImportList) {
            detailsImport.setImport_id(importId);
        }
        return detailImportRepo.saveAll(detailsImportList);
    }
}
