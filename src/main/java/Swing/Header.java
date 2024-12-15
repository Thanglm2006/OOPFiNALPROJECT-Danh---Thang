package Swing;

import Swing.scrollbar.Button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Header extends JPanel {

    public Header() {
        initComponents();
    }

    public void addMenuEvent(ActionListener event) {
        cmdMenu.addActionListener(event);
    }

    private void initComponents() {

        cmdMenu = new Button();
        pic = new ImageAvatar();
        lbUserName = new JLabel();
        lbRole = new JLabel();
        jSeparator1 = new JSeparator();
        buttonBadges1 = new ButtonBadges();
        buttonBadges2 = new ButtonBadges();

        setBackground(new java.awt.Color(255, 255, 255));

        cmdMenu.setIcon(new ImageIcon(getClass().getResource("/Image/icon/menu.png"))); // NOI18N
//
        pic.setIcon(new ImageIcon(getClass().getResource("/Image/icon/danhhanma.jpg"))); // NOI18N

        lbUserName.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lbUserName.setForeground(new java.awt.Color(127, 127, 127));
        lbUserName.setText("User Name");

        lbRole.setForeground(new java.awt.Color(127, 127, 127));
        lbRole.setText("Danh Hanma");

        jSeparator1.setOrientation(SwingConstants.VERTICAL);

        buttonBadges1.setForeground(new java.awt.Color(250, 49, 49));
        buttonBadges1.setIcon(new ImageIcon(getClass().getResource("/Image/icon/notification.png"))); // NOI18N
        buttonBadges1.setBadges(12);

        buttonBadges2.setForeground(new java.awt.Color(63, 178, 232));
        buttonBadges2.setIcon(new ImageIcon(getClass().getResource("/Image/icon/message.png"))); // NOI18N
        buttonBadges2.setBadges(5);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cmdMenu, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 354, Short.MAX_VALUE)
                                .addComponent(buttonBadges2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(buttonBadges1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 8, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lbUserName, GroupLayout.Alignment.TRAILING)
                                        .addComponent(lbRole, GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addComponent(pic, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbUserName)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbRole))
                                        .addComponent(cmdMenu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pic, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator1)
                                        .addComponent(buttonBadges1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonBadges2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Header");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Header header = new Header();
        frame.add(header);

        // Thiết lập kích thước và hiển thị
        frame.setSize(800, 100); // Chiều ngang 800, chiều cao 100
        frame.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
        frame.setVisible(true); // Hiển thị giao diện
    }

    private ButtonBadges buttonBadges1;
    private ButtonBadges buttonBadges2;
    private Button cmdMenu;
    private JSeparator jSeparator1;
    private JLabel lbRole;
    private JLabel lbUserName;
    private ImageAvatar pic;

}

