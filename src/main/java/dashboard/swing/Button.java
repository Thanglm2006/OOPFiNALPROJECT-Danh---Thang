package dashboard.swing;

import org.jdesktop.animation.timing.Animator; // Điều khiển hoạt ảnh thường là 400ms
import org.jdesktop.animation.timing.TimingTarget; // Xây dựng hoạt ảnh
import org.jdesktop.animation.timing.TimingTargetAdapter;//

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;



//Hiệu ứng khi ấn vào gợn sóng lan tỏa trong Button

public class Button extends JButton {

    public Color getEffectColor() {
        return effectColor;
    }
//Chuyển màu tùy thích
    public void setEffectColor(Color effectColor) {
        this.effectColor = effectColor;
    }

    private Animator animator;// chạy hoạt ảnh
    private int targetSize; // Kích thước lớn nhất của gợn sóng (rộng+cao)*2
    private float animatSize; // Kích thước gợn sóng trong quá trình hoạt ảnh
    private Point pressedPoint; //Tọa độ người dùng nhấn nút là tâm gợn sóng
    private float alpha;// Độ trong suốt của gợn sóng, thay đổi trong quá trình hoạt ảnh.
    private Color effectColor = new Color(173, 173, 173); // màu hiệu ứng gợn sóng

    public Button() {
        setContentAreaFilled(false); // vô hiệu hóa nền mặc định Button
        setBorder(new EmptyBorder(5, 5, 5, 5)); // Tạo khoảng cách panel xung quanh nút
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent me) {
                targetSize = Math.max(getWidth(), getHeight()) * 2; // Tính toán kích thước gợn sóng tối đa
                animatSize = 0;// Đặt kích thước ban đầu gợn sóng là 0
                pressedPoint = me.getPoint(); // Lưu tọa độ điểm nhấn làm tâm hiệu ứng
                alpha = 0.5f; // Độ trong suốt ban đầu là 0.5
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }
        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction; // Khi hiệu ứng dần đến 1 thì độ mờ giảm dần
                }
                animatSize = fraction * targetSize; // Gợn sóng tăng
                repaint();// Cập nhật giao diện khi nút thay đổi
            }
        };
        animator = new Animator(400, target);
        animator.setResolution(0);//đảm bảo hoạt ảnh được vẽ với độ mượt tối đa, không bị giới hạn bởi khoảng thời gian cố định.
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, height, height);
        if (pressedPoint != null) {
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize);
        }
        g2.dispose();
        grphcs.drawImage(img, 0, 0, null);
        super.paintComponent(grphcs);
    }
}
