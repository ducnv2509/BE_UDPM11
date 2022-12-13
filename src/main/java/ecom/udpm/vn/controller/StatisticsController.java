package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.request.Statistics.StatisticsImportRequest;
import ecom.udpm.vn.dto.request.Statistics.StatisticsInventoryRequest;
import ecom.udpm.vn.service.IStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/statistics")
@CrossOrigin("*")
public class StatisticsController {
    private IStatisticsService statisticsService;

    public StatisticsController(IStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/imports")
    public ResponseEntity getStatisticsImport(@RequestBody StatisticsImportRequest request) {
        return ResponseEntity.ok(statisticsService.getStatisticsImport(request));
    }

    @PostMapping("/imports/extend")
    public ResponseEntity getStatisticsImportExtend(@RequestBody StatisticsImportRequest request) {

        return ResponseEntity.ok(statisticsService.getStatisticsImportExtend(request));
    }

    @PostMapping("/inventories")
    public ResponseEntity getStatisticsInventory(@RequestBody StatisticsInventoryRequest request) {
        return ResponseEntity.ok(statisticsService.getStatisticsInventory(request));
    }
}
