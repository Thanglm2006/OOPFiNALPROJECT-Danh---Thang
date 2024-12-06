package GUI;

import javax.swing.*;

public class main extends JFrame {
    DictionaryFrame se;
    public main(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setSize(1280,700);
        setLocationRelativeTo(null);
        se= new DictionaryFrame();
        add(se);
        setVisible(true);

    }

    public static void main(String[] args) {
        new main();
    }
}
