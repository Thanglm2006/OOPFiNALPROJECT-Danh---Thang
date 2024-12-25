package Component.PanelPrototype;

import Res.AutomticMail;
import Res.SQLQueries;
import net.miginfocom.swing.MigLayout;
import Component.Button.Button;
import Component.ImageAvatar;
import Component.TextFieldAndSoOn.TextField;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;

public class ForgetPassWord2 extends JPanel {
    private JLabel l1,l2,l3;
    private Button backtoForget1, backLogin;
    private Button confirm, load;
    private TextField code;
    private ActionListener ac,acc,ac3;
    private ImageAvatar image;
    private AutomticMail mail;
    private SQLQueries sql;
    private int Code;
    private String role,Email,name;
    private int id;
    private ForgetPassWord3 forgetPassWord3;

    public void setCode(int code) {
        Code = code;
    }

    public int getId(){
        return id;
    }
    public String getEmail(){
        return Email;
    }
    public String getRole(){
        return role;
    }
    public void setATM(AutomticMail mail){
        this.mail = mail;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ForgetPassWord2(SQLQueries sql, ForgetPassWord3 forgetPassWord3){
        this.forgetPassWord3 = forgetPassWord3;
        this.sql = sql;

        initComponents();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void forget2() {
        code.grabFocus();
    }

    public void addEventBackLogin(ActionListener event) {
        backLogin.addActionListener(event);
        ac = event;
    }

    public void addEventBackToForget1(ActionListener event) {
        backtoForget1.addActionListener(event);
        acc = event;
    }

    public void addEventToForget3(ActionListener event) {
        ac3 = event;
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
        l2.setText("Danh Hanma");
        add(l2,"w 10%, center, y 38.5%");

        code = new TextField();
        code.setLabelText("Nhập mã xác nhận");
        add(code,"w 75%, y 48%");

        confirm = new Button();
        confirm.setText("Xác nhận");
        confirm.setBackground(new Color(25, 182, 247));
        confirm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        confirm.setForeground(Color.WHITE);
        confirm.setFont(new Font("sansserif", Font.BOLD, 15));
        confirm.addActionListener(e->{
            try {
                if (Code == Integer.parseInt(code.getText())) {
                    forgetPassWord3.setId(id);
                    forgetPassWord3.setRole(role);
                    forgetPassWord3.getL2().setText(name);
                    code.setText("");
                    ac3.actionPerformed(e);
                } else {
                    JOptionPane.showMessageDialog(null, "Mã xác nhận không đúng");
                }
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Mã xác nhận không đúng");
            }
        });
        add(confirm,"w 75%, y 58%");
        code.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (evt.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
                    try {
                        if (Code == Integer.parseInt(code.getText())) {
                            forgetPassWord3.setId(id);
                            forgetPassWord3.setRole(role);
                            forgetPassWord3.getL2().setText(name);
                            ac3.actionPerformed(null);
                        } else {
                            JOptionPane.showMessageDialog(null, "Mã xác nhận không đúng");
                        }
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(null,"Mã xác nhận không đúng");
                    }
                }
            }
        });
        l3 = new JLabel("Bạn muốn gửi lại mã hãy click vào đây");
//        l3.setForeground(new Color());
        l3.setFont(new Font("SansSerif", 2, 13));
        l3.setHorizontalAlignment(SwingConstants.CENTER);
        add(l3,"w 75%, y 68%");

        load = new Button();
        load.setText("Gửi lại mã");
        load.setBackground(new Color(25, 182, 247));
        load.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        load.setForeground(Color.WHITE);
        load.addActionListener(e->{
            createCode();
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    mail.sendmail(Email, "Mã xác nhận", "Mã xác nhận của bạn là: " + Code);
                    return null;
                }

                @Override
                protected void done() {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            };
            worker.execute();
        });

        load.setFont(new Font("sansserif", Font.BOLD, 15));
        add(load,"w 75%, y 72%");

        backtoForget1 = new Button();
        backtoForget1.setIcon(new ImageIcon(getClass().getResource("/Image/quaylai.png")));
        backtoForget1.setForeground(Color.WHITE);
        backtoForget1.setFont(new Font("sansserif", Font.BOLD, 15));
        backtoForget1.addActionListener(acc);
        add(backtoForget1,"w 2%,x 0%, y 0%");


        backLogin = new Button();
        backLogin.setFont(new Font("sansserif", Font.BOLD, 15));
        backLogin.setForeground(new Color(30, 122, 236));
        backLogin.setText("Quay lại trang đăng nhập");
        backLogin.setContentAreaFilled(false);
        backLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLogin.addActionListener(ac);
        add(backLogin,"w 75%, y 90%");
    }
    public void createCode(){
        Code = (int) (Math.random()*999999)+1;
    }

}
