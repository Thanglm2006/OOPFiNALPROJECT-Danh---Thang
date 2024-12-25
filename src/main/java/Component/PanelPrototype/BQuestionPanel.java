package Component.PanelPrototype;

import Res.SQLQueries;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BQuestionPanel extends JPanel {
    private int ID;
    private String AudioString;
    private ArrayList<smallQuestion> QList;
    private SQLQueries sql;
    public int calTheScore(){
        int sum=0;
        for(smallQuestion x:QList){
            sum+=x.getSore();
        }
        return sum;
    }
    public int getNumberOfQuestion(){
        return QList.size();
    }
    public BQuestionPanel(int id,String audioString, SQLQueries sql) {
        AudioString= audioString;
        ID= id;
        this.sql= sql;
        ResultSet res=sql.getQRes(ID);

        QList= new ArrayList<>();

        try {
            int idx=0;

            while(res.next()){
                idx++;
                String txt=res.getNString("QuestionText");
                HashMap<String,Integer> tmp=sql.getSelectionForQuestion(res.getInt("QuestionID"));
                QList.add(new smallQuestion("Câu "+idx+": "+txt,tmp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        GroupLayout layout= new GroupLayout(this);
        GroupLayout.Group gr= layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.Group gr1= layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.Group gr3=layout.createSequentialGroup();
        for(smallQuestion x:QList ){
            gr1.addComponent(x,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE);
            gr3.addComponent(x,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE).addGap(5,5,10);

        }
        layout.setHorizontalGroup(gr1);
        gr.addGroup(gr3);
        layout.setVerticalGroup(gr);
        setLayout(layout);
    }
    public BQuestionPanel(int id,SQLQueries sql) {
        ID= id;
        this.sql= sql;
        ResultSet res=sql.getQRes(ID);

        QList= new ArrayList<>();

        try {
            int idx=0;
            if(res!=null)while(res.next()){
                idx++;
                String txt=res.getNString("QuestionText");

                HashMap<String,Integer> tmp=sql.getSelectionForQuestion(res.getInt("QuestionID"));
                String fp=res.getNString("FilePath");
                if(fp!=null&&!fp.isEmpty()){
                    QList.add( new smallQuestion("Câu "+idx+": "+txt,tmp,fp));
                }
                else QList.add(new smallQuestion("Câu "+idx+": "+txt,tmp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        GroupLayout layout= new GroupLayout(this);
        GroupLayout.Group gr= layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.Group gr1= layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.Group gr3=layout.createSequentialGroup();
        for(smallQuestion x:QList ){
            gr.addComponent(x,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE);
            gr3.addComponent(x,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE).addGap(5,5,10);

        }
        layout.setHorizontalGroup(gr);
        gr1.addGroup(gr3);
        layout.setVerticalGroup(gr1);
        setLayout(layout);
    }

//    public static void main(String[] args) {
//        JFrame f= new JFrame();
//        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        f.setSize(900,750);
//        BQuestionPanel p= new BQuestionPanel(1, new SQLQueries());
//        JScrollPane scr= new JScrollPane(p);
//        scr.setPreferredSize(new Dimension(800,700));
//        JPanel p1= new JPanel();
//        p1.setLayout(new BorderLayout());
//        p1.setSize(800,700);
//        p1.add(scr,BorderLayout.CENTER);
//        JPanel p2= new JPanel();
//        p2.setSize(900,700);
//        p2.add(p1);
//        f.add(p2);
//        f.setVisible(true);
//        f.setLocationRelativeTo(null);
//    }
}
