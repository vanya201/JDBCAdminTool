package ua.cn.stu.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerMapper implements RowMapper<Seller> {
    @Override
    public Seller mapRow(ResultSet resultSet, int i) throws SQLException {
        Seller seller = new Seller();
        seller.setName(resultSet.getString("seller_name"));
        seller.setEmail(resultSet.getString("seller_email"));
        seller.setId(resultSet.getLong("seller_id"));
        return seller;
    }
}
