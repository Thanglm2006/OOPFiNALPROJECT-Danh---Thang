package GUI;

import Component.Header;
import Component.Menu;
import Component.ProfilePanel;
import Res.SQLQueries;
import event.EventMenuSelected;
import event.EventShowPopupMenu;
import Component.DoAssignment;
import Component.form.Form1;
import Component.form.MainForm;
import Component.MenuItem;
import Component.PopupMenu;
import Component.ChangePass;
import Component.changeEmail;
import Component.DictionaryFrame;
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
    private Menu menu;
    private Header header;
    private Animator animator;
    private MainForm main;
    private DictionaryFrame dictionnary;
    private SQLQueries sql;
    private String [] assignments;
    private int StudentID;
    private String user;
    private JPanel[] PF= new JPanel[3];
    private HashMap<Integer,String> allAssignment;
    public FrameForStudent(int id, String name) {
        StudentID=id;
        this.user=name;
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameForStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameForStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameForStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameForStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
        init();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void init() {
        sql= new SQLQueries();
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        bg.setLayout(layout);
        menu = new Menu();
        PF[0] = new ProfilePanel(StudentID,sql);
        PF[1]= new ChangePass("SV",StudentID,sql);
        PF[2]= new changeEmail("SV",StudentID,sql);
        //
        allAssignment=sql.getStudentAssignment(StudentID);
        ArrayList<Integer> assID= new ArrayList<>();
        allAssignment.forEach((k,v)->{
            assID.add(k);
        });
        ArrayList<Integer> notFinish= new ArrayList<>();
        notFinish=sql.getNotFinished(StudentID);

        assignments=new String[notFinish.size()];
        int idx=0;
        DoAssignment assignment[]= new DoAssignment[notFinish.size()];
        for(Integer x: notFinish){
            assignments[idx]=allAssignment.get(x);
            assignment[idx]=new DoAssignment(idx,StudentID,getHeight()-100,sql);
        }
        idx=0;
        String[] all= new String[allAssignment.size()];
        AtomicInteger finalIdx = new AtomicInteger(0);
        DoAssignment allAss[]= new DoAssignment[allAssignment.size()];
        allAssignment.forEach((k,v)->{
            all[finalIdx.get()]=v;
            allAss[finalIdx.getAndAdd(1)]= new DoAssignment(k,StudentID,getHeight()-100,sql);

        });
        header = new Header(user,"Sinh Viên");
        main = new MainForm();
        AtomicInteger finalIdx1 = new AtomicInteger(0);
        //
        dictionnary= new DictionaryFrame(getWidth(),getHeight()-20,sql);
        menu.addEvent(new EventMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int subMenuIndex) {
                System.out.println("Menu Index : " + menuIndex + " SubMenu Index " + subMenuIndex);
                if(menuIndex==0 && subMenuIndex==-1){
                    main.showForm(new Form1());
                }
//                if(menuIndex==1 && subMenuIndex==0) main.showForm(new Form2());
//                if(menuIndex==3&& subMenuIndex==0) main.showForm(assignment);
//                if(menuIndex==4&& subMenuIndex==-1) main.showForm(dictionnary);
//                if(menuIndex==5) dispose();
                switch(menuIndex){
                    case 1-> {
                        if(subMenuIndex!=-1)
                            main.showForm(PF[subMenuIndex]);
                    }
                    case 2->{

                    }
                    case 3->{
                        main.showForm(assignment[subMenuIndex]);
                    }
                    case 4 ->{
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
        menu.addEventShowPopup(new EventShowPopupMenu() {
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
        menu.initMenuItem(assignments,all);

        bg.add(menu, "w 230!, spany 2");
        bg.add(header, "h 50!, wrap");
        bg.add(main, "w 100%, h 100%");
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menu.isShowMenu()) {
                    width = 60 + (170 * (1f - fraction));
                } else {
                    width = 60 + (170 * fraction);
                }
                layout.setComponentConstraints(menu, "w " + width + "!, spany2");
                menu.revalidate();
            }

            @Override
            public void end() {
                menu.setShowMenu(!menu.isShowMenu());
                menu.setEnableMenu(true);
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
                menu.setEnableMenu(false);
                if (menu.isShowMenu()) {
                    menu.hideallMenu();
                }
            }
        });

        main.showForm(new Form1());
    }


    private void initComponents() {
//        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        bg = new JLayeredPane();

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
                new FrameForStudent(1,"Thanglm2006").setVisible(true);

}
}
