
package GUI;

import Res.SQLQueries;
import Swing.MyButton;
import Swing.MyPassword;
import Swing.MyTextField;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


public class Register extends javax.swing.JPanel {
    private SQLQueries sql;
    private JButton cmdBackLogin;
    private JLabel jLabel1,jLabel2,jLabel3,jLabel4,jLabel5,jLabel6,jLabel7,jLabel8,jLabel9,m1,m2,m3,m4,m5,m6,m7;
    private MyButton myButton_Register;
    private MyPassword txtPass,txtPass1;
    private MyTextField txtUser;
    private MyTextField txtEmail,txtFullName;
    private JFormattedTextField Birth;
    private DatePicker birth;
    private JRadioButton jRadioButton_Student,jRadioButton_Teacher;
    private ButtonGroup buttonGroup;
    private String birthdate;
    public Register() {
        initComponents();
    }

    public void register() {
        txtUser.grabFocus();
    }
    private ActionListener ac;
    public void addEventBackLogin(ActionListener event) {
        cmdBackLogin.addActionListener(event);
        ac=event;
    }

    public String getUser(){
        return txtUser.getText();
    }
    public String getPass1(){
        return txtPass1.getText();
    }
    public String getEmail(){
        return txtEmail.getText();
    }
    public String getFullName(){
        return txtFullName.getText();
    }
    public String getBirth() {
        return birthdate;
    }
    private void initComponents() {
        sql= new SQLQueries();
        birth= new DatePicker();
        txtUser = new MyTextField();
        jLabel1 = new javax.swing.JLabel();
        m1= new JLabel(); m1.setForeground(Color.RED);
        jLabel2 = new javax.swing.JLabel();
        txtPass = new MyPassword();
        jLabel3 = new javax.swing.JLabel();
        myButton_Register = new MyButton();
        cmdBackLogin = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtPass1 = new MyPassword();
        txtEmail = new MyTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8= new JLabel();
        jLabel9 = new JLabel();
        txtFullName= new MyTextField();
        Birth= new JFormattedTextField();
        jRadioButton_Student = new JRadioButton();
        jRadioButton_Teacher = new JRadioButton();
        jLabel7 = new JLabel();
        m2= new JLabel();
        m2.setForeground(Color.RED);
        m3= new JLabel();
        m3.setForeground(Color.RED);
        m4= new JLabel();
        m4.setForeground(Color.RED);
        m5= new JLabel();
        m5.setForeground(Color.RED);
        m7= new JLabel();
        m7.setForeground(Color.GREEN);
        m6= new JLabel("( theo định dạng dd-mm-yyyy)");

        birth.setEditor(Birth);

        birth.addDateSelectionListener(new DateSelectionListener() {

            @Override
            public void dateSelected(DateEvent dateEvent) {
                LocalDate dates =birth.getSelectedDate();
                DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                if(dates!=null) {

                    birthdate = df.format(dates);
                }
            }
        });

        birth.setDateSelectionAble(localDate ->
                !localDate.isAfter(LocalDate.now()) && !localDate.isBefore(LocalDate.of(1900, 1, 1))
        );
        birth.setColor(Color.CYAN);
        birth.setColor(Color.CYAN);

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Tên đăng nhập( không dấu)");
        jLabel2.setFont(new java.awt.Font("sansserif", 1, 36));
        jLabel2.setForeground(new java.awt.Color(69, 68, 68));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Đăng kí");

        jLabel3.setText("Mật khẩu");

        myButton_Register.setBackground(new java.awt.Color(125, 229, 251));
        myButton_Register.setForeground(new java.awt.Color(40, 40, 40));
        myButton_Register.setText("Đăng kí");
        myButton_Register.addActionListener(e -> {
            if(getUser().isEmpty()){
                m1.setText("Tên đăng nhập không được để trống");
            }
            else if((jRadioButton_Teacher.isSelected()&&!getUser().matches("GV\\S+"))||(jRadioButton_Student.isSelected()&&!getUser().matches("SV\\S+"))){
                m1.setText("vui lòng đặt tên đăng nhập theo đúng định dạng");
            }else if(!jRadioButton_Student.isSelected()&&!jRadioButton_Teacher.isSelected()){
                m2.setText("vui lòng chọn loại tài khoản");
            }else if(txtPass.getText().isEmpty()){
                m3.setText("Mật khẩu không được để trống!");
            }else if(txtPass.getText().length()<8){
                m3.setText("Mật khẩu phải trên 8 kí tự!");
            }
            else if(!txtPass.getText().equals(txtPass1.getText())){
                jLabel6.setForeground(Color.RED);
                jLabel6.setText("Hãy nhập đúng mật khẩu đã nhập ở trên");
            }else if(txtEmail.getText().isEmpty()){
                m4.setText("vui lòng nhập email!");
            }else if(!txtEmail.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
                m4.setText("nhập email theo định dạng example@domain.com");
            }else if(txtFullName.getText().isEmpty()){
                m5.setText("Vui lòng nhập họ và tên!");
            }else if(birthdate==null){
                m6.setForeground(Color.RED);
                m6.setText("Vui lòng nhập ngày sinh!");
            }
            else{
                m1.setText("");
                m2.setText("");
                jLabel6.setForeground(Color.BLACK);
                jLabel6.setText("( nhập lại mật khẩu)");
                m3.setText("");
                m4.setText("");
                m5.setText("");
                m6.setText("( theo định dạng dd-mm-yyyy)");
                m6.setForeground(Color.BLACK);
                if(jRadioButton_Teacher.isSelected()){
                    boolean check= sql.insertTeacher(txtUser.getText(),txtPass.getText(),txtFullName.getText(),txtEmail.getText(),birthdate);
                    if(!check) {
                        m1.setText("tên đăng nhập đã tồn tại!");
                        m1.setForeground(Color.red);
                    }else{
                        m1.setText("");
                        txtEmail.setText("");
                        txtFullName.setText("");
                        txtUser.setText("");
                        txtPass.setText("");
                        txtPass1.setText("");
                        Birth.setText(null);
                        myButton_Register.addActionListener(ac);
                        myButton_Register.doClick();
                    }

                }else{
                    boolean check= sql.insertStudent(txtUser.getText(),txtPass.getText(),txtFullName.getText(),txtEmail.getText(),birthdate);
                    if(!check) {
                        m1.setText("tên đăng nhập đã tồn tại!");
                        m1.setForeground(Color.red);
                    }else{

                        m1.setText("");
                        m1.setText("");
                        txtEmail.setText("");
                        txtFullName.setText("");
                        txtUser.setText("");
                        txtPass.setText("");
                        txtPass1.setText("");
                        Birth.setText(null);
                        myButton_Register.addActionListener(ac);
                        myButton_Register.doClick();
                    }
                }
            }
        });
        cmdBackLogin.setFont(new java.awt.Font("sansserif", 1, 12));
        cmdBackLogin.setForeground(new java.awt.Color(30, 122, 236));
        cmdBackLogin.setText("Bạn đã có tài khoản ư?");
        cmdBackLogin.setContentAreaFilled(false);
        cmdBackLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel4.setText("Mật khẩu");
        jLabel5.setText("Email");
        jLabel8.setText("Họ và tên");
        jLabel9.setText("Ngày sinh");
        jRadioButton_Student.setText("Sinh viên/ Học sinh");
        jLabel7.setText("Loại tài khoản");
        jRadioButton_Teacher.setText("Giáo viên");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButton_Student);
        buttonGroup.add(jRadioButton_Teacher);

