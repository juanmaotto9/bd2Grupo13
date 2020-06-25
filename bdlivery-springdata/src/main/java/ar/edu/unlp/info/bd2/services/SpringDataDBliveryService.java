package ar.edu.unlp.info.bd2.services;


import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SpringDataDBliveryService implements DBliveryService {
	

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
        Product prod = new Product(name, price, weight, supplier);
        return productRepository.save(prod);
    }

    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
        Product prod = new Product(name, price, weight, supplier, date);
        return productRepository.save(prod);
    }
    
    @Override
    public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        Supplier supp = new Supplier(name, cuil, address, coordX, coordY);
        return supplierRepository.save(supp);
    }


}
