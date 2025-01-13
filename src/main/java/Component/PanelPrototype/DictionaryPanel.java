package Component.PanelPrototype;

import Component.form.MainForm;
import Res.SQLQueries;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Res.MP3Mplayer.playAudio;

public class DictionaryPanel extends JPanel{
    private JScrollPane cr;
    private static JTextField searchBar;
    static SQLQueries sql;
    static JList<Item> searchDatas;
    private MainForm main;
    static DefaultListModel<Item> Mlist;
    private JPanel sP;
    private static ImageIcon ic;
    private JRadioButton tv;
    static Image img;
    public DictionaryPanel(int w, int h, SQLQueries sql,MainForm main) {
        this.main=main;
        ImageIcon i1 = new ImageIcon(DictionaryPanel.class.getResource("/volumeIcon.png"));
        img = i1.getImage(); // Get the original image
        img = img.getScaledInstance(20, 20, Image.SCALE_FAST);
        ic= new ImageIcon(img);
        this.sql=sql;
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
                        playAudio(item.FilePath);
                    }
                }
            }
        });

        cr= new JScrollPane(searchDatas);
        cr.setSize(900,getHeight()-100);
        cr.setVisible(true);
        cr.setEnabled(true);
        JButton ok= new JButton("Tìm Kiếm");
        ok.setFont(new Font("Times New Roman",Font.PLAIN,10));
        ok.setSize(100,40);
        ok.setLocation(w-400,0);
        ok.addActionListener(e ->{
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
           SwingWorker<Void,Void> worker= new SwingWorker<>() {
               @Override
               protected Void doInBackground() throws Exception {
                   try{
                       search();
                   } catch (SQLException ex) {

                   }
                   return null;
               }

               @Override
               protected void done() {
                     setCursor(Cursor.getDefaultCursor());
                     main.showForm(DictionaryPanel.this);
                     searchBar.grabFocus();
                     System.out.println("Done");
               }
           };
           worker.execute();
        });
        searchBar = new JTextField("Từ điển");
        searchBar.setForeground(Color.gray);
        searchBar.setSize(w-400, 40);
        searchBar.setLocation(0,0);
        searchBar.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (searchBar.getText().equalsIgnoreCase("Từ điển")) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                }else if(e.getKeyChar()==KeyEvent.VK_ENTER){
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    SwingWorker<Void,Void> worker= new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            try{
                                search();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            return null;
                        }
                        @Override
                        protected void done() {
                            setCursor(Cursor.getDefaultCursor());
                            main.showForm(DictionaryPanel.this);
                            searchBar.grabFocus();
                            System.out.println("Done");
                        }
                    };
                    worker.execute();
                }
            }
        });
        tv=new JRadioButton("Tìm kiếm bằng Tiếng Việt");
        tv.setFont(new Font("Times New Roman",Font.PLAIN,12));
        tv.setSize(300,40);
        tv.setLocation(w-300,0);
        setLayout(new BorderLayout());
        sP= new JPanel();
        sP.setLayout(null);
        sP.setPreferredSize(new Dimension(w,40));
        sP.add(searchBar);
        sP.add(ok);
        sP.add(tv);
        setSize(900,h);
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
        ResultSet res1;
        if(!tv.isSelected())  res1 =sql.Search(txt);
        else{
            res1=sql.Search2(txt);
        }

        if(!res1.next()){
            Mlist.addElement(new Item("NotFound!","","","",""));
        }
        ResultSet res=null;
        if(!tv.isSelected())  res =sql.Search(txt);
        else{
            res=sql.Search2(txt);
        }
            try {
                while (res.next()) {
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
            setBackground(new Color(200,255,255));
            setOpaque(true);
            return this;
        }
    }

    public static void main(String[] args) {
        DictionaryPanel se;
        JFrame f= new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setSize(1280,700);
        f.setLocationRelativeTo(null);
        se= new DictionaryPanel(900,700, new SQLQueries(),new MainForm());
        f.add(se);
        f.setVisible(true);
    }
}
