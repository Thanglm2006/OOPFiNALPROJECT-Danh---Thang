package Component.PanelPrototype;

import Component.Button.MyButton;
import Component.Button.Button;
import Component.scrollbar.ScrollBarCustom;
import Component.scrollbar.ScrollBarCustomUI;
import Component.table.TableCustom;
import Res.SQLQueries;
import net.miginfocom.swing.MigLayout;
import Component.mainFrames.FrameForStudent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuQues extends JPanel {
    private ArrayList<Button> BigQuestionList;
    private JScrollPane scrollPane;
    private Color click = new Color(149,165,166);
    private Color Default = new Color(76,181,195);
    private int currentSelected = 0;
    private int numOfBQ;
    private BQuestionListPanel BQuestionPanel;
    public MenuQues(int numOfBQ,BQuestionListPanel BQuestionPanel){
        this.numOfBQ=numOfBQ;
        this.BQuestionPanel = BQuestionPanel;
        initComponents();
    }

    public void initComponents(){
        setBackground(new Color(255,255,255));
        setLayout(new MigLayout("wrap"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255,255,255));
        buttonPanel.setLayout(new MigLayout("wrap"));
        BigQuestionList = new ArrayList<Button>();

        for(int i=1;i<=numOfBQ;i++){
            Button button = new Button();
            button.setText("Bài " +i);
            button.setFont(new Font("Sansserif",1,18));
            button.setBackground(Default);

            BigQuestionList.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BigQuestionList.get(currentSelected).setBackground(Default);
                    currentSelected = BigQuestionList.indexOf(button);
                    button.setBackground(click);
                    BQuestionPanel.changeBQ(currentSelected);
                }
            });
            buttonPanel.add(button,"w 220!, h 120!, wrap");
        }
        BigQuestionList.get(currentSelected).setBackground(click);

        scrollPane = new JScrollPane(buttonPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        verticalScrollBar.setUI(new ScrollBarCustomUI());
        horizontalScrollBar.setUI(new ScrollBarCustomUI());

        add(scrollPane,"h 100%, w 250!");
    }


    public static void main(String[] args) {
        // Tạo JFrame để hiển thị MenuQues
        JFrame frame = new JFrame("Menu Ques Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 200);

        // Thêm MenuQues vào JFrame
        MenuQues menuQues = new MenuQues(10, new BQuestionListPanel(1, new SQLQueries(), 10, new FrameForStudent(1, "user"), 1));
        frame.add(menuQues);

        // Hiển thị JFrame
        frame.setVisible(true);
    }
}
