package Component.PanelPrototype;


import Component.findbar.SearchOption;
import Component.findbar.TextFieldSearchOption;
import Res.SQLQueries;
import net.miginfocom.swing.MigLayout;
import Object.Student;
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
    private int Teacher;
    private int Class;

    public ListStudent(SQLQueries sql,int  Teacher,int Class){
        this.Teacher=Teacher;
        this.Class=Class;
        initComponents(sql);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setBackground(new Color(255,255,255));
    }

   public void initComponents(SQLQueries sql){
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
       deleteStudent.setIcon(new ImageIcon(getClass().getResource("/Image/delete.png")));
       add(deleteStudent,"x 90%, y 4%, width 7%");

       search = new TextFieldSearchOption();

       SearchOption x = (new SearchOption("Tìm mã sv", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/num.png"))));
       SearchOption y = (new SearchOption("Tìm tên", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/5.png"))));
       SearchOption z = (new SearchOption("Lớp", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/class.png"))));
       SearchOption t = (new SearchOption("Giới tính", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/gender.png"))));
       SearchOption q = (new SearchOption("Tìm email", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/message.png"))));
       SearchOption m = (new SearchOption("Tìm ngày sinh", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/birth.png"))));
       search.addOption(x); search.addOption(y); search.addOption(z); search.addOption(t); search.addOption(q); search.addOption(m);
       searchList.add(x); searchList.add(y);searchList.add(z);searchList.add(t); searchList.add(q); searchList.add(m);
       add(search,"x 60%, y 3.5%, width 20%");
       addStudent.addActionListener(e->{
          new InsertST(sql,Class,table,this);

       });
       deleteStudent.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               DefaultTableModel model = (DefaultTableModel) table.getJTable().getModel();
               int rowCount = model.getRowCount();

               for (int i = rowCount - 1; i >= 0; i--) {
                   boolean isSelected = (boolean) model.getValueAt(i, 7);
                   if (isSelected) {
                       sql.deleteStudentOutOfClass((int)model.getValueAt(i, 1),Class);
                       model.removeRow(i);
                       revalidate();
                       repaint();
                   }
               }
           }
       });


       table = new TableStudent(sql,Teacher,Class);
       add(table,"width 100%,y 11%, height 88%");
       logo = new JLabel(new ImageIcon(getClass().getResource("/Image/vku_lo.png")));
       add(logo,"x 0%, y 1%, width 9%");

   }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("List Student");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

            ListStudent listStudent = new ListStudent(new SQLQueries(),1,1);
            frame.add(listStudent);

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
class InsertST extends JFrame {
    private JPanel root;
    public InsertST(SQLQueries sql, int Teacher,TableStudent table, JPanel root) throws HeadlessException {
        this.root=root;
        initComponents(sql,Teacher,table);
    }
    public void initComponents(SQLQueries sql, int Class,TableStudent table){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel l1= new JLabel("Nhập ID sinh viên:");
        JTextField t1= new JTextField();
        t1.setFont(new Font("Arial",Font.PLAIN,20));
        JButton b1= new JButton("Thêm");
        b1.addActionListener(e->{
            if(!t1.getText().isEmpty()){
                boolean check= sql.insertStudentIntoClass(Integer.parseInt(t1.getText()),Class);
                if(check) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công");
                    table.updateTableData();
                    root.revalidate();
                    root.repaint();

                }else JOptionPane.showMessageDialog(null,"Sinh viên đã tồn tại trong lớp hoặc không tồn tại ID sinh viên");
                dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,"Vui lòng nhập ID sinh viên");
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
}
