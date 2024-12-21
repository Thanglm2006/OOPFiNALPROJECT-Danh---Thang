package Component.PanelPrototype;
import Object.*;
import Res.SQLQueries;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class InsertAssignment extends JPanel {
    final int[] ID = {0};
    private ArrayList<BQ> BQ = new ArrayList<>();
    private JScrollPane BQScroll;
    private JPanel BQPanel;
    private int AssignmentID;

    public InsertAssignment(String text, SQLQueries sql, int TecherID) {
        this.AssignmentID=sql.getAssID();
        ID[0]=sql.getSMID();
        setLayout(new BorderLayout());
        JLabel l1 = new JLabel(text);
        JButton insert = new JButton("Thêm câu hỏi lớn");
        insert.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        insert.setBackground(Color.GREEN);
        BQPanel= new JPanel();
        BQPanel.setLayout(new BoxLayout(BQPanel,BoxLayout.Y_AXIS));
        BQPanel.setBackground(Color.WHITE);
        BQPanel.setOpaque(true);
        final int[] BID = {0};
        insert.addActionListener(e -> {
            BQPanel.setBackground(Color.WHITE);
            String []path = new String[1];
            String[] txt=new String[1];
            InputBigQuestion inputBigQuestion = new InputBigQuestion(BQ.size() + 1,path,txt);
            inputBigQuestion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                   if(BID[0] ==0) {
                       BID[0] = sql.getBID();
                   }
                    String text=txt[0];
                    String pathh=path[0];
                    BQ tmp;
                   if(pathh!=null) tmp= new BQ(text, BID[0],AssignmentID,BQ.size()+1,sql,ID,pathh);
                   else{
                          tmp= new BQ(BID[0],AssignmentID,BQ.size()+1,text,sql,ID);
                   }
                    if(++BID[0] %2==0)tmp.setBackground(Color.LIGHT_GRAY);
                    else tmp.setBackground(new Color(160, 221, 200));
                    BQ.add(tmp);
                    BQPanel.add(tmp);
                    BQPanel.revalidate();
                    BQPanel.repaint();
                    BQScroll.repaint();
                }
            });
        });

        l1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        JButton submit = new JButton("Hoàn thành");
        submit.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        submit.setBackground(Color.BLUE);
        submit.setMaximumSize(new Dimension(50,50));
        submit.addActionListener(e->{
            for(BQ i:BQ){
                sql.saveAssignment(text,TecherID);
                i.save();
            }
            JOptionPane.showMessageDialog(null,"Đã lưu bài tập");
        });
        JPanel title = new JPanel();
        title.setLayout(new BorderLayout());
        title.add(l1, BorderLayout.WEST);
        title.add(submit,BorderLayout.CENTER);
        title.add(insert, BorderLayout.EAST);

        BQPanel = new JPanel();
        BQPanel.setLayout(new BoxLayout(BQPanel, BoxLayout.Y_AXIS));
        BQScroll = new JScrollPane(BQPanel);
        BQScroll.setBackground(Color.WHITE);
        BQScroll.setOpaque(true);
        add(title, BorderLayout.NORTH);
        add(BQScroll, BorderLayout.CENTER);
    }
}

