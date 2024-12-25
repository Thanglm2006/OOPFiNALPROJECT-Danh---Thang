package Component.PanelPrototype;

import Component.Button.Combobox;
import Component.Button.Message;
import Component.TextFieldAndSoOn.DateField;
import Component.TextFieldAndSoOn.PasswordField;
import Component.TextFieldAndSoOn.TextField;
import Component.Button.Button;

import Component.dialog.Notice;
import Res.SQLQueries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Register extends JPanel {
    private SQLQueries sql;
    private JButton backlogin;
    private JLabel l2;
    private Message m;
    private Button register;
    private PasswordField pass, pass1;
    private TextField username;
    private TextField email, fullName;
    private DateField Birth;
    private DatePicker birth;
    private String birthdate;
    private Combobox combobox;
    private Combobox sexgay;
    private Argon2 argon2;

    public Register() {
        initComponents();
        argon2 = Argon2Factory.create();
    }

    public void register() {
        username.grabFocus();
    }

    private ActionListener ac;

    public void addEventBackLogin(ActionListener event) {
        backlogin.addActionListener(event);
        ac = event;
    }

    public String getUser() {
        return username.getText();
    }

    public String getPass1() {
        return pass1.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getFullName() {
        return fullName.getText();
    }

    public String getBirth() {
        return birthdate;
    }

    private void initComponents() {
        setLayout(new MigLayout("al center center, wrap"));
        sql = new SQLQueries();
        birth = new DatePicker();
        username = new TextField();
        m = new Message(new Frame(), false);

        l2 = new JLabel();
        pass = new PasswordField();
        pass.setLabelText("Mật khẩu");
        pass.setShowAndHide(true);

        register = new Button();
        backlogin = new Button();
        pass1 = new PasswordField();
        pass1.setLabelText("Nhập lại mật khẩu");
        pass1.setShowAndHide(true);

        email = new TextField();
        email.setLabelText("Email");

        fullName = new TextField();
        fullName.setLabelText("Họ và tên");

        Birth = new DateField();

        combobox = new Combobox();
        combobox.setModel(new DefaultComboBoxModel<>(new String[]{"Học sinh", "Giáo viên"}));
        combobox.setSelectedIndex(-1);
        combobox.setLabeText("Loại tài khoản");

        sexgay = new Combobox();
        sexgay.setModel(new DefaultComboBoxModel<>(new String[]{"Nam", "Nữ", "Không muốn tiết lộ"}));
        sexgay.setSelectedIndex(-1);
        sexgay.setLabeText("Giới tính");

        add(l2, "width 75%, y 1%");
        add(username, "width 75%, y 12%");
        add(pass, "width 75%, y 21%");
        add(pass1, "width 75%, y 30%");
        add(email, "width 75%, y 39%");
        add(fullName, "width 75%, y 48%");
        add(sexgay, "width 40%, y 55%");
        add(Birth, "width 40%, y 65%");
        add(combobox, "width 40%, y 72%");
        add(register, "width 75%, y 85%");
        add(backlogin, "width 75%, y 92%");

        birth.setEditor(Birth);
        birth.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {
                LocalDate dates = birth.getSelectedDate();
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                if (dates != null) {
                    birthdate = df.format(dates);
                }
            }
        });

        birth.setDateSelectionAble(localDate ->
                !localDate.isAfter(LocalDate.now()) && !localDate.isBefore(LocalDate.of(1900, 1, 1))
        );
        birth.setColor(Color.CYAN);

        setBackground(new Color(255, 255, 255));

        l2.setFont(new Font("sansserif", Font.BOLD, 36));
        l2.setForeground(new Color(69, 68, 68));
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        l2.setText("Đăng ký");

        register.setBackground(new Color(25, 182, 247));
        register.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        register.setForeground(Color.WHITE);
        register.setText("Đăng ký");
        register.setFont(new Font("sansserif", Font.BOLD, 15));
        register.addActionListener(e -> handleRegister());

        backlogin.setFont(new Font("sansserif", Font.BOLD, 15));
        backlogin.setForeground(new Color(30, 122, 236));
        backlogin.setText("Bạn đã có tài khoản?");
        backlogin.setContentAreaFilled(false);
        backlogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        username.setLabelText("Tên đăng nhập (không dấu)");
    }

    private String hashPasswordArgon2(String password) {
        try {
            return argon2.hash(2, 65536, 1, password);
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }

    private void handleRegister() {
        if (username.getText().isEmpty()) {
            m.showMessage("Tên đăng nhập không được để trống");
        } else if ((combobox.getSelectedIndex() != 0 && !getUser().matches("GV\\S+")) ||
                (combobox.getSelectedIndex() != 1 && !getUser().matches("SV\\S+"))) {
            m.showMessage("Tên đăng nhập và loại tài khoản không khớp!");
        } else if (pass.getText().isEmpty()) {
            m.showMessage("Mật khẩu không được để trống!");
        } else if (pass.getText().length() < 8) {
            m.showMessage("Mật khẩu phải trên 8 ký tự!");
        } else if (!pass.getText().equals(pass1.getText())) {
            m.showMessage("Hãy nhập đúng mật khẩu đã nhập ở trên!");
        } else if (email.getText().isEmpty()) {
            m.showMessage("Vui lòng nhập email!");
        } else if (!email.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            m.showMessage("Nhập email theo định dạng example@domain.com");
        } else if (fullName.getText().isEmpty()) {
            m.showMessage("Vui lòng nhập họ và tên!");
        } else if (birthdate == null) {
            m.showMessage("Vui lòng nhập ngày sinh!");
        } else {
            String hashedPassword = hashPasswordArgon2(pass.getText());
            boolean check = combobox.getSelectedIndex() == 1
                    ? sql.insertTeacher(username.getText(), hashedPassword, fullName.getText(),
                    sexgay.getSelectedItem().toString(), email.getText(), birthdate)
                    : sql.insertStudent(username.getText(), hashedPassword, fullName.getText(),
                    sexgay.getSelectedItem().toString(), email.getText(), birthdate);
            if (!check) {
                m.showMessage("Tên đăng nhập đã tồn tại!");
            } else {
                showSuccessNotice();
                ac.actionPerformed(null);
                clearFields();
            }
        }
    }

    private void showSuccessNotice() {
        Notice notice = new Notice(Register.this, true);
        notice.setPreferredSize(new Dimension(550, 370));
        notice.setIcon("/Image/success.png");
        notice.setText("Đăng ký thành công");
        notice.setError("SUCCESS", new Color(12, 233, 12));
        notice.add(notice.getTextLabel(), "y 65%, center");
        notice.add(notice.getError(), "y 40%, center");

        notice.showAlert();
    }

    private void clearFields() {
        email.setText("");
        fullName.setText("");
        username.setText("");
        pass.setText("");
        pass1.setText("");
        Birth.setText("");
        combobox.setSelectedIndex(-1);
        sexgay.setSelectedIndex(-1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Đăng ký");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 800);
            frame.add(new Register());
            frame.setVisible(true);
        });
    }
}
