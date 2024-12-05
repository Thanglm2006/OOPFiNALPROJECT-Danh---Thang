
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;


public class TestTakingDataFromSQLServer {
    Connection c= null;
    FileInputStream fis=null;
    public TestTakingDataFromSQLServer() {
        try {
            File f= new File("src/Res/Connection.txt");
            f.getParentFile().mkdirs();
            fis= new FileInputStream(f);
            BufferedReader r= new BufferedReader(new FileReader(f));
            String connection_String= r.readLine();
            System.out.println(connection_String);
            c=DriverManager.getConnection(connection_String);
            Statement s=c.createStatement();
            ResultSet res= s.executeQuery("select FilePath From AudioForVocab" +"\n"+
                    " join Vocabulary on Vocabulary.WordID=AudioForVocab.WordID" +"\n"+
                    " where Vocabulary.Word='mother';");
           while(res.next()) {
               String sf = res.getNString("FilePath");
               System.out.println(sf);
               playAudio(sf);
           }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally{

            if(fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {

                }
            }
            if(c!=null)try {
                c.close();
            } catch (SQLException e) {

            }
        }

    }
    public void playAudio(String sf) {
        Player p = null;
        try {
            p = new Player(new FileInputStream(sf));
            p.play();
        } catch (JavaLayerException | FileNotFoundException e) {

            System.out.println("error!");
        }
    }
    public static void main(String[] args) {
        new TestTakingDataFromSQLServer();
    }

}
