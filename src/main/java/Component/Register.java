package Component;

import Res.SQLQueries;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;  // Import MigLayout

public class Register extends JPanel {
    private SQLQueries sql;
    private JButton backlogin;
    private JLabel  l2, l3, l4, l6, l7;
    Message m;
    private Button register;
    private PasswordField pass, pass1;
    private TextField username;
    private TextField email, fullName;
    private DateField Birth;
    private DatePicker birth;
    private JRadioButton jRadioButton_Student, jRadioButton_Teacher;
    private ButtonGroup buttonGroup;
    private String birthdate;

    public Register() {
        initComponents();
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

        sql = new SQLQueries();
        birth = new DatePicker();
        username = new TextField();

        m = new Message(new Frame(),false);

        l2 = new JLabel();
        pass = new PasswordField();
        pass.setLabelText("Mật khẩu");
        l3 = new JLabel();
        register = new Button();
        backlogin = new Button();
        l4 = new JLabel();
        pass1 = new PasswordField();
        pass1.setLabelText("Nhập lại mật khẩu");
        email = new TextField();
        email.setLabelText("Email");

        l6 = new JLabel();
        fullName = new TextField();
        fullName.setLabelText("Họ và tên");
        Birth = new DateField();
        jRadioButton_Student = new JRadioButton();
        jRadioButton_Teacher = new JRadioButton();
        l7 = new JLabel();



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

        l2.setFont(new Font("sansserif", 1, 36));
        l2.setForeground(new Color(69, 68, 68));
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        l2.setText("Đăng kí");

        register.setBackground(new Color(25, 182, 247));
        register.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        register.setForeground(new Color(255, 255, 255));
        register.setText("Đăng kí");
        register.setFont(new Font("sansserif", 1, 15));
        register.addActionListener(e -> {
            if (getUser().isEmpty()) {
                m.showMessage("Tên đăng nhập không được để trống");

            } else if ((jRadioButton_Teacher.isSelected() && !getUser().matches("GV\\S+")) || (jRadioButton_Student.isSelected() && !getUser().matches("SV\\S+"))) {
                m.showMessage("Vui lòng đặt tên đăng nhập theo đúng định dạng");
            } else if (!jRadioButton_Student.isSelected() && !jRadioButton_Teacher.isSelected()) {
                m.showMessage("Vui lòng chọn loại tài khoản");
            } else if (pass.getText().isEmpty()) {
                m.showMessage("Mật khẩu không được để trống!");
            } else if (pass.getText().length() < 8) {
                m.showMessage("Mật khẩu phải trên 8 kí tự!");
            } else if (!pass.getText().equals(pass1.getText())) {
                m.showMessage("Hãy nhập đúng mật khẩu đã nhập ở trên");
            } else if (email.getText().isEmpty()) {
                m.showMessage("Vui lòng nhập email!");
            } else if (!email.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                m.showMessage("Nhập email theo định dạng example@domain.com");
            } else if (fullName.getText().isEmpty()) {
                m.showMessage("Vui lòng nhập họ và tên!");
            } else if (birthdate == null) {
                m.setForeground(Color.RED);
                m.showMessage("Vui lòng nhập ngày sinh!");
            } else {
                if (jRadioButton_Teacher.isSelected()) {
                    boolean check = sql.insertTeacher(username.getText(), pass.getText(), fullName.getText(), email.getText(), birthdate);
                    if (!check) {
                        m.showMessage("Tên đăng nhập đã tồn tại!");
                    } else {
                        clearFields();
                    }
                } else {
                    boolean check = sql.insertStudent(username.getText(), pass.getText(), fullName.getText(), email.getText(), birthdate);
                    if (!check) {
                        m.showMessage("Tên đăng nhập đã tồn tại!");
                        m.setForeground(Color.red);
                    } else {

                        clearFields();
                    }
                }
            }
        });

        backlogin.setFont(new Font("sansserif", 1, 15));
        backlogin.setForeground(new Color(30, 122, 236));
        backlogin.setText("Bạn đã có tài khoản?");
        backlogin.setContentAreaFilled(false);
        backlogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jRadioButton_Student.setText("Sinh viên/ Học sinh");
        l7.setText("Loại tài khoản");
        jRadioButton_Teacher.setText("Giáo viên");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButton_Student);
        buttonGroup.add(jRadioButton_Teacher);

        username.setLabelText("Tên đăng nhập (không dấu)");
        username.setText("Nếu là sinh viên: đặt là SV...; nếu là giáo viên đặt là GV...");
        username.setForeground(Color.gray);

        username.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(username.getText().equalsIgnoreCase("sinh viên: đặt là SV...; giáo viên đặt là GV...")||username.getText().length()>30){
                    username.setText("");
                    username.setForeground(Color.BLACK);
                }
                else if(username.getText().equalsIgnoreCase("")){
                    username.setText("nếu là sinh viên: đặt là SV...; nếu là giáo viên đặt là GV...");
                    username.setForeground(Color.gray);
                }
            }
        });




        l6.setFont(new Font("Segoe UI", 0, 10));


        setLayout(new MigLayout("al center center, wrap"));

        add(l2, "width 75%, y 1%");
        add(username, "width 75%, y 12%");
        add(l4);
        add(pass, "width 75%, y 21%");
        add(l3); add(l6);
        add(pass1, "width 75%, y 30%");
        add(email, "width 75%, y 39%");
        add(fullName, "width 75%, y 48%");
        add(Birth, "width 75%, y 57%");
        add(l7,"width 75%, y 66%");
        add(jRadioButton_Student, "split 2, y 70%");
        add(jRadioButton_Teacher, "wrap, y 70%");
        add(register, "width 75%, y 80%");
        add(backlogin, "width 75%, y 90%");
    }

    private void clearFields() {

        email.setText("");
        fullName.setText("");
        username.setText("");
        pass.setText("");
        pass1.setText("");
        Birth.setText("");
    }
}
