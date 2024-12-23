package Component.PanelPrototype;



import Object.Student;
import Component.table.TableCustom;
import Res.SQLQueries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TableStudent extends JPanel {
    private ArrayList<Student> students;
    public TableStudent(SQLQueries sql,int Teacher, int Class) {
        students=sql.getAllStudent(Teacher,Class);
        initComponents();
        setBackground(Color.WHITE);
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        testData(jTable1);
    }

    private void testData(JTable table) {
        updateTableData();
    }

    void updateTableData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        int stt=1;
        if(students!=null) {
            for (Student student : students) {
                model.addRow(new Object[]{
                        stt++,
                        student.getID(),
                        student.getName(),
                        student.GetClass(),
                        student.getGender(),
                        student.getEmail(),
                        student.getBirthDate(),
                        student.isSelected()
                });

            }
        }
    }

    public JTable getJTable() {
        return jTable1;
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"STT", "ID", "Tên", "Lớp", "Giới tính","Email", "Ngày sinh", "Tùy chọn"}
        ) {
            Class[] types = new Class[]{
                    Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false,false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        jScrollPane1.setViewportView(jTable1);


        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(250);
        }
        setLayout(new BorderLayout());
        add(jScrollPane1, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Table Student");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TableStudent(new SQLQueries(),1,1));
            frame.setSize(1100, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }


    private JScrollPane jScrollPane1;
    private JTable jTable1;
}
