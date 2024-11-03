package ua.cn.stu.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.cn.stu.model.Product;
import ua.cn.stu.model.ProductMapper;

import javax.swing.*;
import java.util.List;

public class ProductController
{
    private DriverManagerDataSource dataSource;
    private DefaultListModel<Product> produListModel;

    public ProductController(DriverManagerDataSource dataSource, DefaultListModel<Product> produListModel) {
        this.dataSource = dataSource;
        this.produListModel = produListModel;
    }

    public void getAllProducts() {
        List<Product> productList = getAllProductsJDBCTemplate();
        SwingUtilities.invokeLater(() -> {
            produListModel.removeAllElements();
            for (Product product : productList)
                produListModel.addElement(product);
        });
    }

    public void addProduct(String name, String description, Long sellerId) {
        addProductJDBCTemplate(dataSource, name, description, sellerId);
        getAllProducts();
    }

    private void addProductJDBCTemplate(DriverManagerDataSource dataSource, String productName, String productDescription, Long sellerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO product (product_name, product_description, seller_id) VALUES (?, ?, ?)"; // Замените имена столбцов на ваши
        jdbcTemplate.update(sql, productName, productDescription, sellerId);
    }

    private List<Product> getAllProductsJDBCTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query("select * from product", new ProductMapper());
    }

}
