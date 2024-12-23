package Component.MenuAndHeader;

import Component.PanelPrototype.Profile;
import event.EventMenu;
import event.EventMenuSelected;
import event.EventShowPopupMenu;

import Component.scrollbar.ScrollBarCustom;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class MenuTeacher extends JPanel {

    public boolean isShowMenu() {
        return showMenu;
    }

    public void addEvent(EventMenuSelected event) {
        this.event = event;
    }

    public void setEnableMenu(boolean enableMenu) {
        this.enableMenu = enableMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public void addEventShowPopup(EventShowPopupMenu eventShowPopup) {
        this.eventShowPopup = eventShowPopup;
    }

    private final MigLayout layout;
    private EventMenuSelected event;
    private EventShowPopupMenu eventShowPopup;
    private boolean enableMenu = true;
    private boolean showMenu = true;
    public MenuTeacher() {
        initComponents();
        setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setVerticalScrollBar(new ScrollBarCustom());
        layout = new MigLayout("wrap, fillx, insets 0", "[fill]", "[]0[]");
        panel.setLayout(layout);
    }
    public void itemTeacher(String [] classes) {
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/Image/icon/home.png")), "Trang chủ"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/Image/icon/5.png")), "Tài khoản","Thông tin cá nhân","Đổi mật khẩu","Sửa đổi email"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/Image/icon/star.png")), "Danh sách lớp",classes));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/Image/icon/7.png")),"Bài tập","Tạo bài tập","Danh sách bài tập"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/Image/icon/dictionary.png")), "Từ điển"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/Image/icon/logout3.png")), "Đăng xuất"));

    }

    private void addMenu(ModelMenu menu) {
        MenuItem menuItem = new MenuItem(menu, getEventMenu(), event, panel.getComponentCount());
        panel.add(menuItem, "h 40!");
        menuItem.setSelected(false);
    }



    private EventMenu getEventMenu() {
        return new EventMenu() {
            @Override
            public boolean menuPressed(Component com, boolean open) {
                if (enableMenu) {
                    if (isShowMenu()) {
                        if (open) {
                            new MenuAnimation(layout, com).openMenu();

                        } else {
                            new MenuAnimation(layout, com).closeMenu();
                        }
                        return true;
                    } else {
                        eventShowPopup.showPopup(com);
                    }
                }
                return false;
            }
        };
    }

    public void hideallMenu() {
        for (Component com : panel.getComponents()) {
            MenuItem item = (MenuItem) com;
            if (item.isOpen()) {
                new MenuAnimation(layout, com, 500).closeMenu();
                item.setOpen(false);
            }
        }
    }

    private void initComponents() {

        sp = new JScrollPane();
        panel = new JPanel();
        profile1 = new Profile();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setViewportBorder(null);

        panel.setOpaque(false);

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 312, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 523, Short.MAX_VALUE)
        );

        sp.setViewportView(panel);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(sp, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                        .addComponent(profile1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(profile1,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sp, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
        );
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gra = new GradientPaint(0, 0, Color.decode("#2a3f54"), getWidth(), 0, new Color(42,63,84,255));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }


    private JPanel panel;
    private Profile profile1;
    private JScrollPane sp;

}
