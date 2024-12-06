package GUI;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static Res.MP3Mplayer.playAudio;

public class DictionaryFrame extends JPanel{
    private JScrollPane cr;
    private static JTextField searchBar;
    static SQLQueries sql;
    static JList<Item> searchDatas;
    static DefaultListModel<Item> Mlist;
    private JPanel sP;
    private static ImageIcon ic;
    static Image img;
    public DictionaryFrame() {
        ImageIcon i1 = new ImageIcon(DictionaryFrame.class.getResource("/volumeIcon.png"));
        img = i1.getImage(); // Get the original image
        img = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ic= new ImageIcon(img);
        sql= new SQLQueries();

        Mlist= new DefaultListModel<>();
        searchDatas= new JList<>(Mlist);
        searchDatas.setCellRenderer(new Render());
        searchDatas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = searchDatas.locationToIndex(e.getPoint());
                if (index != -1) {
                    Rectangle cellBounds = searchDatas.getCellBounds(index, index);
                    Point clickPoint = e.getPoint();


                    if (cellBounds.contains(clickPoint)) {
                        Item item = Mlist.getElementAt(index);
                        System.out.println(item.Word);
                        System.out.println(item.FilePath);
                        playAudio(item.FilePath);
                    }
                }
            }
        });

        cr= new JScrollPane(searchDatas);
        cr.setSize(900,600);
        cr.setVisible(true);
        cr.setBorder(new EtchedBorder());
        cr.setRowHeader(new JViewport());
        cr.setEnabled(true);
        JButton ok= new JButton("Tìm Kiếm");
        ok.setFont(new Font("Times New Roman",Font.PLAIN,10));
        ok.setSize(100,30);
        ok.setLocation(800,0);
        ok.addActionListener(e ->{
            try{
                search();
            } catch (SQLException ex) {

            }
        });
        searchBar = new JTextField("Từ điển");
        searchBar.setForeground(Color.gray);
        searchBar.setSize(800, 30);
        searchBar.setLocation(0,0);
        searchBar.setFont(new Font("Times New Roman", Font.PLAIN, 27));
        searchBar.addActionListener(e -> {
            cr.setVisible(true);
        });
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (searchBar.getText().equalsIgnoreCase("Từ điển")) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                }else if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    try{
                        search();
                    } catch (SQLException ex) {

                    }
                }
            }
        });

        setLayout(new BorderLayout());
        sP= new JPanel();
        sP.setLayout(null);
        sP.setPreferredSize(new Dimension(900,30));
        sP.add(searchBar);
        sP.add(ok);
        setSize(900,700);
        setLocation(140,0);
        add(sP,BorderLayout.NORTH);
        add(cr,BorderLayout.CENTER);
    }
    public class Item{
        private String Word,Pronunciation,Meaning,FilePath,Cate;

        public Item(String word, String pronunciation, String meaning,String FilePath,String CategoryName) {
            Word = word;
            Pronunciation = pronunciation;
            Meaning = meaning;
            this.FilePath=FilePath;
            Cate=CategoryName;
        }

        @Override
        public String toString() {
            return
                    Word +"\n"+
                   Pronunciation + '\'' +
                     Meaning + '\''
                    ;
        }
    }

    public void search() throws SQLException {
        Mlist.clear();
        String txt=searchBar.getText();
        while(txt.charAt(txt.length()-1)==' '){
            txt=txt.substring(0,txt.length()-1);
        }
        while(txt.charAt(0)==' '){
            txt=txt.substring(1);
        }
        ResultSet res1 =sql.Search(txt);
        if(!res1.next()){
            Mlist.addElement(new Item("NotFound!","","","",""));
        }
        ResultSet res= sql.Search(txt);
            try {
                while (res.next()) {
                    System.out.println(res.getNString("Word"));
                    Item tmp = new Item(res.getNString("Word"), res.getNString("Pronunciation"), res.getNString("Meaning"), res.getNString("FilePath"),res.getNString("CategoryName"));
                    Mlist.addElement(tmp);
                }
            } catch (SQLException e) {

            }

    }
    static class Render extends JPanel implements ListCellRenderer<Item>{
        private JLabel Word, Pronunciation, Meaning;
        private JButton Au;
        public Render takeRender(){
            return this;
        }
        public JButton takeAu(){
            return Au;
        }
        public Render() {
            setLayout( new BorderLayout());
            Au= new JButton(ic);
            Au.setBounds(0,0,20,20);
            Word =new JLabel("");
            Word.setFont(new Font("Arial",Font.BOLD,20));

            Pronunciation = new JLabel("");
            Pronunciation.setFont(new Font("Arial",Font.PLAIN,12));
            Pronunciation.setForeground(Color.gray);

            Meaning = new JLabel("");
            Meaning.setFont(new Font("Arial",Font.PLAIN,19));

            setBorder(new LineBorder(Color.DARK_GRAY));
            add(Au,BorderLayout.EAST);
            add(Word,BorderLayout.NORTH);
            add(Pronunciation,BorderLayout.CENTER);
            add(Meaning,BorderLayout.SOUTH);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index, boolean isSelected, boolean cellHasFocus) {
            Word.setText(value.Word+"   ("+value.Cate+")");
            Pronunciation.setText(value.Pronunciation);
            Meaning.setText(value.Meaning);
            if(value.FilePath.equalsIgnoreCase("")) Au.setVisible(false);
            else{
                Au.setVisible(true);
            }
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setOpaque(true);
            return this;
        }
    }

    public static void main(String[] args) {
        DictionaryFrame se;
        JFrame f= new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setSize(1280,700);
        f.setLocationRelativeTo(null);
        se= new DictionaryFrame();
        f.add(se);
        f.setVisible(true);
    }
}
