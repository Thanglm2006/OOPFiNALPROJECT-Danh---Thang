package dashboard.swing;

import dashboard.event.EventMenu;
import dashboard.event.EventMenuSelected;
import dashboard.model.ModelMenu;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuItem extends javax.swing.JPanel {

    private float alpha;
    private ModelMenu menu;
    private boolean open;
    private EventMenuSelected eventSelected;
    private int index;
    private boolean isSelected = false;


    public ModelMenu getMenu() {
        return menu;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public EventMenuSelected getEventSelected() {
        return eventSelected;
    }

    public void setEventSelected(EventMenuSelected eventSelected) {
        this.eventSelected = eventSelected;
    }

    public int getIndex() {
        return index;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint(); // Vẽ lại để áp dụng màu mới
    }

    public MenuItem(ModelMenu menu, EventMenu event, EventMenuSelected eventSelected, int index) {
        initComponents();
        this.menu = menu;
        this.eventSelected = eventSelected;
        this.index = index;

        setOpaque(false);
        setLayout(new MigLayout("wrap, fillx, insets 0", "[fill]", "[fill, 40!]0[fill, 35!]"));
        MenuButton firstItem = new MenuButton(menu.getIcon(), "      " + menu.getMenuName());
        firstItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (menu.getSubMenu().length > 0 ) {

                    if (event.menuPressed(MenuItem.this, !open)) {
                        open = !open;
                    }
                }
                setSelected(true);
                eventSelected.menuSelected(index, -1);

            }
        });
        add(firstItem);

        int subMenuIndex = -1;

        for (String st : menu.getSubMenu()) {
            MenuButton item = new MenuButton(st);
            item.setIndex(++subMenuIndex);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    eventSelected.menuSelected(index, item.getIndex());
                }
            });
            add(item);
        }


    }




    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getPreferredSize().height;
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(50, 50, 50));

//        if(isSelected) g2.setColor(new Color(100,140,237));
//        else g2.setColor(new Color(50,50,50));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(0, 2, width, 38);
        g2.setComposite(AlphaComposite.SrcOver);
        g2.fillRect(0, 40, width, height - 40);
        g2.setColor(new Color(100, 100, 100));
        g2.drawLine(30, 40, 30, height - 17);

//      Vẽ đường phân cách
        for (int i = 0; i < menu.getSubMenu().length; i++) {
            int y = ((i + 1) * 35 + 40) - 17;
            g2.drawLine(30, y, 38, y);
        }
        // Vẽ mũi tên chỉ
        if (menu.getSubMenu().length > 0) {
            createArrowButton(g2);
        }
        super.paintComponent(grphcs);
    }

//     Vẽ mũi tên
    private void createArrowButton(Graphics2D g2) {
        int size = 4;
        int y = 19;
        int x = 205;
        g2.setColor(new Color(230, 230, 230));
        float ay = alpha * size;
        float ay1 = (1f - alpha) * size;
        g2.drawLine(x, (int) (y + ay), x + 4, (int) (y + ay1));
        g2.drawLine(x + 4, (int) (y + ay1), x + 8, (int) (y + ay));
    }


}
