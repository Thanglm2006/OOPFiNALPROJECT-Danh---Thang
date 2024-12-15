package Swing.form;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainForm extends JPanel {

    public MainForm() {
        initComponents();
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 20, 10, 20));

    }

    public void showForm(Component form) {
        removeAll();
        add(form);
        repaint();
        revalidate();

    }

    private void initComponents() {

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );
    }
}
