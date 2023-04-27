package org.example;

import com.google.protobuf.Value;
import org.example.model.Category;
import org.example.model.Product;
import org.example.service.Service;
import org.example.service.impl.CatalogueService;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        CatalogueService service = new CatalogueService();
        System.out.println("============= listar =============");
        try {
            service.getlist().forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Category category = new Category();
        category.setName("Iluminación");
        Product product = new Product();
        product.setProduct_name("Lámpara led escritorio");
        product.setPrice(990.0);
        product.setDate_register(Date.valueOf("2022-02-05").toLocalDate());
        try {
            service.guardarProductoConCategoria(product, category);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Producto guardado con éxito: " +
                product.getId());
        try {
            service.getlist().forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}