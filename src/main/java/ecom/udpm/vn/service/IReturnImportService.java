package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.DetailsReturnImport;
import ecom.udpm.vn.entity.ReturnImport;

import java.util.List;

public interface IReturnImportService {
    ReturnImport save(ReturnImport returnImport);

    void saveAllDetails(List<DetailsReturnImport> returnImport, Integer inventoryId, Integer importReturnId);

}
