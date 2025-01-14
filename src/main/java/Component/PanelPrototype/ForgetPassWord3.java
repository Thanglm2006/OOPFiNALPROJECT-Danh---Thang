package Component.PanelPrototype;

import Component.TextFieldAndSoOn.PasswordField;
import Res.SQLQueries;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import net.miginfocom.swing.MigLayout;
import Component.Button.Button;
import Component.ImageAvatar;
import Component.TextFieldAndSoOn.TextField;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ForgetPassWord3 extends JPanel {
    private JLabel l1,l2;
    private Button backToForget2, backLogin;
    private Button setupNewPass;
    private TextField code;
    private PasswordField pass, pass1;
    private ActionListener ac,acc;
    private ImageAvatar image;
    private SQLQueries sql;
    private int id;
    private String role;
    public ForgetPassWord3(SQLQueries sql){
        this.sql = sql;
        initComponents();
    }

    public JLabel getL2() {
        return l2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void forget3() {
        pass.grabFocus();
    }

    public void addEventBackLogin(ActionListener event) {
        backLogin.addActionListener(event);
        ac = event;
    }

    public void addEventBackToForget2(ActionListener event) {
        backToForget2.addActionListener(event);
        acc = event;
    }

    public void initComponents(){
        setLayout(new MigLayout("al center center, wrap"));
        setBackground(new Color(255, 255, 255));
        l1 = new JLabel();
        l1.setText("Quên mật khẩu");
        l1.setFont(new Font("sansserif", 1, 36));
        l1.setForeground(new Color(69, 68, 68));
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        add(l1,"y 5%, width 75%");

        image = new ImageAvatar();
        image.setIcon(new ImageIcon(getClass().getResource("/Image/user.png")));
        add(image,"y 5%, center, w 40%, h 40%");

        l2 = new JLabel();
        l2.setFont(new Font("SansSerif", 1, 15));
        l2.setForeground(new Color(30, 122, 236));
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        add(l2,"w 10%, center, y 38.5%");


        pass = new PasswordField();
        pass.setLabelText("Nhập mật khẩu mới");
        pass.setShowAndHide(true);
        add(pass,"w 75%, y 45%");

        pass1 = new PasswordField();
        pass1.setLabelText("Nhập lại mật khẩu");
        pass1.setShowAndHide(true);
        add(pass1,"w 75%, y 54%");

        setupNewPass = new Button();
        setupNewPass.setText("Đặt mật khẩu mới");
        setupNewPass.setBackground(new Color(25, 182, 247));
        setupNewPass.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setupNewPass.setForeground(Color.WHITE);
        setupNewPass.addActionListener(e->{
            SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    if(pass.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Mật khẩu không được để trống");
                        return null;
                    }
                    else if (pass.getText().equals(pass1.getText())) {
                        String pass = pass1.getText();
                        Argon2 argon2 = Argon2Factory.create();
                        String hash = argon2.hash(2, 65536, 1, pass);
                        boolean check = false;
                        if (role.equals("SV"))
                            check=sql.resetStudentPass(hash, id);
                        else check=sql.resetTeacherPass(hash, id);
                        if (check)
                            JOptionPane.showMessageDialog(null, "Đặt mật khẩu mới thành công");
                        else JOptionPane.showMessageDialog(null, "Đặt mật khẩu mới thất bại, đã có lỗi xảy ra");
                        ac.actionPerformed(null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Mật khẩu không khớp");
                    }
                    return null;
                }
            };
            worker.execute();
        });
        pass1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            if (pass.getText().equals(pass1.getText())) {
                                String pass = pass1.getText();
                                Argon2 argon2 = Argon2Factory.create();
                                String hash = argon2.hash(2, 65536, 1, pass);
                                boolean check = false;
                                if (role.equals("SV"))
                                    check = sql.resetStudentPass(hash, id);
                                else check = sql.resetTeacherPass(hash, id);
                                if (check) {
                                    JOptionPane.showMessageDialog(null, "Đặt mật khẩu mới thành công");
                                    ac.actionPerformed(null);
                                }
                                else JOptionPane.showMessageDialog(null, "Đặt mật khẩu mới thất bại, đã có lỗi xảy ra");

                            } else {
                                JOptionPane.showMessageDialog(null, "Mật khẩu không khớp");
                            }
                            return null;
                        }
                    };
                    worker.execute();
                }
            }
        });
        setupNewPass.setFont(new Font("sansserif", Font.BOLD, 15));
        add(setupNewPass,"w 75%, y 67%");

        backToForget2 = new Button();
        backToForget2.setIcon(new ImageIcon(getClass().getResource("/Image/quaylai.png")));


        backToForget2.setForeground(Color.WHITE);
        backToForget2.setFont(new Font("sansserif", Font.BOLD, 15));
        backToForget2.addActionListener(acc);
        add(backToForget2,"w 2%,x 0%, y 0%");

        backLogin = new Button();
        backLogin.setFont(new Font("sansserif", Font.BOLD, 15));
        backLogin.setForeground(new Color(30, 122, 236));
        backLogin.setText("Quay lại trang đăng nhập");
        backLogin.setContentAreaFilled(false);
        backLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(backLogin,"w 75%, y 90%");
    }



}
