package Res;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
            "values (%d,N'%s', '%s','%s')";
    private String registerForTeacher="insert into TeacherAccount (Account, Password)" +"\n"+
            " values ('%s', '%s');"+"\n"+
            "insert into Teacher (TeacherID,TeacherName, Email, BirthDate) "+"\n"+
            "values (%d,N'%s', '%s', '%s')";
    private String loginStudent="select Account,Password from StudentAccount where Account='%s'";
    private String loginTeacher="select Account,Password from TeacherAccount where Account='%s'";
    private String sendStudent="select Email, Password\n" +
            "from Student join StudentAccount on StudentAccount.StudentID= Student.StudentId\n" +
            "where Account='%s' or Email='%s'";
    private String sendTeacher="select Email,Password\n" +
            "from Teacher join TeacherAccount on TeacherAccount.TeacherID= Teacher.TeacherId\n" +
            "where Account='%s' or Email='%s'";
    public SQLQueries() {
        String connectionS = null;
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("ConnectionToSQL1.dat");
            if (resourceUrl == null) {
                throw new FileNotFoundException("Resource 'ConnectionToSQL1.dat' not found.");
            }
            try (InputStream in = resourceUrl.openStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                ByteArrayOutputStream byteCollector = new ByteArrayOutputStream();

                while ((bytesRead = in.read(buffer)) != -1) {
                    byteCollector.write(buffer, 0, bytesRead);
                }
                byte[] fullBytes = byteCollector.toByteArray();
                 connectionS  = new String(fullBytes, "UTF-8");
                 connectionS=connectionS.substring(7);
            }
            System.out.println(connectionS);
             this.c=DriverManager.getConnection(connectionS);
            st=c.createStatement();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public String[] getTeacherMail(String Acc){
        try {
            String txt=String.format(sendTeacher,Acc,Acc);
            String[] tmp= new String[2];
            ResultSet res= st.executeQuery(txt);
            if(!res.next()){
                tmp[0]="no email";
                return tmp;
            }
            ResultSet res1= st.executeQuery(txt);
            while(res1.next()){
                tmp[0]=res1.getNString("Email");
                tmp[1]=res1.getNString("Password");
                return tmp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String[] getStudentMail(String Acc){
        try {
            String txt=String.format(sendStudent,Acc,Acc);
            String[] tmp= new String[2];
            ResultSet res= st.executeQuery(txt);
            if(!res.next()){
                tmp[0]="no email";
                return tmp;
            }
            ResultSet res1= st.executeQuery(txt);
            while(res1.next()){
                tmp[0]=res1.getNString("Email");
                tmp[1]=res1.getNString("Password");
                return tmp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    public String LoginStudent(String Acc){
        try {
            ResultSet res= st.executeQuery(String.format(loginStudent,Acc));
            if(!res.next()){
                return "no acc";
            }
            ResultSet res1= st.executeQuery(String.format(loginStudent,Acc));
            while(res1.next()){
                return res1.getNString("Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String LoginTeacher(String Acc){
        try {
            ResultSet res= st.executeQuery(String.format(loginTeacher,Acc));
            if(!res.next()){
                return "no acc";
            }
            ResultSet res1= st.executeQuery(String.format(loginTeacher,Acc));
            while(res1.next()){
                return res1.getNString("Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        SQLQueries s=new SQLQueries();
        ResultSet res= s.Search("hello");
        while(res.next()){
            System.out.println(res.getNString("Word"));
        }
    }
}
