package Component.mainFrames;

import Component.MenuAndHeader.Header;
import Component.MenuAndHeader.MenuStudent;
import Component.PanelPrototype.ProfilePanel;
import Res.SQLQueries;
import event.EventMenuSelected;
import event.EventShowPopupMenu;
import Component.PanelPrototype.PanelOfAssignment;
import Component.form.Form1;
import Component.form.MainForm;
import Component.MenuAndHeader.MenuItem;
import Component.PanelPrototype.PopupMenu;
import Component.PanelPrototype.ChangePass;
import Component.PanelPrototype.changeEmail;
import Component.PanelPrototype.DictionaryPanel;
import Component.PanelPrototype.ResultOfStudent;
import Component.PanelPrototype.Ranking;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import javax.swing.*;

public class FrameForStudent extends JFrame {
    private JLayeredPane bg;
    private MigLayout layout;
    private MenuStudent menuStudent;
    private Header header;
    private Animator animator;
    private MainForm main;
    private DictionaryPanel dictionnary;
    private SQLQueries sql;
    private int StudentID;
    private String user;
    private JPanel[] PF= new JPanel[3];
    private HashMap<Integer,String> allAssignment;
    private ResultOfStudent rs;
    public FrameForStudent(int id, String name) {
        StudentID=id;
        this.user=name;
        initF();
        initComponents();
        init();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    public void initF(){
        ImageIcon ic;
        ic = new ImageIcon(getClass().getResource("/Image/eln.jpg"));
        setIconImage(ic.getImage());
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameForStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    public JLayeredPane getBg() {
        return bg;
    }

    public void init() {
        main = new MainForm();
        sql= new SQLQueries();
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        bg.setLayout(layout);
        menuStudent = new MenuStudent();
        PF[0] = new ProfilePanel(StudentID,sql,this);
        PF[1]= new ChangePass(StudentID,sql,this);
        PF[2]= new changeEmail(StudentID,sql,this);
        //
        rs= new ResultOfStudent(StudentID,sql);
        Ranking rank= new Ranking(sql,StudentID);
//        JScrollPane cont= new JScrollPane(rs,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        allAssignment=sql.getStudentAssignment(StudentID);
        ArrayList<Integer> assID= new ArrayList<>();
        allAssignment.forEach((k,v)->{
            assID.add(k);
        });
        ArrayList<Integer> notFinish= new ArrayList<>();
        notFinish=sql.getNotFinished(StudentID);
        int num= notFinish.size();
        String[] assignments=new String[num];
        int idx=0;
        ArrayList<PanelOfAssignment> assignment= new ArrayList<PanelOfAssignment>();
        for(Integer x: notFinish){
            assignments[idx]=allAssignment.get(x);
           assignment.add(new PanelOfAssignment(x,StudentID,sql,this)) ;
            idx++;
        }
        idx=0;
        String[] all= new String[allAssignment.size()];
        AtomicInteger finalIdx = new AtomicInteger(0);
        PanelOfAssignment allAss[]= new PanelOfAssignment[allAssignment.size()];
        allAssignment.forEach((k,v)->{
            all[finalIdx.get()]=v;
            allAss[finalIdx.getAndAdd(1)]= new PanelOfAssignment(k,StudentID,sql,this);

        });
        header = new Header(user,"Sinh Viên");

        AtomicInteger finalIdx1 = new AtomicInteger(0);
        //
        if(dictionnary==null)dictionnary= new DictionaryPanel(getWidth(),getHeight()-20,sql,main);
        menuStudent.addEvent(new EventMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int subMenuIndex) {
                System.out.println("MenuAndHeader Index : " + menuIndex + " SubMenu Index " + subMenuIndex);
                if(menuIndex==0 && subMenuIndex==-1){
                    main.showForm(new Form1());
                }
                switch(menuIndex){
                    case 1-> {
                        if(subMenuIndex!=-1)
                            main.showForm(PF[subMenuIndex]);
                    }
                    case 2->{
                        if(subMenuIndex==0) main.showForm(rank);
                        if(subMenuIndex==1) main.showForm(rs);
                    }
                    case 3->{
                        if(subMenuIndex==-1&&num==0){
                            JOptionPane.showMessageDialog(null,"Bạn đã làm hết bài tập");
                        }
                        if(subMenuIndex!=-1)
                            main.showForm(assignment.get(subMenuIndex));
                    }
                    case 4 ->{
                        if(subMenuIndex!=-1)
                            main.showForm(allAss[subMenuIndex]);

                    }
                    case 5->{
                        main.showForm(dictionnary);
                    }
                    case 6->{
                        dispose();
                    }

                }
            }
        });
        menuStudent.addEventShowPopup(new EventShowPopupMenu() {
            @Override
            public void showPopup(Component com) {
                MenuItem item = (MenuItem) com;
                PopupMenu popup = new PopupMenu(FrameForStudent.this, item.getIndex(), item.getEventSelected(), item.getMenu().getSubMenu());
                int x = FrameForStudent.this.getX() + 52;
                int y = FrameForStudent.this.getY() + com.getY() + 86;
                popup.setLocation(x, y);
                popup.setVisible(true);
            }
        });
        menuStudent.initMenuItem(assignments,all);

        bg.add(menuStudent, "w 230!, spany 2");
        bg.add(header, "h 50!, wrap");
        bg.add(main, "w 100%, h 100%");
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menuStudent.isShowMenu()) {
                    width = 60 + (170 * (1f - fraction));
                } else {
                    width = 60 + (170 * fraction);
                }
                layout.setComponentConstraints(menuStudent, "w " + width + "!, spany2");
                menuStudent.revalidate();
            }

            @Override
            public void end() {
                menuStudent.setShowMenu(!menuStudent.isShowMenu());
                menuStudent.setEnableMenu(true);
            }

        };
        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        header.addMenuEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    animator.start();
                }
                menuStudent.setEnableMenu(false);
                if (menuStudent.isShowMenu()) {
                    menuStudent.hideallMenu();
                }
            }
        });

        main.showForm(new Form1());
    }


    public void initComponents() {
//        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        if(bg==null)bg = new JLayeredPane();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        bg.setBackground(new Color(245, 245, 245));
        bg.setOpaque(true);

        GroupLayout bgLayout = new GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 1366, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 783, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
//        setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new FrameForStudent(4, "Thanglm2006").setVisible(true);
        });

}
}
