package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPageForTeacher extends JFrame {
    public final int T =100000000;
    public MainPageForTeacher(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setPreferredSize(new Dimension(1280,700));
        JPanel p= new JPanel();
        p.setLayout(null);
        p.setSize(1280,700);
        JButton MenuButton = new JButton();
        ImageIcon i1 = new ImageIcon(MainPageForTeacher.class.getResource("/menuBut.png"));
        Image img = i1.getImage(); // Get the original image
        Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale the image
        ImageIcon scaledIcon = new ImageIcon(scaledImg); //
        MenuButton.setIcon(scaledIcon);
        MenuButton.setBorderPainted(false);
//        MenuButton.addActionListener(e -> {
//            MenuButton.setBorderPainted(true);
//            Timer t= new Timer(0, new ActionListener() {
//                int i=0;
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    i++;
//                    while(i<T) i++;
//                    MenuButton.setBorderPainted(false);
//                }
//            });
//            t.start();
//        });
        MenuButton.setSize(50,50);
        MenuButton.setLocation(0,0);
        p.add(MenuButton);
        add(p);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainPageForTeacher();
    }
}
