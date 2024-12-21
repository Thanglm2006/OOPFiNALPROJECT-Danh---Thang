package Component.PanelPrototype;

import Component.table.TableCustom;
import Res.SQLQueries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Rank extends JPanel {
    private SQLQueries sql;

    public Rank(SQLQueries sql) {
        this.sql=sql;
        initComponents();
        setBackground(Color.WHITE);
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        testData(jTable1);
    }

    private void testData(JTable table) {

        ResultSet res = sql.allStudent();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        try{
            int idx =1;
            while(res.next()){
                model.addRow(new Object[]{idx++,res.getNString("studentName"),res.getFloat("score"),false});
            }
        } catch (SQLException e) {

        }
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"STT","Tên", "Điểm", "Tùy chọn"}
        ) {
            Class[] types = new Class[]{
                    Object.class, Object.class, Object.class,  Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false,true
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
            frame.add(new Rank());
            frame.setSize(1100, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private JScrollPane jScrollPane1;
    private JTable jTable1;
}
