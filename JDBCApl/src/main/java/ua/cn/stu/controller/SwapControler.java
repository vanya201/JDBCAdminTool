package ua.cn.stu.controller;

import javax.swing.*;
import java.awt.*;

public class SwapControler {
    CardLayout cardLayout = null;

    public SwapControler(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public void next(JPanel from) {
        cardLayout.next(from);
    }

    public void prev(JPanel from) {
        cardLayout.next(from);
    }
}
