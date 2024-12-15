package dashboard.swing;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuButton extends JButton {

    private int index; // Chỉ số của mục menu
    private Animator animator; // Animation
    private int targetSize; // Kích thước hiệu ứng
    private float animatSize; // Kích thước hiệu ứng hiện tại
    private Point pressedPoint; // Tọa độ nhấn chuột
    private float alpha; // Độ trong suốt của hiệu ứng
    private Color effectColor = new Color(255, 255, 255, 150); // Màu hiệu ứng khi nhấp chuột

    private boolean isHovered = false; // Trạng thái hover
    private boolean isSelected = false; // Trạng thái được chọn
    private final Color hoverOverlayColor = new Color(12, 243, 34); // Lớp phủ màu tối khi hover
    private final Color selectedOverlayColor = new Color(0, 0, 0, 100); // Lớp phủ màu tối khi được chọn

    // Danh sách chứa tất cả các nút
    private static final List<MenuButton> buttons = new ArrayList<>();

    public MenuButton(Icon icon, String text) {
        super(text);
        setIcon(icon);
        init();
        setBorder(new EmptyBorder(1, 20, 1, 1));
        buttons.add(this); // Thêm nút vào danh sách
    }

    public MenuButton(String text) {
        super(text);
        init();
        setBorder(new EmptyBorder(1, 50, 1, 1));
        buttons.add(this); // Thêm nút vào danh sách
    }

    public MenuButton(String text, boolean subMenu) {
        super(text);
        init();
        buttons.add(this); // Thêm nút vào danh sách
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private void init() {
        setContentAreaFilled(false); // Tắt nền mặc định
        setForeground(new Color(255, 255, 255)); // Màu chữ mặc định
        setHorizontalAlignment(JButton.LEFT); // Căn lề trái
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Thay đổi con trỏ chuột

        // Gắn sự kiện chuột
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                isHovered = true; // Kích hoạt trạng thái hover
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                isHovered = false; // Tắt trạng thái hover
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent me) {
                // Xử lý hiệu ứng click
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = me.getPoint();
                alpha = 0.5f;

                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();

                // Đặt trạng thái selected cho nút hiện tại
                setSelectedButton(MenuButton.this);
            }
        });

        // Cài đặt animation
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize;
                repaint();
            }
        };

        animator = new Animator(400, target);
        animator.setResolution(0);
    }

    // Phương thức để đặt nút được chọn
    private static void setSelectedButton(MenuButton selectedButton) {
        for (MenuButton button : buttons) {
            button.isSelected = false; // Hủy chọn tất cả các nút
            button.repaint();
        }
        selectedButton.isSelected = true; // Chọn nút hiện tại
        selectedButton.repaint();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Giữ nguyên vẽ nền của JButton
        super.paintComponent(g2);

        // Vẽ hiệu ứng nhấp chuột
        if (pressedPoint != null) {
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize);
        }

        // Hiển thị overlay màu tối khi hover hoặc được chọn
        if (isHovered) {
            g2.setColor(hoverOverlayColor); // Màu overlay khi hover
            g2.fillRect(0, 0, getWidth(), getHeight());
        } else if (isSelected) {
            g2.setColor(selectedOverlayColor); // Màu overlay khi được chọn
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Button Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(50, 50, 50)); // Đặt màu nền

        // Nút menu "Dashboard"
        MenuButton button1 = new MenuButton("Dashboard");
        button1.setBounds(50, 50, 200, 40);

        // Nút menu "Settings"
        MenuButton button2 = new MenuButton("Settings");
        button2.setBounds(50, 120, 200, 40);

        // Nút menu "Profile"
        MenuButton button3 = new MenuButton("Profile");
        button3.setBounds(50, 190, 200, 40);

        frame.add(button1);
        frame.add(button2);
        frame.add(button3);

        frame.setVisible(true);
    }
}
