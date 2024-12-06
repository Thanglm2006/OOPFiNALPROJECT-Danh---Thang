

import java.io.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import static Res.MP3Mplayer.playAudio;

public class TestTakingDataFromSQLServer {
    Connection c= null;
    FileInputStream fis=null;
    public TestTakingDataFromSQLServer() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Connection.txt");
            BufferedReader r= new BufferedReader(new InputStreamReader(inputStream));
            String connection_String= r.readLine();
            System.out.println(connection_String);
            c=DriverManager.getConnection(connection_String);
            Statement s=c.createStatement();
            ResultSet res= s.executeQuery("select FilePath,Meaning From AudioForVocab" +"\n"+
                    " join Vocabulary on Vocabulary.WordID=AudioForVocab.WordID" +"\n"+
                    " where Vocabulary.Word like 'hello';");
           while(res.next()) {
               String sf = res.getNString("FilePath");
               System.out.printf("%s %n %s %n",sf, res.getNString("Meaning"));
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

    public static void main(String[] args) {
        new TestTakingDataFromSQLServer();
    }

}
