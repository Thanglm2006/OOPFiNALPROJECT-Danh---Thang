package Component.PanelPrototype;

import Component.Button.Button;
import Component.ImageAvatar;
import Component.TextFieldAndSoOn.TextField;
import Res.AutomticMail;
import Res.SQLQueries;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ForgetPassWord1 extends JPanel {
    private JLabel l1,l2,l3;
    private ImageAvatar image;
    private TextField email,username;
    private Button next;
    private Button iconBackLogin,backLogin;
    private ActionListener ac,acc;
    private AutomticMail mail;
    private SQLQueries sql;
    private String role,Email,name;
    private int id;
    private int code;
    private ForgetPassWord2 forgetPassWord2;
    public int getId(){
        return id;
    }
    public String getEmail(){
        return Email;
    }
    public String getRole(){
        return role;
    }
    public ForgetPassWord1(SQLQueries sql, ForgetPassWord2 forgetPassWord2){
        this.forgetPassWord2 = forgetPassWord2;
        this.sql = sql;
        mail=new AutomticMail(sql);
        initComponent();
    }
    public void forget1() {
        email.grabFocus();
    }
    public void addEventToForget2(ActionListener event) {
        acc = event;
    }
    public void addEventBackLogin(ActionListener event) {
        iconBackLogin.addActionListener(event);
        backLogin.addActionListener(event);
        ac = event;
    }

    public void initComponent(){
        setBackground(new Color(255,255,255));
        setLayout(new MigLayout("al center center, wrap"));

        iconBackLogin = new Button();
        iconBackLogin.setIcon(new ImageIcon(getClass().getResource("/Image/quaylai.png")));
        iconBackLogin.setForeground(Color.WHITE);
        iconBackLogin.setFont(new Font("sansserif", Font.BOLD, 15));
        add(iconBackLogin,"w 2%,x 0%, y 0%");
        l1 = new JLabel();
        l1.setText("Quên mật khẩu");
        l1.setFont(new Font("sansserif", 1, 36));
        l1.setForeground(new Color(69, 68, 68));
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        add(l1,"y 5%, width 75%");

        image = new ImageAvatar();
        image.setIcon(new ImageIcon(getClass().getResource("/Image/user_.png")));
        add(image,"y 5%, center, w 40%, h 40%");

        l2 = new JLabel();
        l2.setFont(new Font("SansSerif", 1, 18));
        l2.setForeground(new Color(30, 122, 236));
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        l2.setText("User");
        add(l2,"w 10%, center, y 38%");

        email = new TextField();
        email.setLabelText("Nhập email của bạn");
        add(email,"w 75%, y 45%");
        username = new TextField();
        username.setLabelText("Nhập tài khoản của bạn");
        add(username,"w 75%, y 54%");
        
        next = new Button();
        next.setText("Tiếp theo");
        AtomicBoolean Check = new AtomicBoolean(false);

        ActionListener act=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(username.getText().isEmpty()||email.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Vui lòng nhập đầy đủ thông tin");
                }else{
                    if(!Check.get())
                        Check.set(handle());
                    if(Check.get()) {
                        acc.actionPerformed(e);
                    }
                }
            }
        };
        next.addActionListener(act);
        email.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    act.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,""));
                }
            }
        });
        username.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    act.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,""));
                }
            }
        });
        next.setBackground(new Color(25, 182, 247));
        next.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        next.setForeground(Color.WHITE);
        next.setFont(new Font("sansserif", Font.BOLD, 15));
        add(next,"w 75%, y 68%");





        backLogin = new Button();
        backLogin.setFont(new Font("sansserif", Font.BOLD, 15));
        backLogin.setForeground(new Color(30, 122, 236));
        backLogin.setText("Quay lại trang đăng nhập");
        backLogin.setContentAreaFilled(false);
        backLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(backLogin,"w 75%, y 90%");
    }
    private void createRandomCode(){
        Random random = new Random();
        code = random.nextInt(999999)+1;
    }
    public boolean handle(){
        createRandomCode();
        String user = username.getText();
        String maill = email.getText();
        if(user.matches("SV\\S+")||user.matches("sv\\S+")){
            role = "SV";
            id=sql.getSTID(user);
            if(id==0){
                JOptionPane.showMessageDialog(null,"Tài khoản không tồn tại");
                return false;
            }
            ResultSet res = sql.stInfor(id);

            try{
                while(res.next()){
                    if(res.getString("Email").equals(maill)){
                        Email = maill;
                        name=res.getString("StudentName");
                    }else{
                        JOptionPane.showMessageDialog(null,"Email không đúng");
                        return false;
                    }
                }
            }catch (SQLException e) {
                    e.printStackTrace();
            }
        }else if(user.matches("GV\\S+")||user.matches("gv\\S+")){
            role = "GV";
            id=sql.getTID(user);
            if(id==0){
                JOptionPane.showMessageDialog(null,"Tài khoản không tồn tại");
                return false;
            }
            ResultSet res = sql.TInfor(id);
            try{
                while(res.next()){
                    if(res.getString("Email").equals(maill)){
                        Email = maill;
                        name=res.getString("StudentName");
                    }else{
                        JOptionPane.showMessageDialog(null,"Email không đúng");
                        return false;
                    }
                }

            }catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null,"Tài khoản hoặc email không hợp lệ");
            return false;
        }
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        SwingWorker<Void,Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                mail.sendmail(Email, "Mã xác nhận", "Mã xác nhận của bạn là: " + code);
                return null;
            }

            @Override
            protected void done() {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        };
        worker.execute();
        forgetPassWord2.setEmail(Email);
        forgetPassWord2.setId(id);
        forgetPassWord2.setRole(role);
        forgetPassWord2.setATM(mail);
        forgetPassWord2.setCode(code);
        forgetPassWord2.setName(name);
        username.setText("");
        email.setText("");
        return true;
    }

}
