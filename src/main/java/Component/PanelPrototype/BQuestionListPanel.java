package Component.PanelPrototype;

import Component.Button.Button;
import Component.mainFrames.FrameForStudent;
import net.miginfocom.swing.MigLayout;
import Res.SQLQueries;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BQuestionListPanel extends JPanel {
    private Button submit;
    private BQPanel baitap;
    private JLabel[] L;
    private JPanel[] TP;
    private int AssignmentID,numOfBQ,StudentID,currentBQ=0;
    private SQLQueries sql;
    private FrameForStudent root;
    private BQPanel[] BQList;

    public float getScore(){
        int sum=0;
        for(BQPanel x:BQList){
            sum+=x.calTheScore();
        }
        return ((float)(10.0/(float)getTotalQ()))*(float)sum ;
    }
    public int getTotalQ(){
        int res=0;
        for(BQPanel x: BQList){
            res+=x.getNumberOfQuestion();
        }
        return res;
    }
    public void changeBQ(int idx){
        TP[currentBQ].setVisible(false);
        BQList[currentBQ].setVisible(false);
        TP[idx].setVisible(true);
        BQList[idx].setVisible(true);
        currentBQ=idx;
    }
    public BQuestionListPanel(int AssignmentID, SQLQueries sql, int numOfBQ, FrameForStudent root,int StudentID) {
        this.root=root;
        this.AssignmentID = AssignmentID;
        this.sql = sql;
        this.numOfBQ=numOfBQ;
        this.StudentID=StudentID;
        init();
    }

    public void init(){
        MigLayout layout = new MigLayout();
        setLayout(new MigLayout("wrap"));
        setBackground(new Color(255,255,255));
        submit = new Button();
        submit.setBackground(new Color(25, 182, 247));
        submit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        submit.setForeground(new Color(255, 255, 255));
        submit.setText("Nộp bài");
        submit.addActionListener(e -> {
            boolean check = sql.updateProgress(AssignmentID, StudentID, getScore());
            if (!check) {
                JOptionPane.showMessageDialog(null, "Điểm lần này thấp hơn lần trước (" + getScore() + " ) ,nên kết quả sẽ không được lưu lại.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Điểm của bạn đtaj được: " + getScore() + "\n" + "Kết quả đã được lưu lại.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            }
            root.getBg().removeAll();
            root.init();
            root.revalidate();
            root.repaint();
        });
        BQList= new BQPanel[numOfBQ];
        L=new JLabel[numOfBQ];
        TP=new JPanel[numOfBQ];
        ResultSet res2=sql.getallBQ(AssignmentID);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int scrW = screen.width;
        int scrH = screen.height;
        try{
            int idx=0;
            while (res2.next()) {
                int id=res2.getInt("BQuestionID");
                String fp=null;
                try {
                    fp = res2.getNString("FilePath");
                }catch (SQLException e){
                    e.printStackTrace();
                }
                if(fp!=null){
                    BQList[idx]=new BQPanel(id,fp,sql);
                }
                else{
                    BQList[idx]=new BQPanel(id,null,sql);
                }
                BQList[idx].setVisible(false);
                L[idx]=new JLabel("Bài "+(idx+1));
                L[idx].setFont(new Font("Sansserif",1,22));
                TP[idx]=new JPanel();
                TP[idx].setLayout(new MigLayout("al center,wrap"));
                TP[idx].setBackground(new Color(255,255,255));
                TP[idx].add(L[idx],"center");
                TP[idx].setVisible(false);
                add(TP[idx],"y 0%, center, w 100%");
                if(BQList[idx].getAU()!=null)add(BQList[idx], String.format("x 0%%,y 3.3%%,h %d, w %d",(int) (scrH*0.83),(int)(scrW*0.856) ));
                else add(BQList[idx], String.format("x 0%%,y 3.5%%,h %d, w %d",(int) (scrH*0.83),(int)(scrW*0.856) ));
                idx++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TP[0].setVisible(true);
        BQList[0].setVisible(true);
        add(submit,String.format("w %d", (int)(scrW*1)));
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setLayout(new MigLayout("wrap"));
        f.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        f.add(new BQuestionListPanel(7, new SQLQueries(),2,new FrameForStudent(4, "user"),4));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }



}
