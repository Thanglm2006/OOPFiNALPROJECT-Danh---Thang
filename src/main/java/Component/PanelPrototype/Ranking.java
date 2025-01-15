package Component.PanelPrototype;

import Component.findbar.SearchOption;
import Component.findbar.TextFieldSearchOption;
import Res.SQLQueries;
import net.miginfocom.swing.MigLayout;
import Object.Student;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Component.Button.Button;

public class Ranking extends JPanel {
    private Rank rankTable;
    private Button addRank, deleteRank;
    private JLabel logo;
    private TextFieldSearchOption search;
    private ArrayList<SearchOption> searchList = new ArrayList<>();
    private int Student;

    public Ranking(SQLQueries sql, int Student){
        this.Student = Student;
        initComponents(sql);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setBackground(new Color(255,255,255));
    }

    public void initComponents(SQLQueries sql){
        setLayout(new MigLayout("al center center, wrap"));
        search = new TextFieldSearchOption();

        SearchOption optionID = new SearchOption("Tìm mã sv", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/num.png")));
        SearchOption optionName = new SearchOption("Tìm tên", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/5.png")));
        SearchOption optionGender = new SearchOption("Giới tính", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/gender.png")));
        SearchOption optionEmail = new SearchOption("Tìm email", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/message.png")));

        search.addOption(optionID);
        search.addOption(optionName);
        search.addOption(optionGender);
        search.addOption(optionEmail);
        searchList.add(optionID);
        searchList.add(optionName);
        searchList.add(optionGender);
        searchList.add(optionEmail);


        add(search,"x 40%, y 3.5%, width 40%");

        rankTable = new Rank(sql, Student);
        add(rankTable,"width 100%, y 11%, height 88%");

        // Add Logo and Class ID
        logo = new JLabel(new ImageIcon(getClass().getResource("/Image/vku_lo.png")));
        String ID;
        int Class=rankTable.getClassID();
        if(Class < 10) {
            ID = "CLASS00" + Class;
        } else if(Class < 100) {
            ID = "CLASS0" + Class;
        } else {
            ID = "CLASS" + Class;
        }
        JLabel ClassId = new JLabel("ID lớp học: " + ID);
        ClassId.setFont(new Font("Arial", Font.PLAIN, 20));
        add(logo,"x 0%, y 1%, width 9%");
        add(ClassId,"x 15%, y 3%, width 9%");
    }
}
