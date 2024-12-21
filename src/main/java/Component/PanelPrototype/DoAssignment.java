package Component.PanelPrototype;

import Component.Button.MyButton;
import Res.SQLQueries;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoAssignment extends JPanel {
    private SQLQueries sql;
    private int Assignment;
    private int Student;
    private JScrollPane BQs;
    private JPanel centerPanel;
   private MyButton[] BQList;MyButton currentSelected=null;
   private JLabel title;
   private AudioBar audiobar;
    private JPanel p= new JPanel();
    private int currentQ=0;
    private BQuestionPanel []pa;
    private int currentSe=0;
    public void but(MyButton selected){
        selected.setColor(Color.lightGray);
        selected.setBackground(Color.lightGray);
        selected.setColorOver(Color.LIGHT_GRAY);
            if(currentSelected==selected) return;
            if(currentSelected!=null){
                currentSelected.setBackground( new Color(125, 224, 237));
                currentSelected.setColor( new Color(125, 224, 237));
                currentSelected.setColorOver( new Color(125, 224, 237));
            }
            currentSelected=selected;
    }
    private String wrapText(String text, int maxLineLength) {
        StringBuilder wrappedText = new StringBuilder();
        String[] words = text.split(" ");
        int currentLength = 0;

        for (String word : words) {
            if (currentLength + word.length() > maxLineLength) {
                wrappedText.append("<br>");
                currentLength = 0;
            }
            wrappedText.append(word).append(" ");
            currentLength += word.length() + 1;
        }

        return wrappedText.toString().trim();
    }
    public float getScore(){
        int sum=0;
        for(BQuestionPanel x:pa){
            sum+=x.calTheScore();
        }
        return ((float)(10.0/(float)getTotalQ())*(float)sum );
    }
    public int getTotalQ(){
        int res=0;
        for(BQuestionPanel x: pa){
            res+=x.getNumberOfQuestion();
        }
        return res;
    }
    public DoAssignment(int Assignment, int Student, int y,SQLQueries sql) {
        this.Assignment=Assignment;
        this.Student=Student;
        setLayout(null);
        setSize(1000,y);
        this.sql= sql;
        ArrayList<String> BQLists=sql.getBQ(Assignment);
        int numOfBQ= BQLists.size();
        BQList= new MyButton[numOfBQ];
        JPanel[]pa1= new JPanel[numOfBQ];
        int i=0;
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        for(String x:BQLists){
            BQList[i]= new MyButton();
            BQList[i].setFont(new Font("Times New Roman",Font.BOLD,20));
            BQList[i].setText("<html>"+wrapText("Bài "+(int)(i+1)+": "+x,30)+"</html>");
            Dimension d=BQList[i].getPreferredSize();
            BQList[i].setPreferredSize(new Dimension(d.width,d.height));
            BQList[i].setMaximumSize(new Dimension(200,100));
            int finalI = i;
            BQList[i].addActionListener(e->{
                pa1[currentSe].setVisible(false);
                but((MyButton)e.getSource());
                currentQ= currentSe=finalI;
                title.setText("Bài "+(currentQ+1));
                pa1[finalI].setVisible(true);
            });
            p.add(BQList[i++]);
        }

        BQs= new JScrollPane(p,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        BQs.setBounds(0,0,200,getHeight());

        centerPanel= new JPanel();
        centerPanel.setLayout( new BorderLayout());
        title= new JLabel("Bài "+(currentQ+1),JLabel.CENTER);
        title.setFont(new Font("Times New Roman",Font.BOLD,20));
        title.setBounds(0,0,centerPanel.getWidth(),20);
        centerPanel.setBounds(200,0,getWidth()+50,getHeight());
        centerPanel.add(title,BorderLayout.NORTH);

        pa= new BQuestionPanel[numOfBQ];
        ResultSet res2=sql.getallBQ(Assignment);
        try {
            int idx=0;
            while (res2.next()){
                int id=res2.getInt("BQuestionID");
//                System.out.println(id);
                String fp=null;
                try {
                    fp = res2.getNString("FilePath");
                }catch (SQLException e){
                    e.printStackTrace();
                }
                 if(fp!= null) {
                    pa[idx] = new BQuestionPanel(id, fp,sql);
                    JScrollPane scr= new JScrollPane(pa[idx]);
                    scr.getVerticalScrollBar().setUnitIncrement(15);
                     scr.setPreferredSize(new Dimension(800,y-100));
                    pa1[idx]= new JPanel();
                    pa1[idx].setLayout(new BorderLayout());
                    audiobar= new AudioBar(700,fp);
                    pa1[idx].setBounds(0,0, centerPanel.getWidth(), centerPanel.getHeight()-40 );
                    pa1[idx].add(audiobar,BorderLayout.NORTH);
                    pa1[idx].add(scr,BorderLayout.CENTER);
                }
                else {
                        pa[idx] = new BQuestionPanel(id,sql);
                        JScrollPane scr= new JScrollPane(pa[idx]);
                     scr.getVerticalScrollBar().setUnitIncrement(15);
                        scr.setPreferredSize(new Dimension(800,y-100));
                        pa1[idx]= new JPanel();

                        pa1[idx].setLayout(new BorderLayout());
                        pa1[idx].setBounds(0,35, centerPanel.getWidth(), centerPanel.getHeight()-100);
                        pa1[idx].add(scr,BorderLayout.SOUTH);}
                idx++;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        for(int j=0;j<numOfBQ;j++){
            if(j!=0)pa1[j].setVisible(false);
            centerPanel.add(pa1[j], BorderLayout.CENTER);
        }

        JPanel sub= new JPanel();
        sub.setLayout(new BorderLayout());

        MyButton submit= new MyButton("Nộp bài");
        submit.setPreferredSize(new Dimension(30,50));

        if(numOfBQ>0) {
            submit.addActionListener(e -> {
                boolean check = sql.updateProgress(Assignment, Student, getScore());
                if (!check) {
                    JOptionPane.showMessageDialog(null, "Điểm lần này thấp hơn lần trước (" + getScore() + " ) ,nên kết quả sẽ không được lưu lại.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Điểm của bạn đtaj được: " + getScore() + "\n" + "Kết quả đã được lưu lại.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                }
            });

            sub.add(submit, BorderLayout.CENTER);
            centerPanel.add(sub, BorderLayout.SOUTH);
            add(BQs);
            add(centerPanel);
            but(BQList[0]);
        }
    }

    public static void main(String[] args) {
        JFrame f= new JFrame();
        DoAssignment d= new DoAssignment(1,1,700, new SQLQueries());
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(1280,800);
        f.add(d);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
