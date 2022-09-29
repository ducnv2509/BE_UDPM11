package ecom.udpm.vn.service.impl;

import ecom.udpm.vn.models.Supplier;
import ecom.udpm.vn.repository.SupplierRepository;
import ecom.udpm.vn.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }
}