class BQ extends JPanel{
    private String BQText,Audio;
    private int BQID,AssignmentID;
    private JLabel text,Stt;
    private JButton insert;
    SQLQueries sql;
    private ArrayList<SmallQuestion> smallQuestions= new ArrayList<SmallQuestion>();
    public BQ(String BQText, int BQID, int assignmentID, int stt,SQLQueries sql, int[] ID, String path) {
        setMaximumSize(new Dimension(Integer.MAX_VALUE,getPreferredSize().height+30));
        setBorder(new EtchedBorder(Color.CYAN,Color.WHITE));

        setOpaque(true);
        this.sql=sql;
        this.BQText = BQText;
        this.Audio=path;
        this.BQID = BQID;
        AssignmentID = assignmentID;
        setLayout(new BorderLayout());
        text= new JLabel(BQText);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        Stt= new JLabel("Bài "+stt+String.format(":%-30s",""));
        Stt.setMaximumSize(new Dimension(100,getMaximumSize().height));
        Stt.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        insert= new JButton("Thêm câu hỏi");

        insert.addActionListener(e->{
            String[] infor=new String[7];
            InputSmallQuestionNoAudio inputSmallQuestion = new InputSmallQuestionNoAudio(smallQuestions.size()+1,BQID,AssignmentID,infor);
            inputSmallQuestion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e){
                    int BID=BQID;
                    String text=infor[0];
                    String[] answers=new String[4];
                    for(int i=0;i<4;i++)answers[i]=infor[i+1];

                    int correctAnswer=Integer.parseInt(infor[5]);
                    smallQuestions.add(new SmallQuestion(text, ID[0]++,BID,answers,correctAnswer));
                }
            });
        });
        insert.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        insert.setBackground(Color.GREEN);
        add(Stt,BorderLayout.WEST);
        add(text,BorderLayout.CENTER);
        add(insert,BorderLayout.EAST);
    }
    public BQ(int BQID, int assignmentID, int stt,String BQText,SQLQueries sql,int[] ID) {
        setMaximumSize(new Dimension(Integer.MAX_VALUE,getPreferredSize().height+30));
        setBorder(new EtchedBorder(Color.CYAN,Color.WHITE));
        setOpaque(true);
        this.BQText = BQText;
        this.BQID = BQID;
        this.sql=sql;
        AssignmentID = assignmentID;
        setLayout(new BorderLayout());
        text= new JLabel(BQText);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        Stt= new JLabel("Bài "+stt+String.format(":%-30s",""));
        Stt.setMaximumSize(new Dimension(100,getMaximumSize().height));
        Stt.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        insert= new JButton("Thêm câu hỏi");
        insert.addActionListener(e->{
            String[] infor=new String[7];
            InputSmallQuestion inputSmallQuestion = new InputSmallQuestion(smallQuestions.size()+1,BQID,AssignmentID,infor);
            inputSmallQuestion.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e){
                    int BID=BQID;
                    String text=infor[0];
                    String[] answers=new String[4];
                    for(int i=0;i<4;i++)answers[i]=infor[i+1];

                    int correctAnswer=Integer.parseInt(infor[5]);
                    String Audio=infor[6];
                    if(Audio==null)smallQuestions.add(new SmallQuestion(text, ID[0]++,BID,answers,correctAnswer));
                    else smallQuestions.add(new SmallQuestion(text, ID[0]++,BID,Audio,answers,correctAnswer));
                }
            });
        });

        insert.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        insert.setBackground(Color.GREEN);
        add(Stt,BorderLayout.WEST);
        add(text,BorderLayout.CENTER);
        add(insert,BorderLayout.EAST);
    }
    public void save(){
        if(Audio!=null){
            sql.saveBigQuestion(AssignmentID,BQText,Audio);
            int BQID=1;
            BQID=sql.getBID()-1;
            for(SmallQuestion x:smallQuestions){
                sql.saveSmallQuestion(BQID,x.getQuestionText(),null);
                for(int i=0;i<4;i++){
                    int tf=0;
                    if(x.getCorrectAnswer()==i+1) tf=1;
                    sql.saveSelection(x.getQuestionID(),x.getSelection(i),tf);
                }

            }
        }else{
            sql.saveBigQuestion(AssignmentID,BQText,null);
            int BQID=1;
            BQID=sql.getBID()-1;
            for(SmallQuestion x:smallQuestions){
                if(x.getAudio()!=null) sql.saveSmallQuestion(BQID,x.getQuestionText(),x.getAudio());
                else sql.saveSmallQuestion(BQID,x.getQuestionText(),null);
                for(int i=0;i<4;i++){
                    int tf=0;
                    if(x.getCorrectAnswer()==i+1) tf=1;
                    sql.saveSelection(x.getQuestionID(),x.getSelection(i),tf);
                }
            }
        }
    }

}
