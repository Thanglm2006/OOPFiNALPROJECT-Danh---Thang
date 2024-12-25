package Component.dialog;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.Interpolator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Mess extends JDialog {

    private Background content;
    private Animator animator;
    private Dimension originalSize;
    private Point originalLocation;
    private boolean show;
    private int w=0;
    private int h=0;


    public Mess(JPanel panel) {
        super((Frame) SwingUtilities.getWindowAncestor(panel), true);
        init();
    }

    private void init() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        content = new Background();
        content.setBackground(Color.WHITE);
        setContentPane(content);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void end() {
                if (!show) {
                    dispose();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f = show ? fraction : 1f - fraction;
                int w = (int) (originalSize.width * f);
                int h = (int) (originalSize.height * f);
                int x = originalLocation.x - w / 2;
                int y = originalLocation.y - h / 2;
                setLocation(x, y);
                setSize(new Dimension(w, h));
            }
        };
        animator = new Animator(500, target);
        animator.setInterpolator(new Interpolator() {
            @Override
            public float interpolate(float f) {
                return show ? easeOutBounce(f) : easeOutExpo(f);
            }
        });
        animator.setResolution(0);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                closeAlert();
            }
        });
    }

    private float easeOutBounce(float x) {
        float n1 = 7.5625f;
        float d1 = 2.75f;
        if (x < 1 / d1) {
            return n1 * x * x;
        } else if (x < 2 / d1) {
            return n1 * (x -= 1.5 / d1) * x + 0.75f;
        } else if (x < 2.5 / d1) {
            return n1 * (x -= 2.25 / d1) * x + 0.9375f;
        } else {
            return n1 * (x -= 2.625 / d1) * x + 0.984375f;
        }
    }

    private float easeOutExpo(float x) {
        return x == 1 ? 1 : 1 - (float) Math.pow(2, -10 * x);
    }

    public void setMess(int w, int h){
        this.w = w;
        this.h = h;
    }

    public void showAlert() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        originalSize = getPreferredSize();
        originalSize = new Dimension(originalSize.width + w, originalSize.height+ h);
        originalLocation = getLocationRelativeToParent();
        setSize(new Dimension(0, 0));
        if (animator.isRunning()) {
            animator.stop();
        }
        show = true;
        animator.setDuration(500);
        animator.start();
        setVisible(true);
    }

    public void closeAlert() {
        if (animator.isRunning()) {
            animator.stop();
        }
        show = false;
        animator.setDuration(400);
        animator.start();
    }

    private Point getLocationRelativeToParent() {
        Container parent = getParent();
        Point location = parent.getLocationOnScreen();
        Dimension size = parent.getSize();
        int x = location.x + size.width / 2;
        int y = location.y + size.height / 2;
        return new Point(x, y);
    }

    private class Background extends JComponent {

        @Override
        protected void paintComponent(Graphics grphcs) {
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
            super.paintComponent(grphcs);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Mess Dialog");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLayout(new BorderLayout());

            JPanel mainPanel = new JPanel(new FlowLayout());
            frame.add(mainPanel, BorderLayout.CENTER);

            JButton btnShowDialog = new JButton("Show Dialog");
            btnShowDialog.addActionListener(e -> {
                Mess dialog = new Mess(mainPanel);

                dialog.setMess(300,400);
                dialog.showAlert();
            });

            mainPanel.add(btnShowDialog);
            frame.setVisible(true);
        });
    }
}
