package Component.PanelPrototype;

import Res.SQLQueries;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class InsertClass extends JFrame {
    public InsertClass(SQLQueries sql, int Teacher) throws HeadlessException {
        initComponents(sql,Teacher);
    }
    public void initComponents(SQLQueries sql, int Teacher){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel l1= new JLabel("Nhập tên lớp học");
        JTextField t1= new JTextField();
        t1.setFont(new Font("Arial",Font.PLAIN,20));
        JButton b1= new JButton("Thêm");
        b1.addActionListener(e->{
            if(!t1.getText().isEmpty()){
                sql.insertClass(t1.getText(),Teacher);
                JOptionPane.showMessageDialog(null,"Thêm thành công");
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,"Vui lòng nhập tên lớp học");
            }
        });
        JButton b2= new JButton("Hủy");
        b2.addActionListener(e->{
            dispose();
        });
        setLayout(new MigLayout("al center center"));
        add(l1,"width 30%, height 30%, gap related");
        add(t1,"gap related,x 30%, width 70%, height 30%, wrap");
        add(b1,"x 40%, split 2 ,width 20%, height 30%, gap unrelated");
        add(b2,"x 70%, gap unrelated, width 20%, height 30%");
        setSize(500,200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        new InsertClass(new SQLQueries(),1);
    }
}
