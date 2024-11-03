package ua.cn.stu.main;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.cn.stu.domain.Product;
import ua.cn.stu.domain.ProductMapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseExplorer
{
    private static Connection connection = null;
    private static DefaultListModel<Product> produListModel;
    private static DriverManagerDataSource dataSource;

    private static DriverManagerDataSource connectToDatabaseJDBCTemplate(String
                                                                                 url, String name, String password) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(name);
        dataSource.setPassword(password);
        return dataSource;
    }
    private static List<Product> getAllProductsJDBCTemplate(DriverManagerDataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query("select * from product", new ProductMapper());
    }

    private static void addProductJDBCTemplate(DriverManagerDataSource dataSource, String productName,
                                               String productDescription) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO product (product_name, product_description) VALUES (?, ?)"; // Замените имена столбцов на ваши
        jdbcTemplate.update(sql, productName, productDescription);
    }

    private static Connection connectToDatabase(String url, String name, String
            password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = (Connection) DriverManager.getConnection(url, name, password);
        return connection;
    }

    private static List<Product> getAllProducts(Connection connection) throws
            SQLException {
        List<Product> productList = new ArrayList<Product>();
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from product");
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            Product product = new Product();
            product.setId(result.getLong(1));
            product.setName(result.getString(2));
            product.setDescription(result.getString(3));
            productList.add(product);
        }
        return productList;
    }

    private static void addProduct(Connection connection, String productName,
                                   String productDescription) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into product(product_name, product_description) values (?, ?)");
        preparedStatement.setString(1, productName);
        preparedStatement.setString(2, productDescription);
        preparedStatement.execute();
    }


    public static void main(String[] args) {
        try {
            // Установка FlatLaf Look and Feel
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() ->
        {
            try {
                dataSource = connectToDatabaseJDBCTemplate(
                        "jdbc:postgresql://localhost:5432/shop", "postgres", "1204");
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame("Database Explorer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 400);
            JPanel leftPanel = new JPanel();
            leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            leftPanel.setBackground(Color.LIGHT_GRAY);
            leftPanel.setPreferredSize(new Dimension(300, 400));

            JList<Product> list = new JList<Product>();
            produListModel = new DefaultListModel<Product>();
            list.setModel(produListModel);
            produListModel.removeAllElements();
            List<Product> productList = getAllProductsJDBCTemplate(dataSource);
            for (Product product : productList)
                produListModel.addElement(product);

            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new Dimension(280, 380));

            leftPanel.setLayout(new BorderLayout());
            leftPanel.add(scrollPane, BorderLayout.CENTER);

            JPanel formPanel = new JPanel();
            formPanel.setLayout(new GridBagLayout());
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel nameLabel = new JLabel("Product Name:");
            JTextField nameField = new JTextField(15);

            JLabel descriptionLabel = new JLabel("Product Description:");
            JTextField descriptionField = new JTextField(15);

            JButton addButton = getjButton(nameField, descriptionField);
            gbc.gridx = 0;
            gbc.gridy = 0;
            formPanel.add(nameLabel, gbc);
            gbc.gridx = 1;
            formPanel.add(nameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            formPanel.add(descriptionLabel, gbc);
            gbc.gridx = 1;
            formPanel.add(descriptionField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            formPanel.add(addButton, gbc);

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, formPanel);
            splitPane.setDividerLocation(300);
            splitPane.setResizeWeight(0.3);

            frame.add(splitPane);
            frame.setVisible(true);
        });
    }

    private static JButton getjButton(JTextField nameField, JTextField descriptionField) {
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            addProductJDBCTemplate(dataSource, nameField.getText(), descriptionField.getText());
            List<Product> productList = null;
            productList = getAllProductsJDBCTemplate(dataSource);

            produListModel.removeAllElements();
            for (Product product : productList) {
                produListModel.addElement(product);
            }
        }
    });
        return addButton;
    }
}


