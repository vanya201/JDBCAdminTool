package ua.cn.stu.view;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.cn.stu.controller.ProductController;
import ua.cn.stu.model.Product;

import javax.swing.*;
import java.awt.*;

public class ProductPanel extends JPanel {

    private ProductController productController;

    public ProductPanel(DriverManagerDataSource dataSource) {
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setPreferredSize(new Dimension(300, 400));

        JList<Product> list = new JList<Product>();
        DefaultListModel<Product> produListModel = new DefaultListModel<Product>();
        productController = new ProductController(dataSource, produListModel);
        list.setModel(produListModel);
        productController.getAllProducts();

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 380));

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

        JLabel idLabel = new JLabel("Seller Id:");
        JTextField idField = new JTextField(15);


        JButton addButton = getjButton(nameField, descriptionField, idField);

        // Существующие поля
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
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, formPanel);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.3);
        add(splitPane);
    }



    private JButton getjButton(JTextField nameField, JTextField descriptionField, JTextField idField) {
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> {
            productController.addProduct(nameField.getText(), descriptionField.getText(), Long.valueOf(idField.getText()));
        });
        return addButton;
    }
}
