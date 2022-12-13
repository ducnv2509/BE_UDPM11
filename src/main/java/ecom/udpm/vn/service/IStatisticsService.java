package ecom.udpm.vn.service;

import ecom.udpm.vn.dto.request.Statistics.StatisticsImportRequest;
import ecom.udpm.vn.dto.request.Statistics.StatisticsInventoryRequest;
import ecom.udpm.vn.dto.response.Statistics.StatisticsImportResponse;
import ecom.udpm.vn.dto.response.Statistics.StatisticsInventoryRespone;

import java.util.List;

public interface IStatisticsService {
    List<StatisticsImportResponse> getStatisticsImport(StatisticsImportRequest request);

    List<StatisticsImportResponse> getStatisticsImportExtend(StatisticsImportRequest request);


    List<StatisticsInventoryRespone> getStatisticsInventory(StatisticsInventoryRequest request);
}
