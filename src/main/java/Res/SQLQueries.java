package Res;
import java.io.*;
import java.sql.*;
public class SQLQueries {
    Connection c=null;
    Statement st=null;
    String s;
    String search="select Word,Pronunciation,FilePath,Meaning,CategoryName From AudioForVocab" +"\n"+
            " join Vocabulary on Vocabulary.WordID=AudioForVocab.WordID" +"\n"+
            " where Vocabulary.Word like '%s' or Vocabulary.Meaning like '%s';";
    public SQLQueries() {
        FileInputStream f=null;
        ObjectInputStream ois=null;
        try {
             f= new FileInputStream(getClass().getClassLoader().getResource("Connection.dat").getPath());
             ois= new ObjectInputStream(f);
             s =(String) ois.readObject();
             this.c=DriverManager.getConnection(s);
        } catch (IOException | ClassNotFoundException | SQLException e) {

        } finally{
            if(f!=null) {
                try {
                    f.close();
                } catch (IOException e) {

                }
            }
            if(ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {

                }
            }
        }
    }


    public ResultSet  Search(String txt){
        ResultSet res=null;
        try {
            st=c.createStatement();
            String text= String.format(search,txt+"%",txt+"%");
            res=st.executeQuery(text);
        } catch (SQLException e) {

        }
        return res;
    }

    public static void main(String[] args) throws SQLException {
        SQLQueries a= new SQLQueries();
        ResultSet res=a.Search("xin chào");
        while(res.next()){
            System.out.println(res.getNString("Word"));
        }
    }
}
