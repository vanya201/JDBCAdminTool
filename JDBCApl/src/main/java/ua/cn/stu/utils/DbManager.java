package ua.cn.stu.utils;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DbManager {
    public static DriverManagerDataSource connectToDatabaseJDBCTemplate(String url, String name, String password) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(name);
        dataSource.setPassword(password);
        return dataSource;
    }
}
