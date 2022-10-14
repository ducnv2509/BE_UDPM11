package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.response.ImportInvoice.ImportStatusResponse;
import ecom.udpm.vn.entity.ImportsStatus;

import java.util.List;

public interface IImportsStatusService {

    ImportsStatus save(ImportsStatus importsStatus);


    ImportsStatus findByImportIdAndStatusId(Integer importId, Integer statusId);

    List<ImportStatusResponse> findDetailsImportStatus(Integer importId);
}
