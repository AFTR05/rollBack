package org.example.service.impl;

import org.example.model.Product;
import org.example.repository.impl.ProductRepostoryImp;
import org.example.service.Service;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements Service<Product> {

    ProductRepostoryImp repository=new ProductRepostoryImp();
    @Override
    public List<Product> getlist() {
        System.out.println("------------LIST PRODUCTS------------");
        return repository.list();
    }

    @Override
    public Product getbyId(Long id) {
        System.out.println("------------GET PRODUCT BY ID------------");
        return repository.byId(id);
    }

    @Override
    public void save(Product o) {
        System.out.println("------------SAVE PRODUCT------------");
        repository.save(o);
    }

    @Override
    public void delete(Long id) {
        System.out.println("------------DELETE PRODUCT------------");
        repository.delete(id);
    }

    @Override
    public void update(Long id,Product o) {
        System.out.println("------------UPDATE PRODUCT------------");
        repository.update(id,o);
    }
}
