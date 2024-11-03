package ua.cn.stu.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.cn.stu.model.Seller;
import ua.cn.stu.model.SellerMapper;

import javax.swing.*;
import java.util.List;

public class SellerController
{
    private DriverManagerDataSource dataSource;
    private DefaultListModel<Seller> sellerListModel;

    public SellerController(DriverManagerDataSource dataSource, DefaultListModel<Seller> sellerListModel) {
        this.dataSource = dataSource;
        this.sellerListModel = sellerListModel;
    }

    public void getAllSellers() {
        List<Seller> sellerList = getAllSellersJDBCTemplate();
        SwingUtilities.invokeLater(() -> {
            sellerListModel.removeAllElements();
            for (Seller seller : sellerList)
                sellerListModel.addElement(seller);
        });
    }

    public void addSeller(String name, String email) {
        addSellerJDBCTemplate(dataSource, name, email);
        getAllSellers();
    }

    private void addSellerJDBCTemplate(DriverManagerDataSource dataSource, String sellerName, String sellerEmail) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO seller (seller_name, seller_email) VALUES (?, ?)"; // Замените имена столбцов на ваши
        jdbcTemplate.update(sql, sellerName, sellerEmail);
    }

    private List<Seller> getAllSellersJDBCTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query("select * from seller", new SellerMapper());
    }
}
