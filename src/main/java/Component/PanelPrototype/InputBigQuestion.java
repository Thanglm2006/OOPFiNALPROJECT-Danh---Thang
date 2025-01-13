package Component.PanelPrototype;

import Component.Button.MyButton;
import Component.TextFieldAndSoOn.MyTextField;
import Res.SQLQueries;
import li.flor.nativejfilechooser.NativeJFileChooser;
import net.miginfocom.swing.MigLayout;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

import Object.BigQuestion;
public class InputBigQuestion extends JFrame {
    private ArrayList<BQ> BQQ;
    private JPanel QBPanel;
    private String path;
    private JLabel l1;
    private MyTextField t1;
    public InputBigQuestion(int stt, ArrayList<BQ> BQQ, JPanel BQPanel, int AssignmentId, SQLQueries sql, int[] BID,int[] ID) {
        this.BQQ=BQQ;
        this.QBPanel=BQPanel;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        l1 = new JLabel("Nhập nội dung câu hỏi lớn thứ " + stt + ":");
        l1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        t1 = new MyTextField();
        t1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        JLabel chooseFile = new JLabel("<html><u>" + "Chọn file âm thanh cho câu hỏi lớn (nếu có, chỉ chấp nhận file mp3)" + "<u></html>");
        chooseFile.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        chooseFile.setForeground(new Color(30, 122, 236));
        JFileChooser f = new JFileChooser();
        chooseFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chooseFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                f.setFileFilter(new FileNameExtensionFilter("MP3 file", "mp3"));
                f.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = f.showDialog(getParent(), "Choose file");

                if (result == JFileChooser.APPROVE_OPTION) {
                        path = f.getSelectedFile().getAbsolutePath();
                        chooseFile.setText(f.getSelectedFile().getName());
                }
            }
        });
        MyButton submitButton = new MyButton("ok");
        submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        ActionListener actionListener = null;
        actionListener = e -> {

            if (t1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nội dung câu hỏi không được để trống");
            } else {
                if (f.getSelectedFile() != null) {
                    String tmp = t1.getText();
                    BQ tmp1 = new BQ( tmp, BID[0]++,AssignmentId,BQQ.size()+1,sql,path, ID);
                    BQQ.add(tmp1);
                    QBPanel.add(tmp1);
                    QBPanel.revalidate();
                    QBPanel.repaint();
                    dispose();

                } else {
                    String tmp = t1.getText();
                    BQ tmp1 = new BQ( tmp, BID[0]++,AssignmentId,BQQ.size()+1,sql,ID);
                    BQQ.add(tmp1);
                    QBPanel.add(tmp1);
                    QBPanel.revalidate();
                    QBPanel.repaint();
                    dispose();
                }
                dispose();
            }
        };
        submitButton.addActionListener(actionListener);
        t1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (t1.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nội dung câu hỏi không được để trống");
                    } else {
                        if (f.getSelectedFile() != null) {
                            String tmp = t1.getText();
                            BQ tmp1 = new BQ( tmp, BID[0]++,AssignmentId,BQQ.size()+1,sql,path, ID);
                            BQQ.add(tmp1);
                            QBPanel.add(tmp1);
                            QBPanel.revalidate();
                            QBPanel.repaint();
                            dispose();

                        } else {
                            String tmp = t1.getText();
                            BQ tmp1 = new BQ( tmp, BID[0]++,AssignmentId,BQQ.size()+1,sql,ID);
                            BQQ.add(tmp1);
                            QBPanel.add(tmp1);
                            QBPanel.revalidate();
                            QBPanel.repaint();
                            dispose();

                        }
                        dispose();
                    }
                }
            }
        });
        setVisible(true);
        setLayout(new MigLayout("al center center wrap"));
        add(l1, "gap related, width 30%");
        add(t1, "wrap,width 200%");
        add(chooseFile, "center, width 100%, y 50%");
        add(submitButton, " center, y 70%");
        setSize(900, 300);
    }

    public static void main(String[] args) {


    }

}
