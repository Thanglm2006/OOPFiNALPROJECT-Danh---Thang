package Swing.form;

import javax.swing.*;
import java.awt.*;

public class Form2 extends JPanel {

    public Form2() {
        initComponents();
        setOpaque(false);
    }

    private void initComponents() {

        jLabel1 = new JLabel();

        jLabel1.setFont(new Font("sansserif", 1, 48));
        jLabel1.setForeground(new Color(159, 159, 159));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Danh Hanma");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }
    private JLabel jLabel1;

}

