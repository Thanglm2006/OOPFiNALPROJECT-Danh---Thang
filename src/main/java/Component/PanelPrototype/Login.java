package Component.PanelPrototype;
import Component.TextFieldAndSoOn.TextField;
import Component.TextFieldAndSoOn.PasswordField;
import Component.dialog.Notice;
import Component.mainFrames.FrameForStudent;
import Component.mainFrames.FrameForTeacher;
import Res.AutomticMail;
import Res.SQLQueries;
import Component.Button.Button;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JPanel {
    private Button register;
    private SQLQueries sql;
    private Button forget;
    private JLabel j1, m1;
    private Button login;
    private PasswordField password;
    private TextField username;
    int id;
    private Argon2 argon2;
    String user;
    private AutomticMail mail;
    private ActionListener ac;
    private JPanel This;
    private Notice notice;

    public Login() {
        sql= new SQLQueries();
        initComponents();
    }

    public void login() {
        username.grabFocus();
    }
    public SQLQueries getSql() {
        return sql;
    }
    public void addEventRegister(ActionListener event) {
        register.addActionListener(event);
    }

    public void addEventLogin(ActionListener event) {
        ac = event;
    }

    public String getAcc() {
        return username.getText();
    }

    public String getPass() {
        return password.getText();
    }
    public void addEventForget(ActionListener event) {
        forget.addActionListener(event);
    }
    private void initComponents() {
        This = this;
        argon2 = Argon2Factory.create();
        notice = new Notice(new JPanel(), false);
        mail = new AutomticMail(sql);
        username = new TextField();
        username.setLabelText("Tên đăng nhập");

        j1 = new JLabel();
        password = new PasswordField();
        password.setLabelText("Mật khẩu");
        password.setShowAndHide(true);

        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    loginUser();
                }
            }
        });

        login = new Button();
        register = new Button();
        m1 = new JLabel();
        m1.setForeground(Color.RED);
        m1.setFont(new Font("sansserif", 0, 13));
        forget = new Button();

        setBackground(new Color(255, 255, 255));

        j1.setFont(new Font("sansserif", 1, 36));
        j1.setForeground(new Color(69, 68, 68));
        j1.setHorizontalAlignment(SwingConstants.CENTER);
        j1.setText("Đăng nhập");

        login.setBackground(new Color(25, 182, 247));
        login.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        login.setForeground(new Color(255, 255, 255));
        login.setText("Đăng nhập");
        login.setFont(new Font("SansSerif", 1, 15));

        login.addActionListener(e -> {
            loginUser();
        });

        register.setBackground(new Color(25, 182, 247));
        register.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        register.setForeground(new Color(255, 255, 255));
        register.setText("Tạo tài khoản mới");
        register.setFont(new Font("sansserif", 1, 15));
        register.setCursor(new Cursor(Cursor.HAND_CURSOR));

        forget.setFont(new Font("SansSerif", 1, 15));
        forget.setForeground(new Color(30, 122, 236));
        forget.setHorizontalAlignment(SwingConstants.CENTER);
        forget.setText("<html><u>Quên mật khẩu?<u></html>");
        forget.setCursor(new Cursor(Cursor.HAND_CURSOR));

        setLayout(new MigLayout("al center center, wrap"));

        add(j1, "wrap, width 75%, y 12%");
        add(username, "wrap, width 75%");
        add(password, "wrap, width 75%");
        add(m1, "wrap");
        add(login, "wrap, width 75%, y 57%");
        add(forget, "wrap, center, width 75%,y 65%");
        add(register, "wrap, width 75%, y 85%");
    }

    private void loginUser() {
        m1.setText("");
        if (getAcc().matches("SV\\S+") || getAcc().matches("sv\\S+")) {
            ResultSet res = sql.LoginStudent(getAcc());
            String passHash = null;  // Mật khẩu đã được mã hóa
            int id = 0;
            String Name = null;

            if (res != null) {
                try {
                    while (res.next()) {
                        passHash = res.getNString("PassWord");  // Lấy mật khẩu mã hóa
                        id = res.getInt("StudentID");
                        Name = res.getNString("StudentName");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            if (getAcc().isEmpty()) {
                m1.setText("Vui lòng nhập tên đăng nhập!");
            } else if (passHash == null) {
                m1.setText("Tài khoản không tồn tại!");
            } else {
                // Sử dụng Argon2 để kiểm tra mật khẩu

                if (argon2.verify(passHash, getPass())) {
                    m1.setText("");
                    username.setText("");
                    password.setText("");

                    showSuccessNoticeST(id, Name);
                } else {
                    m1.setText("Sai mật khẩu!");
                }
            }
        } else {
            ResultSet res = sql.LoginTeacher(getAcc());
            String passHash = null;
            int id = 0;
            String Name=null;
            if (res != null) {
                try {
                    while (res.next()) {
                        passHash = res.getNString("PassWord");  // Mật khẩu đã được mã hóa
                        id = res.getInt("TeacherID");
                        Name=res.getNString("TeacherName");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            if (getAcc().isEmpty()) {
                m1.setText("Vui lòng nhập tên đăng nhập!");
            } else if (passHash == null || passHash.equals("no acc")) {
                m1.setText("Tài khoản không tồn tại!");
            } else {
                // Sử dụng Argon2 để kiểm tra mật khẩu
                Argon2 argon2 = Argon2Factory.create();
                if (argon2.verify(passHash, getPass())) {
                    m1.setText("");
                    username.setText("");
                    password.setText("");
                    showSuccessNoticeT(id, Name);
                } else {
                    m1.setText("Sai mật khẩu!");
                }
            }
        }
    }

    private void showSuccessNoticeST(int id, String name) {
        FrameForStudent f = new FrameForStudent(id, name);
        notice.setLayout(new MigLayout("al center center, wrap"));
        notice = new Notice(Login.this, false);
        notice.setError("SUCCESS", new Color(12, 233, 12));
        notice.setText("Đăng nhập thành công");
        notice.setPreferredSize(new Dimension(550, 370));
        notice.setIcon("/Image/success.png");
        notice.add(notice.getTextLabel(),"y 65% ,center");
        notice.add(notice.getError(),"y 40%, center");
        notice.setNoticeAction(() -> {
            f.setVisible(true);
            Window win = SwingUtilities.getWindowAncestor(This);
            win.setVisible(false);
            f.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    win.setVisible(true);
                }
            });
        });

        notice.showAlert();
    }
    private void showSuccessNoticeT(int id, String name) {
        FrameForTeacher f = new FrameForTeacher(id, name);
        notice.setLayout(new MigLayout("al center center, wrap"));
        notice = new Notice(Login.this, false);
        notice.setError("SUCCESS", new Color(12, 233, 12));
        notice.setText("Đăng nhập thành công");
        notice.setPreferredSize(new Dimension(550, 370));
        notice.setIcon("/Image/success.png");
        notice.add(notice.getTextLabel(),"y 65% ,center");
        notice.add(notice.getError(),"y 40%, center");
        notice.setNoticeAction(() -> {
            f.setVisible(true);
            Window win = SwingUtilities.getWindowAncestor(This);
            win.setVisible(false);
            f.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    win.setVisible(true);
                }
            });
        });

        notice.showAlert();
    }


}
