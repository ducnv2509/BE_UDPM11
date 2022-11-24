package ecom.udpm.vn.service;

import ecom.udpm.vn.entity.OrderPurchaseItems;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.util.List;

public interface IOrderService {
   List<OrderPurchaseItems> createOrder(Long id_account , String address, String note);

    ResponseEntity<byte[]> createPdf(Integer id_account, Integer id_order) throws FileNotFoundException, JRException;
}
