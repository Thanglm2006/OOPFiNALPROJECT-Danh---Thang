
package GUI;

import Res.AutomticMail;
import Res.SQLQueries;
import Swing.MyButton;
import Swing.MyPassword;
import Swing.MyTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends javax.swing.JPanel {
    private JButton cmdRegister;
    private SQLQueries sql;
    private JLabel forget;
    private JLabel jLabel1,jLabel2,jLabel3,m1;
    private MyButton myButton1;
    private MyPassword txtPass;
    private MyTextField txtUser;
    private AutomticMail mail;
    private ActionListener ac;
    public Login() {
        initComponents();
    }

    public void login() {
        txtUser.grabFocus();
    }

    public void addEventRegister(ActionListener event) {
        cmdRegister.addActionListener(event);
    }
    public void addEventLogin(ActionListener event){ac=event;}
    public String getAcc(){
        return txtUser.getText();
    }
    public String getPass(){
        return txtPass.getText();
    }
    private void initComponents() {
        sql= new SQLQueries();
        mail= new AutomticMail();
        txtUser = new MyTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPass = new MyPassword();
        jLabel3 = new javax.swing.JLabel();
        myButton1 = new MyButton();
        cmdRegister = new javax.swing.JButton();
        m1= new JLabel();
        m1.setForeground(Color.RED);
        forget = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Tên đăng nhập");

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(69, 68, 68));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Đăng nhập");

        jLabel3.setText("Mật khẩu");

        myButton1.setBackground(new java.awt.Color(125, 229, 251));
        myButton1.setForeground(new java.awt.Color(40, 40, 40));
        myButton1.setText("Đăng nhập");
        //
        myButton1.addActionListener(e -> {
            if(getAcc().matches("SV\\S+")){
                String pass=sql.LoginStudent(getAcc());
                if(getAcc().isEmpty()){
                    m1.setText("Nhập tài khoản hoặc email!");
                    m1.setForeground(Color.RED);
                }
                else
                if(pass==null||pass.equals("no acc")){
                    m1.setText("Tài khoản không tồn tại!");
                }else if(!pass.equals(getPass())){
                    m1.setText("Sai mật khẩu!");
                }else{
                    m1.setText("");
                    myButton1.addActionListener(ac);
                    myButton1.doClick();
                    //
                }
            }else{
                String pass=sql.LoginTeacher(getAcc());
                if(getAcc().isEmpty()){
                    m1.setText("Nhập tài khoản hoặc email!");
                    m1.setForeground(Color.RED);
                }
                else
                if(pass==null||pass.equals("no acc")){
                    m1.setText("Tài khoản không tồn tại!");
                }else if(!pass.equals(getPass())){
                    m1.setText("Sai mật khẩu!");
                }else{
                    m1.setText("");
                    myButton1.addActionListener(ac);
                    myButton1.doClick();
                    //
                }
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    if(getAcc().isEmpty()){
                        m1.setText("Nhập tài khoản hoặc email!");
                        m1.setForeground(Color.RED);
                    }
                    else if(getAcc().matches("SV\\S+")){
                        String pass=sql.LoginStudent(getAcc());
                        if(pass==null||pass.equals("no acc")){
                            m1.setText("Tài khoản không tồn tại!");
                        }else if(!pass.equals(getPass())){
                            m1.setText("Sai mật khẩu!");
                        }else{
                            m1.setText("");
                            myButton1.addActionListener(ac);
                            myButton1.doClick();
                            //
                        }
                    }else{
                        String pass=sql.LoginTeacher(getAcc());
                        if(getAcc().isEmpty()){
                            m1.setText("Nhập tài khoản hoặc email!");
                            m1.setForeground(Color.RED);
                        }
                        else
                        if(pass==null||pass.equals("no acc")){
                            m1.setText("Tài khoản không tồn tại!");
                        }else if(!pass.equals(getPass())){
                            m1.setText("Sai mật khẩu!");
                        }else{
                            m1.setText("");
                            myButton1.addActionListener(ac);
                            myButton1.doClick();
                            //
                        }
                    }
                }
            }
        });
        txtPass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    if(getAcc().matches("SV\\S+")){
                        String pass=sql.LoginStudent(getAcc());
                        if(getAcc().isEmpty()){
                            m1.setText("Nhập tài khoản hoặc email!");
                            m1.setForeground(Color.RED);
                        }
                        else if(pass==null||pass.equals("no acc")){
                            m1.setText("Tài khoản không tồn tại!");
                        }else if(!pass.equals(getPass())){
                            m1.setText("Sai mật khẩu!");
                        }else{
                            m1.setText("");
                            myButton1.addActionListener(ac);
                            myButton1.doClick();
                            //
                        }
                    }else{
                        String pass=sql.LoginTeacher(getAcc());
                        if(getAcc().isEmpty()){
                            m1.setText("Nhập tài khoản hoặc email!");
                            m1.setForeground(Color.RED);
                        }
                        else
                        if(pass==null||pass.equals("no acc")){
                            m1.setText("Tài khoản không tồn tại!");
                        }else if(!pass.equals(getPass())){
                            m1.setText("Sai mật khẩu!");
                        }else{
                            m1.setText("");
                            myButton1.addActionListener(ac);
                            myButton1.doClick();
                            //
                        }
                    }
                }
            }
        });
        myButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cmdRegister.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        cmdRegister.setForeground(new java.awt.Color(30, 122, 236));
        cmdRegister.setText("Tạo tài khoản mới");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        forget.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        forget.setForeground(new java.awt.Color(30, 122, 236));
        forget.setText("Quên mật khẩu?");
        forget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forget.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(getAcc().matches("SV\\S+")){
                    String[] res =sql.getStudentMail(getAcc());
                    String email= res[0];
                    String pass=res[1];
                    if(getAcc().isEmpty()){
                        m1.setText("Nhập tài khoản hoặc email trước!");
                        m1.setForeground(Color.RED);

                    }
                    else if(email==null||email.equals("no email")){
                        m1.setText("Tài khoản không tồn tại!");
                        m1.setForeground(Color.RED);
                    }else{
                        m1.setText("");
                        m1.setForeground(Color.GREEN);

                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        SwingWorker<Void, Void> w= new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                mail.sendmail(email,"Mật khẩu cho English Learing App",pass);
                                return null;
                            }

                            @Override
                            protected void done() {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            }
                        } ;
                        w.execute();
                        m1.setText("Mật khẩu đã được gửi về gmail của bạn!");
                        m1.setForeground(Color.GREEN);
                        //
                    }
                }else{
                    String[] res =sql.getTeacherMail(getAcc());
                    String email= res[0];
                    String pass=res[1];
                    if(getAcc().isEmpty()){
                        m1.setText("Nhập tài khoản hoặc email trước!");
                        m1.setForeground(Color.RED);
                    }
                    else if(email==null||email.equals("no email")){
                        m1.setText("Tài khoản không tồn tại!");
                        m1.setForeground(Color.RED);
                    }else{
                         setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        SwingWorker<Void, Void> w= new SwingWorker<Void, Void>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                mail.sendmail(email,"Mật khẩu cho English Learing App",pass);
                                return null;
                            }

                            @Override
                            protected void done() {
                                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            }
                        } ;
                        w.execute();
                        m1.setText("Mật khẩu đã được gửi về gmail của bạn!");
                        m1.setForeground(Color.GREEN);

                        //
                    }
                }
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(50, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3)
                                        .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                        .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(m1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(myButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmdRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(50, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(forget, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(57, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(m1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(forget, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(cmdRegister)
                                .addGap(30, 30, 30))
        );
    }


}
