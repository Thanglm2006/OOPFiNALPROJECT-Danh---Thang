package Component.PanelPrototype;

import Component.Button.MyButton;
import Component.TextFieldAndSoOn.MyTextField;
import Res.AutomticMail;
import Res.SQLQueries;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class changeEmail extends JPanel {
    private MyTextField confirm,NewEmail;
    private JLabel con,New;
    private MyButton but,change;

    public changeEmail(String role, int id, SQLQueries sql) {
        setPreferredSize(new Dimension(900,700));
        String mail = "";
        ResultSet res;
        if(role=="SV") res= sql.stInfor(id);
        else{
            res=sql.TInfor(id);
        }
        try{
            while(res.next()){
                mail=res.getNString("Email");
            }
        } catch (SQLException e) {

        }
        Random r= new Random();
        but= new MyButton("Lấy mã xác nhận");
        String finalMail = mail;
        AtomicInteger ranNum = new AtomicInteger();
        but.addActionListener(e->{

            ranNum.set(100000 + r.nextInt(900000));
            AutomticMail Mail= new AutomticMail(sql);
            Mail.sendmail(finalMail,"Confirm your action",String.valueOf(ranNum.get()));
        });
        but.setBackground(Color.GREEN);
        but.setColor(Color.GREEN);
        but.setColorClick(Color.GREEN);
        but.setColorOver(new Color(75,150,20));
        con= new JLabel("Nhập mã xác nhận: ");
        confirm = new MyTextField();
        NewEmail= new MyTextField();
        con.setPreferredSize(new Dimension(400,50));
        confirm.setPreferredSize(new Dimension(200,50));
        confirm.setFont(new Font("Times New Roman",Font.PLAIN,48));
        NewEmail.setPreferredSize(new Dimension(200,60));
        NewEmail.setFont(new Font("Times New Roman",Font.PLAIN,48));
        con.setFont(new Font("Times New Roman",Font.PLAIN,48));
        MyButton change= new MyButton("xác nhận đổi email");
        change.setFont(new Font("Times New Roman",Font.PLAIN,50));
        change.setPreferredSize(new Dimension(300,60));
        JLabel l= new JLabel();
        New= new JLabel("Nhập Email mới:");
        New.setPreferredSize(new Dimension(400,50));
        New.setFont(new Font("Times New Roman",Font.PLAIN,50));

        l.setFont(new Font("Times New Roman",Font.PLAIN,20));
        l.setPreferredSize(new Dimension(900,60));
        l.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel top= new JPanel();
        top.setLayout(new BorderLayout());
        top.setPreferredSize(new Dimension(900,60));
        top.add(con,BorderLayout.WEST);
        top.add(confirm,BorderLayout.CENTER);
        top.add(but,BorderLayout.EAST);
        JPanel center= new JPanel();
        center.setLayout(new BorderLayout());

        JPanel t= new JPanel();
        t.setLayout(new BorderLayout());
        t.setSize(900,70);
        t.add(New,BorderLayout.WEST);
        t.add(NewEmail,BorderLayout.CENTER);
        JLabel b= new JLabel("                                    ");
        t.add(b,BorderLayout.EAST);
        center.add(t,BorderLayout.NORTH);
        JPanel n= new JPanel();
        n.setLayout(new BorderLayout());
        n.setSize(900,120);
        n.add(l,BorderLayout.CENTER);
        n.add(change,BorderLayout.SOUTH);
        center.add(n,BorderLayout.SOUTH);
        change.addActionListener(e -> {
            if(confirm.getText().isEmpty()){
                l.setText("Vui lòng nhập mã xác nhận gửi về email bạn!");
                l.setForeground(Color.RED);
            }else if(!confirm.getText().equals(String.valueOf(ranNum.get()))){
                l.setText("Mã xác nhận không chính xác!");
                l.setForeground(Color.RED);
            }else{
                boolean check=false;
                if(role=="SV"){
                    check=sql.updateSTEmail(NewEmail.getText(),id);

                }else{
                    check=sql.updateTEmail(NewEmail.getText(),id);
                }
                if(check){
                    NewEmail.setText("");
                    confirm.setText("");
                    l.setText("Cập nhật email mới thành công!");
                    l.setForeground(Color.GREEN);
                }else{
                    l.setText("Lỗi đã xảy ra, vui lòng nhập email đúng định dạng hoặc thử lại sau!");
                    l.setForeground(Color.RED);
                }
            }
        });
        setLayout(new BorderLayout());
        add(top,BorderLayout.NORTH);

        add(center,BorderLayout.CENTER);
    }
}
