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
public class ListStudent extends JPanel {
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
       JButton resetButton = new Button();
       resetButton.setIcon(new ImageIcon(getClass().getResource("/Image/gtk-refresh.png")));
       resetButton.addActionListener(e->{
              table.updateTableData();
              search.setText("");
              revalidate();
              repaint();
       });
       SearchOption x = (new SearchOption("Tìm mã sv", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/num.png"))));
       SearchOption y = (new SearchOption("Tìm tên", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/5.png"))));
       SearchOption t = (new SearchOption("Giới tính", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/gender.png"))));
       SearchOption q = (new SearchOption("Tìm email", new ImageIcon(TextFieldSearchOption.class.getResource("/Image/message.png"))));

       search.addOption(x); search.addOption(y); search.addOption(t); search.addOption(q);
       searchList.add(x); searchList.add(y);searchList.add(t); searchList.add(q);
       add(resetButton,"x 38%, y 4%, width 1.5%");
       add(search,"x 40%, y 3.5%, width 40%");
       addStudent.addActionListener(e->{
          new InsertST(sql,Class,table,this);

       });
       search.addKeyListener(new KeyAdapter() {
           ArrayList<Student> students;
           public void keyTyped(KeyEvent e) {
               if(e.getKeyChar()==KeyEvent.VK_ENTER){
                   int index = search.getSelectedIndex();
                   String key = search.getText();
                   if(!key.isEmpty())table.removeAll();
                   switch(index){
                        case 0->{
                            students=sql.searchSTBySTID(Integer.parseInt(key),Class);
                        }
                        case 1->{
                            students=sql.searchSTByName(key,Class);
                        }
                        case 2->{
                            students=sql.searchSTByGender(key,Class);
                        }
                        case 3->{
                            students=sql.searchSTByEmail(key,Class);
                        }
                   }
                   int stt=1;
                   if(students!=null)for (Student student : students) {
                       ((DefaultTableModel)table.getJTable().getModel()).addRow(new Object[]{
                               stt++,
                               student.getID(),
                               student.getName(),
                               student.GetClass(),
                               student.getEmail(),
                               student.getGender(),
                               student.getBirthDate(),
                               student.isSelected()
                       });

                   }
                   revalidate();
                   repaint();
               }
           }
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


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("List Student");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//
//            ListStudent listStudent = new ListStudent(new SQLQueries(),1,1);
//            frame.add(listStudent);
//
//            frame.setVisible(true);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        });
//    }
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
