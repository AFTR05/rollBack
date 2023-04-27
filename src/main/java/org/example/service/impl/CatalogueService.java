package org.example.service.impl;

import org.example.ConnectionBD;
import org.example.model.Category;
import org.example.model.Product;
import org.example.repository.Repository;
import org.example.repository.impl.CategoryRepositoryImp;
import org.example.repository.impl.ProductRepostoryImp;
import org.example.service.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogueService implements Service<Product>{
    private Repository<Product> productRepository;
    private Repository<Category> categoryRepository;
    public CatalogueService() {
        this.productRepository = new ProductRepostoryImp();
        this.categoryRepository = new CategoryRepositoryImp();
    }
    @Override
    public List<Product> getlist() throws SQLException {
        try (Connection conn = ConnectionBD.getInstance()) {
            productRepository.setConn(conn);
            return productRepository.list();
        }
    }
    @Override
    public Product getbyId(Long id) throws SQLException {
        try (Connection conn = ConnectionBD.getInstance()) {
            productRepository.setConn(conn);
            return productRepository.byId(id);
        }
    }

    @Override
    public void update(Long id, Product o) throws SQLException {

    }

    @Override
    public void save(Product producto) throws SQLException {
        try (Connection conn = ConnectionBD.getInstance()) {
            productRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                productRepository.save(producto);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
    @Override
    public void delete(Long id) throws SQLException {
        try (Connection conn = ConnectionBD.getInstance()) {
            productRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                productRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }

    public List<Category> listCategory() throws SQLException {
        try (Connection conn = ConnectionBD.getInstance()) {
            categoryRepository.setConn(conn);
            return categoryRepository.list();
        }
    }

    public Category byCategoryId(Long id) throws SQLException {
        try (Connection conn = ConnectionBD.getInstance()) {
            categoryRepository.setConn(conn);
            return categoryRepository.byId(id);
        }
    }

    public void guardarCategoria(Category categoria) throws SQLException    {
        try (Connection conn = ConnectionBD.getInstance()) {
            categoryRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            Category nuevaCategoria = null;
            try {
                categoryRepository.save(categoria);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }

    public void eliminarCategoria(Long id) throws SQLException {
        try (Connection conn = ConnectionBD.getInstance()) {
            categoryRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                categoryRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }

    public void guardarProductoConCategoria(Product product, Category category) throws SQLException {
        try (Connection conn = ConnectionBD.getInstance()) {
            productRepository.setConn(conn);
            categoryRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                categoryRepository.save(category);
                product.setCategory(category);
                productRepository.save(product);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}