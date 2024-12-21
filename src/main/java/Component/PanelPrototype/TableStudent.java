package Component.PanelPrototype;



import Component.table.Student;
import Component.table.TableCustom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TableStudent extends JPanel {
    private ArrayList<Student> students = new ArrayList<>();

    public TableStudent() {
        initComponents();
        setBackground(Color.WHITE);
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        testData(jTable1);
    }

    private void testData(JTable table) {
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));
        students.add(new Student(1, "SV01", "Danh Hanma", "24AI", "Nam","thanhdanhtqd@gmail.com", "28/04/2006", false));
        students.add(new Student(2, "SV02", "Lê Mạnh Thắng", "24AI","Nam", "thanglm.24ai@vku.udn.vn", "19/03/2006", false));
        students.add(new Student(3, "SV03", "Viết Hào", "24GIT1","Nam", "haontv.24git@vku.udn.vn", "19/08/2006", false));
        students.add(new Student(4,"SVGIT2","Buồi Gia Khôi","24GIT2","Gay","Khoibubuoi@gmail.com","1/1/2010",false));

        updateTableData();
    }

    private void updateTableData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Student student : students) {
            model.addRow(new Object[]{
                    student.getStt(),
                    student.getId(),
                    student.getName(),
                    student.getClassName(),
                    student.getSex(),
                    student.getEmail(),
                    student.getDob(),
                    student.isSelected()
            });
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
            frame.add(new TableStudent());
            frame.setSize(1100, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }


    private JScrollPane jScrollPane1;
    private JTable jTable1;
}
