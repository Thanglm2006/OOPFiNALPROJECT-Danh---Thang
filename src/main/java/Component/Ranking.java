package Component;

import Res.SQLQueries;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ranking extends JPanel {
    public Ranking(SQLQueries sql) {
        ResultSet res=sql.allStudent();
        setLayout(new BorderLayout());
        JPanel c= new JPanel();
        c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
        c.setBackground(Color.WHITE);
        try{
            int idx=1;
            while(res.next()){
                ress tmp=new ress(idx++,res.getNString("StudentName"),res.getFloat("score"));
                tmp.setMaximumSize(new Dimension(Integer.MAX_VALUE,tmp.getPreferredSize().height));
                c.add(tmp);
            }
        } catch (SQLException e) {

        }
        JScrollPane cr= new JScrollPane(c);
        add(cr);
    }
}
class ress extends JPanel{
    private JLabel Stt,Name, Score;

    public ress(int stt, String name, float score) {
        Stt= new JLabel(String.format("%-30d",stt));
        Name= new JLabel(name);
        Score= new JLabel(String.format("%.2f",score));
        Stt.setFont(new Font("Times New Roman",Font.PLAIN,30));
//        Stt.setPreferredSize(new Dimension(40,30));
        Score.setFont(new Font("Times New Roman",Font.PLAIN,30));
//        Name.setPreferredSize(new Dimension(400,30));
        Name.setFont(new Font("Times New Roman",Font.PLAIN,30));
//        Name.setPreferredSize(new Dimension(40,30));
        setLayout(new BorderLayout());
        add(Stt,BorderLayout.WEST);
        add(Name,BorderLayout.CENTER);
        add(Score,BorderLayout.EAST);
        setBorder(new EtchedBorder(Color.CYAN,Color.BLUE));
    }
}