package Swing;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class smallQuestion extends JPanel {
    private String Question;
    private HashMap<String,Integer> selections;
    private String AudioString;
    private AudioBar audiobar;
    private JRadioButton[] op;
    private ButtonGroup gr;
    private JLabel txt;
    private ImageIcon im;
    public int getSore(){
        int sc=0;
        for(int i=0;i<4;i++){
            if(op[i].isSelected())if(selections.get(op[i].getText())==1) sc++;
        }
        return sc;
    }
    public smallQuestion(String question, HashMap<String, Integer> selections, String audioString) {
        AudioString=audioString;
        audiobar= new AudioBar(300,AudioString);
        Question = question;
        this.selections = selections;
        AudioString = audioString;
        txt= new JLabel(Question);
        txt.setFont(new Font("Milford",Font.BOLD,20));
        txt.setAlignmentX(LEFT_ALIGNMENT);

        gr= new ButtonGroup();
        op= new JRadioButton[4];
        AtomicInteger i= new AtomicInteger();
        selections.forEach((k,v)->{
            op[i.get()]=new JRadioButton(k);
            op[i.get()].setFont(new Font("Milford",Font.PLAIN,18));
            gr.add(op[i.get()]);
            add(op[i.get()]);
            i.getAndIncrement();
        });

        GroupLayout layout= new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(audiobar,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(txt,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addGap(5,7,10)
                        .addComponent(op[0],GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(op[1],GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(op[2],GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(op[3],GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)


        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(audiobar,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                                .addGap(5,5,10)
                                .addComponent(txt,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE)
                                .addGap(5,5,10)
                                .addComponent(op[0],GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                                .addComponent(op[1],GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                                .addComponent(op[2],GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                                .addComponent(op[3],GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        )
                        .addGap(5,5,20)
        );
    }

    public smallQuestion(String question, HashMap<String, Integer> selections) {
        Question = question;
        this.selections = selections;
        txt= new JLabel(Question);
        txt.setFont(new Font("Times New Roman",Font.BOLD,20));
        txt.setAlignmentX(LEFT_ALIGNMENT);

        gr= new ButtonGroup();
        op= new JRadioButton[4];
        AtomicInteger i= new AtomicInteger();
        selections.forEach((k,v)->{
            op[i.get()]=new JRadioButton(k);
            gr.add(op[i.get()]);
            add(op[i.get()]);
            i.getAndIncrement();
        });
        GroupLayout layout= new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(txt)
                        .addComponent(op[0])
                        .addComponent(op[1])
                        .addComponent(op[2])
                        .addComponent(op[3])

        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(txt)
                                .addGap(5,5,10)
                                .addComponent(op[0])
                                .addComponent(op[1])
                                .addComponent(op[2])
                                .addComponent(op[3])
                        )
                        .addGap(7,7,20)
        );
    }


    public static void main(String[] args) {
        JFrame f= new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(700,700);
        HashMap<String,Integer> h= new HashMap<>();
        h.put("Hello",0);h.put("hi",0); h.put("no",0); h.put("yes",1);

        smallQuestion q= new smallQuestion("are you ok?",h,"C:\\Audios\\AudiosForQuestion\\q15.MP3");
        f.add(q);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
