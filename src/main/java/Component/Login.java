package Component;

import GUI.FrameForStudent;
import Res.AutomticMail;
import Res.SQLQueries;
import net.miginfocom.swing.MigLayout;
import Component.Button;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JPanel {
    private Button register;
    private SQLQueries sql;
    private JLabel forget;
    private JLabel j1, jLabel3, m1;
    private Button login;
    private PasswordField password;
    private TextField username;
    int id;
    String user;
    private AutomticMail mail;
    private ActionListener ac;
    private JPanel This;

    public Login() {
        initComponents();
    }

    public void login() {
        username.grabFocus();
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

    private void initComponents() {
        This = this;

        sql = new SQLQueries();
        mail = new AutomticMail();
        username = new TextField();
        username.setLabelText("Tên đăng nhập");

        j1 = new JLabel();
        password = new PasswordField();
        password.setLabelText("Mật khẩu");
        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    if (getAcc().matches("SV\\S+")||getAcc().matches("sv\\S+")) {
                        ResultSet res = null;
                        res = sql.LoginStudent(getAcc());
                        String pass = null;
                        int id = 0;
                        String Name = null;
                        if (res != null)
                            try {
                                while (res.next()) {
                                    pass = res.getNString("PassWord");
                                    id = res.getInt("StudentID");
                                    Name = res.getNString("StudentName");
                                }
                            } catch (SQLException ex) {
                            }
                        if (getAcc().isEmpty()) {
                            m1.setText("Nhập tài khoản hoặc email!");
                            m1.setForeground(Color.RED);
                        } else if (pass == null) {
                            m1.setText("Tài khoản không tồn tại!");
                        } else if (!pass.equals(getPass())) {
                            m1.setText("Sai mật khẩu!");
                        } else {
                            m1.setText("");
                            FrameForStudent f = new FrameForStudent(id, Name);
                            f.setVisible(true);
                            Window win = SwingUtilities.getWindowAncestor(This);
                            win.setVisible(false);
                            f.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    win.setVisible(true);
                                }
                            });

                            //
                        }
                    } else {
                        ResultSet res = null;
                        res = sql.LoginTeacher(getAcc());
                        String pass = null;
                        int id = 0;
                        if (res != null)
                            try {
                                while (res.next()) {
                                    pass = res.getNString("PassWord");
                                    id = res.getInt("TeacherID");
                                }
                            } catch (SQLException ex) {
                            }
                        if (getAcc().isEmpty()) {
                            m1.setText("Nhập tài khoản hoặc email!");
                            m1.setForeground(Color.RED);
                        } else if (pass == null || pass.equals("no acc")) {
                            m1.setText("Tài khoản không tồn tại!");
                        } else if (!pass.equals(getPass())) {
                            m1.setText("Sai mật khẩu!");
                        } else {
                            m1.setText("");

                            //
                        }
                    }
                }
            }
        });
        jLabel3 = new JLabel();
        login = new Button();
        register = new Button();
        m1 = new JLabel();
        m1.setForeground(Color.RED);
        m1.setFont(new Font("sansserif",0,13));
        forget = new JLabel();

        setBackground(new Color(255, 255, 255));

        j1.setFont(new Font("sansserif", 1, 36)); // NOI18N
        j1.setForeground(new Color(69, 68, 68));
        j1.setHorizontalAlignment(SwingConstants.CENTER);
        j1.setText("Đăng nhập");

        login.setBackground(new Color(25, 182, 247));
        login.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        login.setForeground(new Color(255, 255, 255));
        login.setText("Login");
        login.setFont(new Font("SansSerif", 1, 15));

        login.addActionListener(e -> {
            if (getAcc().matches("SV\\S+")||getAcc().matches("sv\\S+")) {
                ResultSet res = null;
                res = sql.LoginStudent(getAcc());
                String pass = null;
                int id = 0;
                String Name = null;
                if (res != null)
                    try {
                        while (res.next()) {
                            pass = res.getNString("PassWord");
                            id = res.getInt("StudentID");
                            Name = res.getNString("StudentName");
                        }
                    } catch (SQLException ex) {
                    }
                if (getAcc().isEmpty()) {
                    m1.setText("Nhập tài khoản hoặc email!");
                    m1.setForeground(Color.RED);
                } else if (pass == null) {
                    m1.setText("Tài khoản không tồn tại!");
                } else if (!pass.equals(getPass())) {
                    m1.setText("Sai mật khẩu!");
                } else {
                    m1.setText("");
                    FrameForStudent f = new FrameForStudent(id, Name);
                    f.setVisible(true);
                    Window win = SwingUtilities.getWindowAncestor(this);
                    win.setVisible(false);
                    f.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            win.setVisible(true);
                        }
                    });

                    //
                }
            } else {
                ResultSet res = null;
                res = sql.LoginTeacher(getAcc());
                String pass = null;
                int id = 0;
                if (res != null)
                    try {
                        while (res.next()) {
                            pass = res.getNString("PassWord");
                            id = res.getInt("TeacherID");
                        }
                    } catch (SQLException ex) {
                    }
                if (getAcc().isEmpty()) {
                    m1.setText("Nhập tài khoản hoặc email!");
                    m1.setForeground(Color.RED);
                } else if (pass == null || pass.equals("no acc")) {
                    m1.setText("Tài khoản không tồn tại!");
                } else if (!pass.equals(getPass())) {
                    m1.setText("Sai mật khẩu!");
                } else {
                    m1.setText("");

                    //
                }
            }
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
        forget.setText("Bạn đã quên mật khẩu?");
        forget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forget.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getAcc().matches("SV\\S+")) {
                    String[] res = sql.getStudentMail(getAcc());
                    String email = res[0];
                    String pass = res[1];
                    if (getAcc().isEmpty()) {
                        m1.setText("Nhập tài khoản hoặc email trước!");
                        m1.setForeground(Color.RED);
                    } else if (email == null || email.equals("no email")) {
                        m1.setText("Tài khoản không tồn tại!");
                        m1.setForeground(Color.RED);
                    } else {
                        m1.setText("");
                        m1.setForeground(Color.GREEN);

                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        SwingWorker<Void, Void> w = new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                mail.sendmail(email, "Mật khẩu cho English Learning App", pass);
                                return null;
                            }

                            @Override
                            protected void done() {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            }
                        };
                        w.execute();
                        m1.setText("Mật khẩu đã được gửi về gmail của bạn!");
                        m1.setForeground(Color.GREEN);
                    }
                } else {
                    String[] res = sql.getTeacherMail(getAcc());
                    String email = res[0];
                    String pass = res[1];
                    if (getAcc().isEmpty()) {
                        m1.setText("Nhập tài khoản hoặc email trước!");
                        m1.setForeground(Color.RED);
                    } else if (email == null || email.equals("no email")) {
                        m1.setText("Tài khoản không tồn tại!");
                        m1.setForeground(Color.RED);
                    } else {
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        SwingWorker<Void, Void> w = new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                mail.sendmail(email, "Mật khẩu cho English Learning App", pass);
                                return null;
                            }

                            @Override
                            protected void done() {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            }
                        };
                        w.execute();
                        m1.setText("Mật khẩu đã được gửi về gmail của bạn!");
                        m1.setForeground(Color.GREEN);
                    }
                }
            }
        });


        setLayout(new MigLayout("al center center, wrap"));

        add(j1, "wrap, width 75%, y 12%");
        add(username, "wrap, width 75%");
        add(password, "wrap, width 75%");
        add(m1, "wrap");
        add(login, "wrap, width 75%, y 57%");
        add(forget, "wrap, center, width 75%,y 65%");
        add(register, "wrap, width 75%, y 85%");

    }
}
