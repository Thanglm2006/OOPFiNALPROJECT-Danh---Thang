package Component.mainFrames;

import Component.MenuAndHeader.Header;
import Component.MenuAndHeader.MenuTeacher;
import Component.PanelPrototype.*;
import Component.PanelPrototype.PopupMenu;
import Res.SQLQueries;
import event.EventMenuSelected;
import event.EventShowPopupMenu;
import Component.form.Form1;
import Component.form.MainForm;
import Component.MenuAndHeader.MenuItem;
import Component.PanelPrototype.DictionaryPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import javax.swing.*;

public class FrameForTeacher extends JFrame {

    private JLayeredPane bg;
    private MigLayout layout;
    private MenuTeacher menuTeacher;
    private Header header;
    private String user; private String [] cll; private ListStudent[] STL;
    private SQLQueries sql;
    private int Teacher;
    private JPanel[] PF = new JPanel[10];
    private Animator animator;
    private MainForm main;
    private DictionaryPanel dictionaryPanel;

    public FrameForTeacher(int id, String Name) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameForTeacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        this.Teacher=id;
        this.user=Name;
        initComponents();
        init();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    private void initClass(){
        HashMap<Integer,String> classes=sql.getClass(Teacher);
        cll= new String[classes.size()+1];
        int i=0;
        for(int key:classes.keySet()){
            cll[i]=classes.get(key);
            i++;
        }
        STL= new ListStudent[cll.length];
        AtomicInteger idx=new AtomicInteger(0);
        classes.forEach((key, value) -> {
            STL[idx.getAndAdd(1)]=new ListStudent(sql,Teacher,key);
        });
        cll[cll.length-1]="Thêm lớp học +";
    }

    public JLayeredPane getBg() {
        return bg;
    }

    public void init() {
        ImageIcon ic;
        ic = new ImageIcon(getClass().getResource("/Image/eln.jpg"));
        setIconImage(ic.getImage());
        sql= new SQLQueries();
        dictionaryPanel= new DictionaryPanel(getWidth(),getHeight(),sql,main);
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        PF[0]= new ProfilePanel(Teacher,sql,this);
        PF[1]= new ChangePass(Teacher,sql,this);
        PF[2]= new changeEmail(Teacher,sql,this);
        bg.setLayout(layout);
        initClass();
        menuTeacher = new MenuTeacher();
        header = new Header(user,"Giáo Viên");
        main = new MainForm();
        menuTeacher.addEvent(new EventMenuSelected(){
            @Override
            public void menuSelected(int menuIndex, int subMenuIndex) {
                System.out.println("MenuAndHeader Index : " + menuIndex + " SubMenu Index " + subMenuIndex);
                if(menuIndex==0 && subMenuIndex==-1){
                    main.showForm(new Form1());
                }
                switch(menuIndex){
                    case 1->{
                        if(subMenuIndex!=-1)
                            main.showForm(PF[subMenuIndex]);
                    }
                    case 2->{
                            if(subMenuIndex!=-1) {
                                if(subMenuIndex!=cll.length-1)
                                    main.showForm(STL[subMenuIndex]);
                                else {
                                    InsertClass inp= new InsertClass(sql,Teacher);
                                    inp.addWindowListener(new WindowAdapter() {
                                        @Override
                                        public void windowClosed(WindowEvent e) {
                                            bg.removeAll();
                                            init();
                                            revalidate();
                                            repaint();
                                        }
                                    });

                                }
                            }
                    }
                    case 3->{
                        if(subMenuIndex==0){

                            String assingnmentText=JOptionPane.showInputDialog("Nhập tên bài tập");
                            if(assingnmentText!=null)if(!assingnmentText.equals("")){
                                InsertAssignment assignment= new InsertAssignment(assingnmentText,sql,Teacher,FrameForTeacher.this);
                                main.showForm(assignment);

                            }
                        }
                        else if(subMenuIndex==1){
                            AssignmentList l= new AssignmentList(sql,Teacher);
                            main.showForm(l);
                        }
                    }
                    case 4->{
                        main.showForm(dictionaryPanel);
                    }
                    case 5->{
                        dispose();
                    }
                }
            }
        });
        menuTeacher.addEventShowPopup(new EventShowPopupMenu() {
            @Override
            public void showPopup(Component com) {
                MenuItem item = (MenuItem) com;
                PopupMenu popup = new PopupMenu(FrameForTeacher.this, item.getIndex(), item.getEventSelected(), item.getMenu().getSubMenu());
                int x = FrameForTeacher.this.getX() + 52;
                int y = FrameForTeacher.this.getY() + com.getY() + 86;
                popup.setLocation(x, y);
                popup.setVisible(true);
            }
        });
        menuTeacher.itemTeacher(cll);
        bg.add(menuTeacher, "w 230!, spany 2");
        bg.add(header, "h 50!, wrap");
        bg.add(main, "w 100%, h 100%");
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menuTeacher.isShowMenu()) {
                    width = 60 + (170 * (1f - fraction));
                } else {
                    width = 60 + (170 * fraction);
                }
                layout.setComponentConstraints(menuTeacher, "w " + width + "!, spany2");
                menuTeacher.revalidate();
            }

            @Override
            public void end() {
                menuTeacher.setShowMenu(!menuTeacher.isShowMenu());
                menuTeacher.setEnableMenu(true);
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
                menuTeacher.setEnableMenu(false);
                if (menuTeacher.isShowMenu()) {
                    menuTeacher.hideallMenu();
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
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setExtendedState(JFrame.MAXIMIZED_VERT);

        bg.setBackground(new java.awt.Color(245, 245, 245));
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



        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrameForTeacher(2,"GVDanhhanma").setVisible(true);
            }
        });
    }



}

