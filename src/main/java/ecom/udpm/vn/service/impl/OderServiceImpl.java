package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.dto.response.InvoiceJasperReport;
import ecom.udpm.vn.entity.OrderPurchaseItems;
import ecom.udpm.vn.service.IOrderService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;

import java.io.File;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class OderServiceImpl implements IOrderService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List createOrder(Long id_account, String address, String note) {
        String query = "call addOrderPurchase(?,?,?)";
        System.out.println("in");
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(OrderPurchaseItems.class), id_account, address, note);
    }

    @Override
    public ResponseEntity<byte[]> createPdf(Integer id_account, Integer id_order) throws FileNotFoundException, JRException {
        String query = "select a.id, c.name, a.account_name, a.created_time, a.address_id,\n" +
                    "        b.quantity, b.price, a.total_quantity, a.total_price, a.fee_money, d.phone\n" +
                    "from order_purchase a\n" +
                    "         join order_purchase_items b on a.id = b.id_order\n" +
                    "         join product_variant c on c.id = b.id_product\n" +
                    "         join customer_account d on d.id = a.account_id\n" +
                    "where a.account_id = ?\n" +
                    "  and b.id_order = ?";

        File file = ResourceUtils.getFile("classpath:invoice_purchase.jrxml");
        JasperReport jasperReports = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jdbcTemplate.query(query, new BeanPropertyRowMapper(InvoiceJasperReport.class), id_account, id_order));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("ID", id_account);
        parameter.put("ID_ORDER", id_order);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReports, parameter, dataSource);
        byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=invoice.pdf");
        System.out.println("print_invoice");
        return ResponseEntity.ok().header(String.valueOf(header)).contentType(MediaType.APPLICATION_PDF).body(data);
    }
}
