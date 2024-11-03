package ua.cn.stu.view;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.cn.stu.controller.SellerController;
import ua.cn.stu.model.Seller;

import javax.swing.*;
import java.awt.*;

public class SellerPanel extends JPanel {

    private SellerController sellerControllerController;

    public SellerPanel(DriverManagerDataSource dataSource) {
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setPreferredSize(new Dimension(300, 400));

        JList<Seller> list = new JList<Seller>();
        DefaultListModel<Seller> sellerListModel = new DefaultListModel<Seller>();
        sellerControllerController = new SellerController(dataSource, sellerListModel);
        list.setModel(sellerListModel);
        sellerControllerController.getAllSellers();

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

        JLabel nameLabel = new JLabel("Seller Name:");
        JTextField nameField = new JTextField(15);

        JLabel emailLabel = new JLabel("Seller Email:");
        JTextField emailField = new JTextField(15);

        JButton addButton = getjButton(nameField, emailField);

        // Существующие поля
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Кнопка добавления
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, formPanel);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.3);
        add(splitPane);
    }



    private JButton getjButton(JTextField nameField, JTextField emailField) {
        JButton addButton = new JButton("Add Seller");
        addButton.addActionListener(e -> {
            sellerControllerController.addSeller(nameField.getText(), emailField.getText());
        });
        return addButton;
    }
}
