package Component.PanelPrototype;

import Component.scrollbar.ScrollBarCustomUI;
import net.miginfocom.swing.MigLayout;
import Res.SQLQueries;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import Object.Pair;
public class BQPanel extends JPanel {
    private ArrayList<smallQuestion> QList;
    private JScrollPane scrollPane;
    private int numOfQuestion=0,BQuestionID;
    private String FilePath;
    private SQLQueries sql;
    private AudioBar AU;
    public int getNumberOfQuestion(){
        return numOfQuestion;
    }
    public AudioBar getAU(){
        return AU;
    }
    public int calTheScore(){
        int sum=0;
        for(smallQuestion x:QList){
            sum+=x.getSore();
        }
        return sum;
    }
    public BQPanel(int BQuestionID, String FilePath, SQLQueries sql){
        this.BQuestionID = BQuestionID;
        this.FilePath = FilePath;
        this.sql = sql;
        init();
    }

    public void init(){
        setLayout(new MigLayout("wrap"));
        QList = new ArrayList<>();
        ResultSet res=sql.getQRes(BQuestionID);
        setBackground(new Color(255,255,255));

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("wrap"));
        panel.setBackground(new Color(255,255,255));
        if(FilePath!=null) AU= new AudioBar(Toolkit.getDefaultToolkit().getScreenSize().width, FilePath);
        try {
            int idx=0;

            while(res.next()){
                idx++;
                numOfQuestion++;
                int QuestionID=res.getInt("QuestionID");
                String txt=res.getNString("QuestionText");
                String audio=res.getNString("FilePath");
                ArrayList<Pair<String,Integer>> tmp=sql.getSelectionForQuestion(QuestionID);
                if(tmp.size()!=4) {
                    System.out.println(QuestionID+":"+tmp.size());
                    continue;
                }
                smallQuestion sq;
                if(audio!=null)  sq = new smallQuestion("Câu "+idx+": "+txt,tmp,audio);
                else  sq = new smallQuestion("Câu "+idx+": "+txt,tmp);
                QList.add(sq);
                panel.add(sq,"wrap");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int scrW = screen.width;
        int scrH = screen.height;

        scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        verticalScrollBar.setUI(new ScrollBarCustomUI());
        horizontalScrollBar.setUI(new ScrollBarCustomUI());
        if(AU==null)add(scrollPane, String.format("h 97%%, w %d",(int)(scrW*1) ));
        else{
            add(AU,"w 100%");
            add(scrollPane, String.format("h 97%%, w %d",(int)(scrW*1) ));
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        f.setLayout(new MigLayout("wrap"));
        f.add(new BQPanel(16,"/home/thanglm2006/Downloads/LE_listening_A2_An_invitation_to_a_party.mp3",new SQLQueries()),"h 100%");

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
