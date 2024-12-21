package Component.PanelPrototype;

import Component.Button.MyButton;
import Component.TextFieldAndSoOn.MyTextField;
import li.flor.nativejfilechooser.NativeJFileChooser;
import net.miginfocom.swing.MigLayout;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

import Object.BigQuestion;
public class InputBigQuestion extends JFrame {
    private String[] path,text;

    private JLabel l1;
    private MyTextField t1;
    public InputBigQuestion(int stt,String[] pathh,String[] txt) {
        this.path = pathh;
        this.text = txt;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
                int result = f.showDialog(getParent(), "Choose file");

                if (result == JFileChooser.APPROVE_OPTION) {
                    if (f.getSelectedFile().getPath().contains(".mp3")) path[0] = f.getSelectedFile().getAbsolutePath();
                    else {
                        JOptionPane.showMessageDialog(null, "File không đúng định dạng");
                    }
                }
            }
        });
        MyButton submitButton = new MyButton("ok");
        submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        ActionListener actionListener = null;
        actionListener = e -> {

            text[0] = t1.getText();
            if (text.equals("")) {
                JOptionPane.showMessageDialog(null, "Nội dung câu hỏi không được để trống");
            } else {
                if (f.getSelectedFile() != null) {
                    text[0] = t1.getText();
                    path[0] = f.getSelectedFile().getAbsolutePath();

                } else {
                    text[0] = t1.getText();
                }
                dispose();
            }
        };
        submitButton.addActionListener(actionListener);
        t1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    text[0] = t1.getText();
                    if (text.equals("")) {
                        JOptionPane.showMessageDialog(null, "Nội dung câu hỏi không được để trống");
                    } else {
                        if (f.getSelectedFile() != null) {
                            text[0] = t1.getText();
                            path[0] = f.getSelectedFile().getAbsolutePath();

                        } else {
                            text[0] = t1.getText();
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

    public String getText() {
        return text[0];
    }
    public String getPath(){
        return path[0];
    }
}