        txtUser.setText("nếu là sinh viên: đặt là SV...; nếu là giáo viên đặt là GV...");
        txtUser.setForeground(Color.gray);
        txtUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(txtUser.getText().equalsIgnoreCase("sinh viên: đặt là SV...; giáo viên đặt là GV...")||txtUser.getText().length()>30){
                    txtUser.setText("");
                    txtUser.setForeground(Color.BLACK);
                }
                else if(txtUser.getText().equalsIgnoreCase("")){
                    txtUser.setText("nếu là sinh viên: đặt là SV...; nếu là giáo viên đặt là GV...");
                    txtUser.setForeground(Color.gray);
                }
            }
        });
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10));
        jLabel6.setText("(nhập lại mật khẩu)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(43, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(m2))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButton_Student)
                                                .addGap(18, 18, 18)
                                                .addComponent(jRadioButton_Teacher))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(m4))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel8)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(m5))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel9)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(m6))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel4)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(m3))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel1)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(m1))
                                                .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                                .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(myButton_Register, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cmdBackLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)

                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel3)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel6))
                                                .addComponent(txtPass1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txtFullName,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
                                                .addComponent(Birth,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE)
                                        ))
//

                                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(m1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(m3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))

                                .addComponent(txtPass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(m4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                //
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(m5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(m6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Birth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                //
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(m2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioButton_Student)
                                        .addComponent(jRadioButton_Teacher))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                .addComponent(myButton_Register, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmdBackLogin)
                                .addGap(19, 19, 19))
        );
    }




}
