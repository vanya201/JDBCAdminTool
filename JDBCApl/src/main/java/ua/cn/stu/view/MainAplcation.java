package ua.cn.stu.view;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.cn.stu.controller.SwapControler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static ua.cn.stu.utils.DbManager.connectToDatabaseJDBCTemplate;

public class MainAplcation extends JFrame {

    private DriverManagerDataSource dataSource = connectToDatabaseJDBCTemplate(
            "jdbc:postgresql://localhost:5432/shop", "postgres", "1204");

    private CardLayout cardLayout = new CardLayout();
    private SwapControler swapControler = new SwapControler(cardLayout);
    private ProductPanel productPanel = new ProductPanel(dataSource);
    private SellerPanel sellerPanel = new SellerPanel(dataSource);
    private JPanel mainPanel;

    public MainAplcation() throws UnsupportedLookAndFeelException, ClassNotFoundException
    {
        UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(productPanel);
        mainPanel.add(sellerPanel);
        JButton swapButton = new JButton("Swap Panels");
        swapButton.addActionListener( e -> {swapControler.next(mainPanel);});
        add(mainPanel, BorderLayout.CENTER);
        add(swapButton, BorderLayout.SOUTH);
        pack();
    }
}
