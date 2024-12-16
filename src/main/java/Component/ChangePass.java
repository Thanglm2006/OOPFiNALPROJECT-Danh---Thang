package Component;

import Res.SQLQueries;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePass extends JPanel {

    private MyPassword NewPass, confirm,oldPass;
    private JLabel old,New,Con;

    public ChangePass(String role, int id, SQLQueries sql) {
        setPreferredSize(new Dimension(900,700));
        String pass = "";
        ResultSet res= sql.stInfor(id);
        try{
            while(res.next()){
                pass=res.getNString("PassWord");
            }
        } catch (SQLException e) {

        }
        old= new JLabel("Nhập mật khẩu cũ:");
        New= new JLabel("Nhập mật khâu mới:");
        Con= new JLabel("Nhập lại mật khẩu mới:");
        oldPass= new MyPassword();
        NewPass= new MyPassword();
        confirm = new MyPassword();
        old.setPreferredSize(new Dimension(200,50));
        New.setPreferredSize(new Dimension(200,50));
        Con.setPreferredSize(new Dimension(200,50));
        oldPass.setPreferredSize(new Dimension(200,50));
        NewPass.setPreferredSize(new Dimension(200,50));
        confirm.setPreferredSize(new Dimension(200,50));
        oldPass.setFont(new Font("Times New Roman",Font.PLAIN,48));
        NewPass.setFont(new Font("Times New Roman",Font.PLAIN,48));
        confirm.setFont(new Font("Times New Roman",Font.PLAIN,48));
        old.setFont(new Font("Times New Roman",Font.PLAIN,48));
        New.setFont(new Font("Times New Roman",Font.PLAIN,48));
        Con.setFont(new Font("Times New Roman",Font.PLAIN,48));
        MyButton change= new MyButton("Đổi Mật Khẩu");
        change.setFont(new Font("Times New Roman",Font.PLAIN,50));
        JLabel l= new JLabel();
        l.setFont(new Font("Times New Roman",Font.PLAIN,20));
        String finalPass = pass;
        change.addActionListener(e -> {
            if(role.equals("SV"))if(!oldPass.getText().equals(finalPass)){
                l.setText("Sai mật khẩu!"); l.setForeground(Color.RED);
            }else{
                if(NewPass.getText().length()<8){
                    l.setText("Độ dài tối thiểu cho mật khẩu là 8 kí tự"); l.setForeground(Color.RED);

                }else if(NewPass.getText().equals(finalPass)){
                    l.setText("Mật khẩu mới phải khác mật khâu cũ!"); l.setForeground(Color.RED);

                }else if(!NewPass.getText().equals(confirm.getText())){
                    l.setText("Vui lòng nhập mật khẩu giống nhau ở hai ô trên!"); l.setForeground(Color.RED);

                }else{
                    boolean check= sql.resetStudentPass(NewPass.getText(),id);
                    if(check){
                        l.setText("Đổi mật khẩu thành công!"); l.setForeground(Color.GREEN);
                        NewPass.setText("");
                        confirm.setText("");
                        oldPass.setText("");
                    }else{
                        l.setText("Đã có lỗi xảy ra!"); l.setForeground(Color.RED);

                    }
                }
            }
        });
        GroupLayout layout= new GroupLayout(this);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(old ,GroupLayout.DEFAULT_SIZE,200,Short.MAX_VALUE)
                                        .addComponent(oldPass ,GroupLayout.DEFAULT_SIZE,200,Short.MAX_VALUE)
                                        .addComponent(New ,GroupLayout.DEFAULT_SIZE,200,Short.MAX_VALUE)
                                        .addComponent(NewPass ,GroupLayout.DEFAULT_SIZE,200,Short.MAX_VALUE)
                                        .addComponent(Con ,GroupLayout.DEFAULT_SIZE,200,Short.MAX_VALUE)
                                        .addComponent(confirm ,GroupLayout.DEFAULT_SIZE,200,Short.MAX_VALUE)
                                        .addComponent(l)
                                        .addComponent(change)
                                )

                        )

        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(50)
                        .addGroup(layout.createSequentialGroup()

                                .addComponent(old)
                                .addGap(30)
                                .addComponent(oldPass)
                                .addGap(30)
                                .addComponent(New)
                                .addGap(30)
                                .addComponent(NewPass)
                                .addGap(30)

                                .addComponent(Con)
                                .addGap(30)
                                .addComponent(confirm)
                                .addGap(50)
                                .addComponent(l)
                                .addGap(10)
                                .addComponent(change)
                        )

        );
        setLayout(layout);
    }
}
