package Component.PanelPrototype;
import Component.mainFrames.FrameForTeacher;
import Object.*;
import Res.SQLQueries;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;

public class InsertAssignment extends JPanel {
    final int[] ID = {0};
    private ArrayList<BQ> BQ = new ArrayList<>();
    private JScrollPane BQScroll;
    private JPanel BQPanel;
    private int AssignmentID;

    public InsertAssignment(String text, SQLQueries sql, int TecherID, FrameForTeacher root) {

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
            if(BID[0] ==0) {
                BID[0] = sql.getBID();
                BID[0]=sql.getSMID();
            }
            InputBigQuestion inputBigQuestion = new InputBigQuestion(BQ.size() + 1,BQ,BQPanel,AssignmentID,sql, BID,ID);
        });

        l1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        JButton submit = new JButton("Hoàn thành");
        submit.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        submit.setBackground(Color.BLUE);
        submit.setMaximumSize(new Dimension(50,50));
        submit.addActionListener(e->{
            sql.saveAssignment(text,TecherID);
            for(BQ i:BQ){
                i.save();
            }
            JOptionPane.showMessageDialog(null,"Đã lưu bài tập");
            setVisible(false);
            root.getBg().removeAll();
            root.init();
            root.revalidate();
            root.repaint();
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
    private ArrayList<SmallQuestion> smallQuestions= new ArrayList<>();
    public BQ(String BQText, int BQID, int assignmentID, int stt,SQLQueries sql, String path, int[] ID) {
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
            new InputSmallQuestionNoAudio(smallQuestions,BQID,ID);
        });
        insert.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        insert.setBackground(Color.GREEN);
        add(Stt,BorderLayout.WEST);
        add(text,BorderLayout.CENTER);
        add(insert,BorderLayout.EAST);
    }
    public BQ(String BQText, int BQID, int assignmentID, int stt,SQLQueries sql, int[] ID) {
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
           new InputSmallQuestion(smallQuestions,BQID,ID);
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
                    sql.saveSelection(x.getId(),x.getSelection(i),tf);
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
                    sql.saveSelection(x.getId(),x.getSelection(i),tf);
                }
            }
        }
    }

}
