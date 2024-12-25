package Component.PanelPrototype;

import Component.Button.Button;
import Res.SQLQueries;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignmentList extends JPanel {
    private SQLQueries sql;
    private int Teacher;
    public AssignmentList(SQLQueries sql, int Teacher) {
        this.sql=sql;
        this.Teacher=Teacher;
        init();
    }
    public void init(){
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        JPanel center,top;
        center= new JPanel();
        top= new JPanel();
        center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Danh sách bài tập");
        title.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        top.setSize(1000,40);
        top.add(title);
        add(top,BorderLayout.NORTH);
        ResultSet res=sql.getAllAss(Teacher);
        if(res!=null){
            try{
                while(res.next()){
                    String Name=res.getString("AssignmentName");
                    int id=res.getInt("AssignmentID");
                    Assi tmp= new Assi(id,Name,sql);
                    tmp.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width-110,40));
                    tmp.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,40));
                    center.add(tmp);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        JScrollPane scroll = new JScrollPane(center);
        scroll.setHorizontalScrollBar(null);
        scroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        add(scroll,BorderLayout.CENTER);

    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setSize(900,600);
//        frame.add(new AssignmentList(new SQLQueries(),1));
//        frame.setVisible(true);
//    }
}
class Assi extends JPanel{
    JLabel ID,Name;
    Button edit,delete,insert;
    public Assi(int id,String Name, SQLQueries sql) {

        this.Name = new JLabel(" "+Name);
        this.Name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        ID=new JLabel();
        String idd;
        if (id<10) idd="BT00"+id;
        else if (id<100) idd="BT0"+id;
        else idd="BT"+id;
        ID.setText("Mã bài: "+idd);
        ID.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        this.edit = new Button();
        this.delete = new Button();
        edit.setBackground(Color.GREEN);
        insert=new Button();
        insert.setBackground(new Color(54, 155, 255));
        insert.setText("Thêm vào lớp học");
        insert.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        delete.setBackground(Color.RED);
        edit.setText("Chỉnh Sửa");
        edit.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        delete.setText("Xóa");
        delete.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        delete.addActionListener(e->{
            int op=JOptionPane.showConfirmDialog(null,"Bạn có chắc chắn muốn xóa bài tập này không?","Xác nhận",JOptionPane.YES_NO_OPTION);
            if(op==JOptionPane.YES_OPTION){
//                sql.deleteAss(id);
                setVisible(false);
            }
        });
        edit.addActionListener(e->{
            String assingnmentText=JOptionPane.showInputDialog("Nhập tên bài tập");
            if(assingnmentText!=null){
                sql.updateAss(id,assingnmentText);
                this.Name.setText(assingnmentText);
            }
        });
        insert.addActionListener(e->{
            String classID=JOptionPane.showInputDialog("Nhập ID lớp học");
            if(classID!=null){
                boolean check=sql.insertAssToClass(id,Integer.parseInt(classID));
                if(check){
                    JOptionPane.showMessageDialog(null,"Thêm thành công");
                }
                else{
                    JOptionPane.showMessageDialog(null,"Thêm thất bại, Bài tập đã tồn tại trong lớp học, hoặc ID lớp học bị sai!");
                }
            }
        });
        insert.setPreferredSize(new Dimension(30,18));
        delete.setPreferredSize(new Dimension(20,18));
        edit.setPreferredSize(new Dimension(20,18));
        setBorder(new EtchedBorder(Color.CYAN,Color.WHITE));
        setBackground(Color.lightGray);
        setLayout(new MigLayout(new LC().fillX(),new AC().align("right").grow().fill(),new AC().align("right").grow().fill()));

        add(ID,"x 0%, y 0%, w 20%,height 8%");
        add(this.Name,"x 10%, y 0%, w 50%,height 8%");
        add(edit,"x 54%, y 0%, w 10%,height 1%");
        add(insert,"x 65%, y 0%, w 10%,height 1%");
        add(delete,"x 76%, y 0%, w 10%,height 1%");

    }
}