package Component.PanelPrototype;

import Component.mainFrames.FrameForStudent;
import Component.mainFrames.FrameForTeacher;
import Res.SQLQueries;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfilePanel extends JPanel {
    private JLabel ID,Name,Email,Birth,Date;
    public ProfilePanel(int st, SQLQueries sql, FrameForTeacher root) {
        setPreferredSize(new Dimension(900,800));
        String name=null,email=null,birth=null,date=null;
        int id = 0;
            ResultSet res= sql.TInfor(st);
            try{
                while(res.next()){
                    id=res.getInt("TeacherID");
                    name=res.getNString("TeacherName");
                    email=res.getNString("Email");
                    Date Birth=res.getDate("BirthDate"),Date=res.getDate("RegistrationDate");
                    SimpleDateFormat f= new SimpleDateFormat("dd-MM-yyy");
                    birth=f.format(Birth);
                    date=f.format(Date);

                }
            } catch (SQLException e) {

            }
            if(id<10)ID= new JLabel("ID: TELN00"+String.valueOf(id));
            else if(id<100)ID= new JLabel("ID: TELN0"+String.valueOf(id));

            else ID= new JLabel("ID: TELN"+String.valueOf(id));
        ID.setPreferredSize(new Dimension(200,50));
        Name= new JLabel("Họ và tên: "+name);
        Name.setPreferredSize(new Dimension(200,50));

        Email= new JLabel("Email: "+email);
        Email.setPreferredSize(new Dimension(200,50));

        Birth= new JLabel("Ngày Sinh: "+birth);
        Birth.setPreferredSize(new Dimension(200,50));

        Date= new JLabel("Bạn đã tạo tài khoản vào: "+date);
        Date.setPreferredSize(new Dimension(200,50));
        ID.setFont(new Font("Times New Roman",Font.PLAIN,50));
        Name.setFont(new Font("Times New Roman",Font.PLAIN,50));
        Email.setFont(new Font("Times New Roman",Font.PLAIN,50));
        Birth.setFont(new Font("Times New Roman",Font.PLAIN,50));
        Date.setFont(new Font("Times New Roman",Font.PLAIN,50));
        GroupLayout layout= new GroupLayout(this);
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20,30,50)
                                .addComponent(ID)
                                .addGap(20,30,50)
                                .addComponent(Name)
                                .addGap(20,30,50)
                                .addComponent(Email)
                                .addGap(20,30,50)
                                .addComponent(Birth)
                                .addGap(20,30,50)
                                .addComponent(Date)
                        )

        );
        layout.setHorizontalGroup(

                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(70,100,200)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(ID)
                                        .addComponent(Name)
                                        .addComponent(Email)
                                        .addComponent(Birth)
                                        .addComponent(Date)
                                )
                        )




        );
        setLayout(layout);
        setBorder(new EtchedBorder());
    }
public ProfilePanel(int st, SQLQueries sql, FrameForStudent root) {
    setPreferredSize(new Dimension(900,800));
    String name=null,email=null,birth=null,date=null;
    int id = 0;
        ResultSet res= sql.stInfor(st);
        try{
            while(res.next()){
                id=res.getInt("StudentID");
                name=res.getNString("StudentName");
                email=res.getNString("Email");
                Date Birth=res.getDate("BirthDate"),Date=res.getDate("RegistrationDate");
                SimpleDateFormat f= new SimpleDateFormat("dd-MM-yyy");
                birth=f.format(Birth);
                date=f.format(Date);

            }
        } catch (SQLException e) {

        }
        if(id<10)ID= new JLabel("ID: STELN00"+String.valueOf(id));
        else if(id<100)ID= new JLabel("ID: STELN0"+String.valueOf(id));

        else ID= new JLabel("ID: STELN"+String.valueOf(id));
    ID.setPreferredSize(new Dimension(200,50));
    Name= new JLabel("Họ và tên: "+name);
    Name.setPreferredSize(new Dimension(200,50));

    Email= new JLabel("Email: "+email);
    Email.setPreferredSize(new Dimension(200,50));

    Birth= new JLabel("Ngày Sinh: "+birth);
    Birth.setPreferredSize(new Dimension(200,50));

    Date= new JLabel("Bạn đã tạo tài khoản vào: "+date);
    Date.setPreferredSize(new Dimension(200,50));
    ID.setFont(new Font("Times New Roman",Font.PLAIN,50));
    Name.setFont(new Font("Times New Roman",Font.PLAIN,50));
    Email.setFont(new Font("Times New Roman",Font.PLAIN,50));
    Birth.setFont(new Font("Times New Roman",Font.PLAIN,50));
    Date.setFont(new Font("Times New Roman",Font.PLAIN,50));
    GroupLayout layout= new GroupLayout(this);
    layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(20,30,50)
                            .addComponent(ID)
                            .addGap(20,30,50)
                            .addComponent(Name)
                            .addGap(20,30,50)
                            .addComponent(Email)
                            .addGap(20,30,50)
                            .addComponent(Birth)
                            .addGap(20,30,50)
                            .addComponent(Date)
                    )

    );
    layout.setHorizontalGroup(

            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addGap(70,100,200)
                            .addGroup(layout.createParallelGroup()
                                    .addComponent(ID)
                                    .addComponent(Name)
                                    .addComponent(Email)
                                    .addComponent(Birth)
                                    .addComponent(Date)
                            )
                    )




    );
    setLayout(layout);
    setBorder(new EtchedBorder());
}
}
