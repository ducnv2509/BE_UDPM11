package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.request.Statistics.StatisticsImportRequest;
import ecom.udpm.vn.dto.request.Statistics.StatisticsInventoryRequest;
import ecom.udpm.vn.dto.response.Statistics.StatisticsImportResponse;
import ecom.udpm.vn.dto.response.Statistics.StatisticsInventoryRespone;
import ecom.udpm.vn.service.IStatisticsService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService implements IStatisticsService {
    private final JdbcTemplate jdbcTemplate;

    public StatisticsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<StatisticsImportResponse> getStatisticsImport(StatisticsImportRequest request) {
        String sql = "call get_statistic_import(?, ?,?, ?,?, ?, ?, ?,?);";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatisticsImportResponse.class), request.getInventoryId()
                , request.getSupplierId(), request.getStartDate(), request.getEndDate(), request.getSortBy(), request.isSortDir(), request.getPage(), request.getSize(),request.getKeySearch()
        );

    }

    @Override
    public List<StatisticsImportResponse> getStatisticsImportExtend(StatisticsImportRequest request) {
        String sql = "call get_statistic_import_extend(?, ?,?, ?,?, ?, ?, ?,?);";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatisticsImportResponse.class), request.getInventoryId()
                , request.getSupplierId(), request.getStartDate(), request.getEndDate(), request.getSortBy(), request.isSortDir(), request.getPage(), request.getSize(),request.getKeySearch()
        );

    }
    @Override
    public List<StatisticsInventoryRespone> getStatisticsInventory(StatisticsInventoryRequest request) {
        String sql = "call get_statistic_inventory(?, ?,?, ?,?, ?, ?,?);";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatisticsInventoryRespone.class), request.getInventoryId()
                , request.getStartDate(), request.getEndDate(), request.getSortBy(), request.isSortDir(), request.getPage(), request.getSize(),request.getKeySearch()
        );

    }

}
