package Component.dialog;

import net.miginfocom.swing.MigLayout;
import Component.Button.Button;

import javax.swing.*;
import java.awt.*;

public class Notice extends Mess {
    private JLabel image, text, error;
    private Button ok;
    private boolean modal;
    private NoticeAction noticeAction;

    public Notice(JPanel parent, boolean modal) {
        super(parent);
        this.modal = modal;
        setModalityType(modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
        initComponents();
    }

    public JLabel getImage() {
        return image;
    }

    public JLabel getTextLabel() {
        return text;
    }

    public JLabel getError() {
        return error;
    }

    public Button getOk() {
        return ok;
    }

    @Override
    public boolean isModal() {
        return modal;
    }

    public NoticeAction getNoticeAction() {
        return noticeAction;
    }

    public void setNoticeAction(NoticeAction action) {
        this.noticeAction = action;
    }

    public void setModal(boolean modal) {
        this.modal = modal;
        setModalityType(modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
    }

    public void setIcon(String iconPath) {
        image.setIcon(new ImageIcon(getClass().getResource(iconPath)));
    }

    public void setError(String errorMessage) {
        error.setText(errorMessage);
    }

    public void setText(String message) {
        text.setText(message);
    }


    public void setError(String errorMessage, Color color) {
        error.setText(errorMessage);
        error.setForeground(color);
        repaint();
    }


    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        MigLayout layout = new MigLayout("al center center, wrap");
        setLayout(layout);

        image = new JLabel();
        image.setIcon(new ImageIcon(getClass().getResource("/Image/delete_2.png")));
        add(image, "y 4%, center");

        error = new JLabel();
        error.setFont(new Font("Arial", Font.BOLD, 36));
        error.setForeground(new Color(234, 32, 39));
        error.setText("ERROR");
//        add(error, "y 33%, center");

        text = new JLabel();
        text.setFont(new Font("Arial", Font.PLAIN, 18));
        text.setForeground(new Color(143, 143, 143));
        text.setText("Mật khẩu của bạn không chính xác!");
//        add(text, "y 50%, center");

        ok = new Button();
        ok.setText("OK");
        ok.setBackground(new Color(27, 156, 252));
        ok.setForeground(Color.WHITE);;
        ok.setFont(new Font("Arial", Font.BOLD, 22));

        add(ok, "width 28%, height 15%, y 82%, center");

        ok.addActionListener(e -> closeAlert());

        ok.addActionListener(e -> {
            if (noticeAction != null) {
                noticeAction.onOk();
            }
            closeAlert();
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Notice Dialog");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            JPanel mainPanel = new JPanel(new FlowLayout());
            frame.add(mainPanel, BorderLayout.CENTER);

            JButton btnShowNotice = new JButton("Show Notice");
            btnShowNotice.addActionListener(e -> {
                Notice notice = new Notice(mainPanel, true);
                notice.setError("ERROR");
                notice.setText("Mật");

                notice.showAlert();
            });

            mainPanel.add(btnShowNotice);
            frame.setVisible(true);
        });
    }
}

