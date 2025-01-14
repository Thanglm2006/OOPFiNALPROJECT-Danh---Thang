package Component.PanelPrototype;
import javax.swing.*;
import java.awt.*;
import Object.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class smallQuestion extends JPanel {
    private String Question;
    private ArrayList<Pair<String,Integer>> selections;
    private String AudioString;
    private AudioBar audiobar;
    private JRadioButton[] op;
    private ButtonGroup gr;
    private JLabel txt;
    private ImageIcon im;
    public int getSore(){
        int sc=0;
        for(int i=0;i<4;i++){
            if(op[i].isSelected()){
                for(Pair<String,Integer> x:selections){
                    if(x.getKey().equals(op[i].getText())&&x.getValue()==1){
                        sc++;
                        break;
                    }
                }
            }
        }
        return sc;
    }
    private String wrapText(String text, int maxLineLength) {
        StringBuilder wrappedText = new StringBuilder();
        wrappedText.append("<html>");
        String[] words = text.split(" ");
        int currentLength = 0;
        for (String word : words) {
            if (currentLength + word.length() > maxLineLength) {
                wrappedText.append("<br>");
                wrappedText.append("&nbsp;");
                currentLength = 1;
            }
            wrappedText.append(word).append(" ");
            currentLength += word.length() + 1;
        }
        wrappedText.append("</html>");
        return wrappedText.toString().trim();
    }
    public smallQuestion(String question,ArrayList<Pair<String,Integer>> selections, String audioString) {
        setBackground(new Color(255,255,255));
        AudioString=audioString;
        audiobar= new AudioBar(1200,AudioString);
        Question = question;
        this.selections = selections;
        AudioString = audioString;
        txt= new JLabel(wrapText(Question, 110));
        txt.setFont(new Font("Milford",Font.BOLD,20));
        txt.setAlignmentX(LEFT_ALIGNMENT);

        gr= new ButtonGroup();
        op= new JRadioButton[4];
        AtomicInteger i= new AtomicInteger();
        selections.forEach(p->{
            op[i.get()]=new JRadioButton(p.getKey());
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

    public smallQuestion(String question, ArrayList<Pair<String,Integer>> selections) {
        setBackground(new Color(255,255,255));
        Question = question;
        this.selections = selections;
        txt= new JLabel(wrapText(Question, 110));
        txt.setFont(new Font("Times New Roman",Font.BOLD,20));
        txt.setAlignmentX(LEFT_ALIGNMENT);

        gr= new ButtonGroup();
        op= new JRadioButton[4];
        AtomicInteger i= new AtomicInteger();
        selections.forEach(p->{
            op[i.get()]=new JRadioButton(p.getKey());
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

}
