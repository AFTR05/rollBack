package org.example.repository.impl;

import org.example.ConnectionBD;
import org.example.model.Category;
import org.example.model.Product;
import org.example.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepostoryImp implements Repository<Product>{
    private Connection conn;

    public ProductRepostoryImp(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public ProductRepostoryImp() {

    }

    private Product createProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setProduct_name(resultSet.getString("product_name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setDate_register(resultSet.getDate("date_register").toLocalDate());
        Category category = new Category();
        category.setId(resultSet.getInt("id_category"));
        category.setName(resultSet.getString("category_name"));
        product.setCategory(category);
        return product;
    }
    @Override
    public List<Product> list() {
        List<Product> products = new ArrayList<>();
        try (Statement statement=conn.createStatement();
             ResultSet resultSet=statement.executeQuery("SELECT p.id,p.product_name,p.price,p.date_register,p.id_category,c.category_name FROM products_category as p join categories as c on(p.id_category=c.id)")
        ){
            while (resultSet.next()) {
                Product product = createProduct(resultSet);
                products.add(product);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product byId(Long id) {
        Product product = null;
        try (PreparedStatement preparedStatement=conn.prepareStatement("SELECT p.id,p.product_name,p.price,p.date_register,p.id_category,c.category_name FROM products_category as p join categories as c on(p.id_category=c.id) where p.id=?")){
            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                product = createProduct(resultSet);
            }
            resultSet.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
        public Product save(Product product) {

        String sql;
        if (product.getId() != null && product.getId() > 0) {
            sql = "UPDATE productos SET product_name=?, price=?, id_category=?, date_register=? WHERE id=?";
        } else {
            sql = "INSERT INTO productos(product_name,price,id_category,date_register) VALUES(?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getProduct_name());
            stmt.setLong(2, product.getPrice().longValue());
            stmt.setLong(3, product.getCategory().getId());
            stmt.setDate(4, Date.valueOf(product.getDate_register()));
            if (product.getId() != null && product.getId() > 0) {
                stmt.setLong(5, product.getId());
            } else {
                stmt.setDate(4, Date.valueOf(product.getDate_register()));
            }
            stmt.executeUpdate();
            if (product.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        product.setId(rs.getInt(1));
                    }
                }
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public void delete(Long id) {
        Product product = null;
        try (PreparedStatement preparedStatement=conn.prepareStatement("DELETE FROM products_category WHERE id =?")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Long id,Product o) {
        Product product = (Product) o;
        try (PreparedStatement preparedStatement=conn.prepareStatement("UPDATE products_category SET product_name=? ,price=?,date_register=?,id_category=? where id=?")){
            preparedStatement.setString(1,product.getProduct_name());
            preparedStatement.setLong(2,product.getPrice().longValue());
            preparedStatement.setDate(3,Date.valueOf(product.getDate_register()));
            preparedStatement.setLong(4,product.getCategory().getId());
            preparedStatement.setLong(5,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
