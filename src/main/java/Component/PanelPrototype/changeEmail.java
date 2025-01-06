package Component.PanelPrototype;

import Component.Button.MyButton;
import Component.TextFieldAndSoOn.MyTextField;
import GUI.FrameForStudent;
import GUI.FrameForTeacher;
import Res.AutomticMail;
import Res.SQLQueries;
import net.miginfocom.swing.MigLayout;

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
    public changeEmail(int id, SQLQueries sql,FrameForStudent root) {
        setPreferredSize(new Dimension(900,700));
        String mail = "";
        ResultSet res;
        res = sql.stInfor(id);
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
        JLabel b= new JLabel("                                    ");
        change.addActionListener(e -> {
            if(confirm.getText().isEmpty()){
                l.setText("Vui lòng nhập mã xác nhận gửi về email bạn!");
                l.setForeground(Color.RED);
            }else if(!confirm.getText().equals(String.valueOf(ranNum.get()))){
                l.setText("Mã xác nhận không chính xác!");
                l.setForeground(Color.RED);
            }else{
                boolean check=false;
                check=sql.updateSTEmail(NewEmail.getText(),id);
                if(check){
                    NewEmail.setText("");
                    confirm.setText("");
                    l.setText("Cập nhật email mới thành công!");
                    l.setForeground(Color.GREEN);
                    root.getBg().removeAll();
                    root.init();
                    root.revalidate();
                    root.repaint();
                }else{
                    l.setText("Lỗi đã xảy ra, vui lòng nhập email đúng định dạng hoặc thử lại sau!");
                    l.setForeground(Color.RED);
                }
            }
        });
        setLayout(new MigLayout("center center"));
        add(con,"split 3, width 40%");
        add(confirm," x 30%,width 60%");
        add(but," height 7%, y 0.7%,wrap");
        add(New,"split 3, width 40%");
        add(NewEmail,"  x 30%,width 60%");
        add(b," width 20%,wrap");
        add(l,"span 3,wrap, y 80%, width 100%");
        add(change,"span, center , y 90%");
    }
    public changeEmail(int id, SQLQueries sql,FrameForTeacher root) {
        setPreferredSize(new Dimension(900,700));
        String mail = "";
        ResultSet res;
        res = sql.TInfor(id);
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
        JLabel b= new JLabel("                                    ");
        change.addActionListener(e -> {
            if(confirm.getText().isEmpty()){
                l.setText("Vui lòng nhập mã xác nhận gửi về email bạn!");
                l.setForeground(Color.RED);
            }else if(!confirm.getText().equals(String.valueOf(ranNum.get()))){
                l.setText("Mã xác nhận không chính xác!");
                l.setForeground(Color.RED);
            }else{
                boolean check=false;
                check=sql.updateTEmail(NewEmail.getText(),id);
                if(check){
                    NewEmail.setText("");
                    confirm.setText("");
                    l.setText("Cập nhật email mới thành công!");
                    l.setForeground(Color.GREEN);
                    root.getBg().removeAll();
                    root.init();
                    root.revalidate();
                    root.repaint();
                }else{
                    l.setText("Lỗi đã xảy ra, vui lòng nhập email đúng định dạng hoặc thử lại sau!");
                    l.setForeground(Color.RED);
                }
            }
        });
        setLayout(new MigLayout("center center"));
        add(con,"split 3, width 40%");
        add(confirm," x 30%,width 60%");
        add(but," height 7%, y 0.7%,wrap");
        add(New,"split 3, width 40%");
        add(NewEmail,"  x 30%,width 60%");
        add(b," width 20%,wrap");
        add(l,"span 3,wrap, y 80%, width 100%");
        add(change,"span, center , y 90%");
    }
}
