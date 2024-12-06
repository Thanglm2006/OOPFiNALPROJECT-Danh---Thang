package GUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
public class SQLQueries {
    Connection c=null;
    Statement st=null;
    String search="select Word,Pronunciation,FilePath,Meaning From AudioForVocab" +"\n"+
            " join Vocabulary on Vocabulary.WordID=AudioForVocab.WordID" +"\n"+
            " where Vocabulary.Word like ";
    public SQLQueries() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Connection.txt");
        BufferedReader r= new BufferedReader(new InputStreamReader(inputStream));
        String connection_String= null;
        try {
            connection_String = r.readLine();
            System.out.println(connection_String);
            c= DriverManager.getConnection(connection_String);
        } catch (IOException | SQLException e) {

        }

    }
    public ResultSet  Search(String txt){
        ResultSet res=null;
        try {
            st=c.createStatement();
            res=st.executeQuery(search+"'"+txt+"%';");
        } catch (SQLException e) {

        }
        return res;
    }

    public static void main(String[] args) throws SQLException {
        SQLQueries a= new SQLQueries();
        ResultSet res=a.Search("hello");
        while(res.next()){
            System.out.println(res.getNString("Word"));
        }
    }
}
