package Swing;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ButtonBadges extends JButton {

    public int getBadges() {
        return badges;
    }

    public void setBadges(int badges) {
        this.badges = badges;
    }

    public Color getEffectColor() {
        return effectColor;
    }

    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
    }

    private Animator animator;// hoạt ảnh lan tỏa
    private int targetSize; // Kích thước tối đa
    private float animatSize; // Kích thước hiện tại
    private Point pressedPoint; // xác định vị trí trung tâm
    private float alpha; // độ trong suốt
    private Color effectColor = new Color(173, 173, 173);// Màu sắ hiệu ứng (mặc định là màu xám)
    private int badges; // lưu số lượng huy hiệu trên nút (tin nhắn chưa được đọc)

    public ButtonBadges() {
        setContentAreaFilled(false); // Bỏ viền
        setBorder(new EmptyBorder(5, 5, 5, 5)); // thiết lập khoảng cách rông xung quanh nút để hiệu ứng không bị cắt xén
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                targetSize = Math.max(getWidth(), getHeight()) * 2; // Thiết lập kích thước tối đa
                animatSize = 0; // Kích thước ban đầu bằng 0
                pressedPoint = me.getPoint(); // Lưu tọa độ
                alpha = 0.5f; // Độ mờ 0.5
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }
        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) { // Tiến trình hiệu ứng càng cao độ mờ sẽ càng giảm
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize; // Độ lan tỏa tăng
                repaint(); // Đặt lại ban đầu
            }
        };
        animator = new Animator(400, target);
        animator.setResolution(0); // Đảm bảo đọ mượt
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        // Lấy chiều rộng chieuf cao hiện tại
        int width = getWidth();
        int height = getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // Tạo một  phương thức để vẽ lại hiệu ứng và nút
        Graphics2D g2 = img.createGraphics(); // Dùng graphics 2d để tạo đồ họa phức tạp hơn
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// Khử răng cưa giúp ảnh sắc nét
        g2.setColor(getBackground()); // Đặt màu nền
        g2.fillRoundRect(0, 0, width, height, height, height);// Vẽ hình chữ nhật góc bo tròn bằng chiều cao của nút ( tùy vào kích thước chiều rộng chiều cao độ bo tròn khác nhau)
        // Kiểm tra người dùng đã nhấn nút hay chưa nếu có thif thực hiện yc
        if (pressedPoint != null) {
            g2.setColor(effectColor); // Dổi màu hiệu ứng
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha)); // Đảm bảo hình vẽ được chồng lên các phần tuwre bên dưới
            g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize); //
        }
        g2.dispose(); // Giải phóng tài nguyên say khi vẽ xong
        grphcs.drawImage(img, 0, 0, null); // Vẽ hình ảnh IMG được tạo
        super.paintComponent(grphcs);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        if (badges > 0) {
            String value = badges > 9 ? "+9" : badges + "";
            int width = getWidth();
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            FontMetrics ft = g2.getFontMetrics();
            Rectangle2D r2 = ft.getStringBounds(value, g2);
            int fw = (int) r2.getWidth();
            int fh = (int) r2.getHeight();
            g2.setColor(getForeground());
            int size = Math.max(fw, fh) + 4;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8f));
            g2.fillOval(width - size, 0, size, size);
            int x = (size - fw) / 2;
            g2.setColor(Color.WHITE);
            g2.setComposite(AlphaComposite.SrcOver);
            g2.drawString(value, width - size + x, ft.getAscent() + 1);
            g2.dispose();
        }
    }
}
