package Component.PanelPrototype;
import Component.mainFrames.FrameForStudent;
import Res.SQLQueries;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelOfAssignment extends JPanel {
    private MenuQues menuQues;
    private BQuestionListPanel BQuestionPanel;
    private int AssignmentID,StudentID;
    private SQLQueries sql;
    private FrameForStudent root;
    ArrayList<String> BQLists;
    public PanelOfAssignment(int AssignmentID, int StudentID, SQLQueries sql, FrameForStudent root){
        this.AssignmentID = AssignmentID;
        this.StudentID = StudentID;
        this.sql = sql;
        this.root = root;
        init();
    }

    public void init(){
        BQLists=sql.getBQ(AssignmentID);
        int numOfBQ=BQLists.size();
        if(numOfBQ!=0){
            BQuestionPanel = new BQuestionListPanel(AssignmentID, sql, numOfBQ, root, StudentID);
            menuQues = new MenuQues(numOfBQ, BQuestionPanel);
            setLayout(new MigLayout());
            setBackground(new Color(255, 255, 255));
            add(menuQues, "y 0%, h 97%");
            add(BQuestionPanel, "h 100%");
        }else{
            JLabel noBQ = new JLabel("Có vẻ như không có câu hỏi nào trong bài tập này, hãy liên hệ với giáo viên để được hỗ trợ!");
            noBQ.setFont(new Font("Milford", Font.BOLD, 20));
            noBQ.setHorizontalAlignment(SwingConstants.CENTER);
            add(noBQ);
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        f.setBackground(new Color(255,255,255));
        f.add(new PanelOfAssignment(7, 4, new SQLQueries(), new FrameForStudent(4, "user")));

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
