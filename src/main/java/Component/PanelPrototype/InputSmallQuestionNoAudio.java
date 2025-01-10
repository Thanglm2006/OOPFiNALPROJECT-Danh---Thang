package Component.PanelPrototype;

import Component.Button.MyButton;
import Component.TextFieldAndSoOn.MyTextField;
import net.miginfocom.swing.MigLayout;
import Object.SmallQuestion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InputSmallQuestionNoAudio extends JFrame {
    private MyTextField t1,t2,t3,t4,t5;
    private JComboBox<String> correctAnswer;
    public InputSmallQuestionNoAudio(ArrayList<SmallQuestion> smallQuestions, int BID, int[] ID) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Nhập dữ liệu cho câu hỏi");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel p1 = new JPanel();

        JLabel l1 = new JLabel("Nhập nội dung câu hỏi "+(smallQuestions.size()+1)+":");
        l1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        t1 = new MyTextField();
        t1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        JLabel answer1 = new JLabel("Nhập câu trả lời 1:");
        answer1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        t2 = new MyTextField();
        t2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        JLabel answer2 = new JLabel("Nhập câu trả lời 2:");
        answer2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        t3 = new MyTextField();
        t3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        JLabel answer3 = new JLabel("Nhập câu trả lời 3:");
        answer3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        t4 = new MyTextField();
        t4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        JLabel answer4 = new JLabel("Nhập câu trả lời 4:");
        answer4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        t5 = new MyTextField();
        t5.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        JLabel l= new JLabel ("Chọn câu trả lời đúng:");
        l.setFont(new Font("Times New Roman",Font.PLAIN,20));
        correctAnswer = new JComboBox<>();
        correctAnswer.addItem("Câu 1");correctAnswer.addItem("Câu 2");correctAnswer.addItem("Câu 3");correctAnswer.addItem("Câu 4");
        correctAnswer.setPrototypeDisplayValue("");
        MyButton submitButton = new MyButton("ok");
        submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(t1.getText().isEmpty()||t2.getText().isEmpty()||t3.getText().isEmpty()||t4.getText().isEmpty()||t5.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Bạn chưa nhập đủ thông tin","Error",JOptionPane.ERROR_MESSAGE);
                }else {
                    String[] selection={t2.getText(),t3.getText(),t4.getText(),t5.getText()};
                    smallQuestions.add(new SmallQuestion(t1.getText(),BID,ID[0]++,selection,correctAnswer.getSelectedIndex()+1));
                    dispose();
                }
            }
        });

        p1.setLayout(new MigLayout("al center center wrap"));
        p1.add(l1, " width 30%");
        p1.add(t1, "wrap, width 70%");
        p1.add(answer1, " width 30%");
        p1.add(t2, "wrap, width 70%");
        p1.add(answer2, " width 30%");
        p1.add(t3, "wrap, width 70%");
        p1.add(answer3, " width 30%");
        p1.add(t4, "wrap, width 70%");
        p1.add(answer4, "width 30%");
        p1.add(t5, "wrap, width 70%");
        p1.add(l, "width 10%, aligny center");
        p1.add(correctAnswer, "width 30%, gapleft 0,gapright 0, aligny center");
        p1.add(submitButton, "width 20%, gapleft 0, aligny center");


        add(p1, BorderLayout.CENTER);
        setSize(600, 400);
        setVisible(true);
    }
    public String getQuestion(){
        return t1.getText();
    }
    public String getAnswer1(){
        return t2.getText();
    }
    public String getAnswer2(){
        return t3.getText();
    }
    public String getAnswer3(){
        return t4.getText();
    }
    public String getAnswer4(){
        return t5.getText();
    }
    public int getCorrectAnswer(){
        return Integer.parseInt(((String)correctAnswer.getSelectedItem()).split(" ")[1]);
    }
}