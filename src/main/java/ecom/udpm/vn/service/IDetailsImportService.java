package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.DetailsImport;

import java.util.List;

public interface IDetailsImportService {

    List<DetailsImport> save(List<DetailsImport> detailsImportList, Integer importId);
}
