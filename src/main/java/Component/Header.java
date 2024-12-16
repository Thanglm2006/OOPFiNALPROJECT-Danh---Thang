package Component;

import Component.scrollbar.Button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Header extends JPanel {

    public Header(String user,String Type) {
        initComponents(user,Type);
    }

    public void addMenuEvent(ActionListener event) {
        cmdMenu.addActionListener(event);
    }

    private void initComponents(String user,String Type) {

        cmdMenu = new Button();
        pic = new ImageAvatar();
        lbUserName = new JLabel();
        lbRole = new JLabel();
        jSeparator1 = new JSeparator();
        buttonBadges1 = new ButtonBadges();
        buttonBadges2 = new ButtonBadges();

        setBackground(new java.awt.Color(255, 255, 255));

        cmdMenu.setIcon(new ImageIcon(getClass().getResource("/Image/icon/menu.png")));
        pic.setIcon(new ImageIcon(getClass().getResource("/Image/student.jpg")));

        lbUserName.setFont(new java.awt.Font("sansserif", 1, 12));
        lbUserName.setForeground(new java.awt.Color(127, 127, 127));
        lbUserName.setText(Type);

        lbRole.setForeground(new java.awt.Color(127, 127, 127));
        lbRole.setText(user);

        jSeparator1.setOrientation(SwingConstants.VERTICAL);

        buttonBadges1.setForeground(new java.awt.Color(250, 49, 49));
        buttonBadges1.setIcon(new ImageIcon(getClass().getResource("/Image/icon/notification.png")));
        buttonBadges1.setBadges(12);

        buttonBadges2.setForeground(new java.awt.Color(63, 178, 232));
        buttonBadges2.setIcon(new ImageIcon(getClass().getResource("/Image/icon/message.png")));
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


        Header header = new Header("Danh hanma","User");
        frame.add(header);


        frame.setSize(800, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private ButtonBadges buttonBadges1;
    private ButtonBadges buttonBadges2;
    private Button cmdMenu;
    private JSeparator jSeparator1;
    private JLabel lbRole;
    private JLabel lbUserName;
    private ImageAvatar pic;

}

