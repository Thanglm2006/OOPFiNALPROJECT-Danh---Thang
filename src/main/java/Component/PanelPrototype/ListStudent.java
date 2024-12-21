package Component.PanelPrototype;


import Component.findbar.SearchOption;
import Component.findbar.TextFieldSearchOption;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Component.Button.Button;
public class ListStudent extends JPanel {
    private JPanel taskbar;
    private TableStudent table;
    private Button addStudent,deleteStudent;
    private JLabel logo;
    private TextFieldSearchOption search;
    private ArrayList<SearchOption> searchList = new ArrayList<>();



    public ListStudent(){
        initComponents();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setBackground(new Color(255,255,255));
    }

   public void initComponents(){
       setLayout(new MigLayout("al center center, wrap"));


       addStudent = new Button();
       deleteStudent = new Button();
       addStudent.setText("Thêm");
       addStudent.setEffectColor(new Color(12,234,120));
       addStudent.setBackground(new Color(26,188,156));
       addStudent.setForeground(new Color(255,255,255));
       addStudent.setFont(new Font("SansSerif", 1, 15));
       addStudent.setIcon(new ImageIcon(getClass().getResource("/Image/add_.png")));
       add(addStudent,"x 82%, width 7%, y 4%");

       deleteStudent.setText("Xóa");
       deleteStudent.setBackground(new Color(234,32,39));
       deleteStudent.setForeground(new Color(255,255,255));
       deleteStudent.setEffectColor(new Color(229,80,57));
       deleteStudent.setFont(new Font("sansserif",1,15));
       deleteStudent.setIcon(new ImageIcon(getClass().getResource("/Image/delete_.png")));
       add(deleteStudent,"x 90%, y 4%, width 7%");

       search = new TextFieldSearchOption();

       SearchOption x = (new SearchOption("Tìm mã sv", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/num.png"))));
       SearchOption y = (new SearchOption("Tìm tên", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/user.png"))));
       SearchOption z = (new SearchOption("Lớp", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/class.png"))));
       SearchOption t = (new SearchOption("Giới tính", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/gay.png"))));
       SearchOption q = (new SearchOption("Tìm email", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/email.png"))));
       SearchOption m = (new SearchOption("Tìm ngày sinh", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/birth.png"))));
       search.addOption(x); search.addOption(y); search.addOption(z); search.addOption(t); search.addOption(q); search.addOption(m);
       searchList.add(x); searchList.add(y);searchList.add(z);searchList.add(t); searchList.add(q); searchList.add(m);
       add(search,"x 60%, y 3.5%, width 20%");



       deleteStudent.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               DefaultTableModel model = (DefaultTableModel) table.getJTable().getModel();
               int rowCount = model.getRowCount();

               for (int i = rowCount - 1; i >= 0; i--) {
                   boolean isSelected = (boolean) model.getValueAt(i, 7);
                   if (isSelected) {
                       model.removeRow(i);
                   }
               }
           }
       });


       table = new TableStudent();
       add(table,"width 100%,y 11%, height 88%");
       logo = new JLabel(new ImageIcon(getClass().getResource("/Image/vku_lo.png")));
       add(logo,"x 0%, y 1%, width 9%");

   }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("List Student");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());


            ListStudent listStudent = new ListStudent();
            frame.add(listStudent);

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
