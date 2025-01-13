package GUI;

import Component.*;
import Component.PanelPrototype.*;
import Component.dialog.Notice;
import Component.Button.*;
import Component.form.Form1;
import Component.form.Form2;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnglishLearningApp extends javax.swing.JFrame {
    private PanelBorder panelBorder1;
    private PanelGradiente panelGradiente1;
    private Notice notice;
    private PanelSlide slide;
    private EnglishLearningApp englishLearningApp;

    public EnglishLearningApp() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EnglishLearningApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        Login login = new Login();
        Register register = new Register();
        ForgetPassWord3 forgetPassWord3 = new ForgetPassWord3(login.getSql());
        ForgetPassWord2 forgetPassWord2 = new ForgetPassWord2(login.getSql(),forgetPassWord3);
        ForgetPassWord1 forgetPassWord1 = new ForgetPassWord1(login.getSql(),forgetPassWord2);


        slide.setAnimate(30);
        slide.init(login, register, forgetPassWord1, forgetPassWord2, forgetPassWord3);
        login.addEventRegister(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(1);
                register.register();
                notice = new Notice(register,true);
                notice.setPreferredSize(new Dimension(550,450));

                notice.setError("Notice");
                notice.setIcon("/Image/note.png");
                notice.setText("<html>"
                        + "<div style='text-align:center;width:400px;'>"
                        + "Bạn vui lòng tạo tài khoản theo yêu cầu của chúng tôi!<br>"
                        + "Nếu là học sinh thì SV+ tài khoản <br>"
                        + "Nếu là giáo viên thì GV+ tài khoản <br>"
                        + "Ví dụ SVabc hoặc GVabc"
                        + "</div>"
                        + "</html>");
                notice.add(notice.getError(),"y 33%, center");
                notice.add(notice.getTextLabel(),"y 50%, center");

                notice.showAlert();

            }
        });
        register.addEventBackLogin(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(0);
                login.login();
            }
        });

        login.addEventForget(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(2);
                forgetPassWord1.forget1();
            }
        });
        login.addEventLogin(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        forgetPassWord1.addEventBackLogin(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(0);
                login.login();
            }
        });
        forgetPassWord1.addEventToForget2(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(3);
                forgetPassWord2.forget2();
            }
        });
        forgetPassWord2.addEventBackLogin(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(0);
                login.login();
            }
        });
        forgetPassWord2.addEventBackToForget1(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(2);
                forgetPassWord1.forget1();
            }
        });
        forgetPassWord2.addEventToForget3(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(4);
                forgetPassWord3.forget3();
            }
        });
        forgetPassWord3.addEventBackLogin(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(0);
                login.login();
            }
        });
        forgetPassWord3.addEventBackToForget2(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                slide.show(3);
                forgetPassWord2.forget2();
            }
        });
    }


    private void initComponents() {

        panelGradiente1 = new PanelGradiente();
        panelBorder1 = new PanelBorder();
        slide = new PanelSlide();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        setUndecorated(true);

        panelGradiente1.setColorPrimario(new java.awt.Color(146, 233, 251));
          panelGradiente1.setColorSecundario(new java.awt.Color(12, 137, 163));
        panelGradiente1.setColorPrimario(new java.awt.Color(206,242,254,255));
        panelBorder1.setBackground(new java.awt.Color(206,242,254,255));

        slide.setBackground(new java.awt.Color(206,242,254,255));

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));
//
        slide.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout slideLayout = new javax.swing.GroupLayout(slide);
        slide.setLayout(slideLayout);
        slideLayout.setHorizontalGroup(
                slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        slideLayout.setVerticalGroup(
                slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 650, Short.MAX_VALUE)
        );

        panelBorder1.setLayer(slide, javax.swing.JLayeredPane.DEFAULT_LAYER);
        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
                panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelBorder1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(slide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
                panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelBorder1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(slide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        panelGradiente1.setLayer(panelBorder1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout panelGradiente1Layout = new javax.swing.GroupLayout(panelGradiente1);
        panelGradiente1.setLayout(panelGradiente1Layout);
        panelGradiente1Layout.setHorizontalGroup(
                panelGradiente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGradiente1Layout.createSequentialGroup()
                                .addContainerGap(100, Short.MAX_VALUE)
                                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(100, Short.MAX_VALUE))
        );
        panelGradiente1Layout.setVerticalGroup(
                panelGradiente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGradiente1Layout.createSequentialGroup()
                                .addContainerGap(100, Short.MAX_VALUE)
                                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(100, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelGradiente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelGradiente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EnglishLearningApp().setVisible(true);

            }
        });
    }




}
