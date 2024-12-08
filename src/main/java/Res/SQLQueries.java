package Res;
import java.io.*;
import java.sql.*;
public class SQLQueries {
    private Connection c=null;
    private Statement st=null;
    private String s;
    private String search="select Word,Pronunciation,FilePath,Meaning,CategoryName From AudioForVocab" +"\n"+
            " join Vocabulary on Vocabulary.WordID=AudioForVocab.WordID" +"\n"+
            " where Vocabulary.Word like '%s' or Vocabulary.Meaning like '%s';";
    private String registerForStudent="insert into StudentAccount (Account, Password)" +"\n"+
            " values ('%s', '%s');"+"\n"+
            "insert into Student (StudentID,StudentName, Email, BirthDate) "+"\n"+
            "values (%d,'%s', '%s','%s')";
    private String registerForTeacher="insert into TeacherAccount (Account, Password)" +"\n"+
            " values ('%s', '%s');"+"\n"+
            "insert into Teacher (TeacherID,TeacherName, Email, BirthDate) "+"\n"+
            "values (%d,'%s', '%s', '%s')";
    public SQLQueries() {
        FileInputStream f=null;
        ObjectInputStream ois=null;
        try {
             f= new FileInputStream(getClass().getClassLoader().getResource("Connection.dat").getPath());
             ois= new ObjectInputStream(f);
             s =(String) ois.readObject();
             this.c=DriverManager.getConnection(s);
            st=c.createStatement();
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
            String text= String.format(search,txt+"%",txt+"%");
            res=st.executeQuery(text);
        } catch (SQLException e) {

        }
        return res;
    }
    public boolean insertTeacher(String acc, String pass, String name, String email, String birth){
        int id=0;
        try {
            ResultSet res= st.executeQuery("select top 1 TeacherID from Teacher order by TeacherID DESC");
            while(res.next()){
                id=res.getInt("TeacherID");
            }
        } catch (SQLException e) {

        }
        id++;
        String txt= String.format(registerForTeacher,acc,pass,id,name,email, birth);
        try {
            PreparedStatement pp= c.prepareStatement(txt);
            pp.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean insertStudent(String acc, String pass, String name, String email, String birth){
        int id=0;
        try {
            ResultSet res= st.executeQuery("select top 1 StudentID from Student order by StudentID DESC");
            while(res.next()){
                id=res.getInt("StudentID");
            }
        } catch (SQLException e) {

        }
        id++;
        String txt= String.format(registerForStudent,acc,pass,id,name,email, birth);
        try {
            PreparedStatement pp= c.prepareStatement(txt);
            pp.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void main(String[] args) throws SQLException {
    }
}
