package Res;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLQueries {
    private Connection c=null;
    private Statement sta=null;
    private String getScore="select Score from StudentProgress where StudentID=%d and AssignmentId=%d;";
    private String score="delete StudentProgress" +
            " where  AssignmentID=%d and StudentID=%d;" +
            " insert into StudentProgress(AssignmentID,StudentID,Score)" +
            " values (%d,%d,%.2f)";
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
    private String findNumberofBQ="select * \n" +
            "from BigQuestion \n" +
            "where AssignmentID=%d\n";
   private String getAudioForBQ="select FilePath" +
           " from AudioForBigQuestion " +
           "where BQuestionID=%d";
    private String getSelection="select * from Selection Where QuestionID=%d";
    private String GetQRes="select AudioForQuestion.FilePath,QuestionText,Question.QuestionID \n" +
            "from Question left join AudioForQuestion on Question.QuestionID=AudioForQuestion.QuestionID\n" +
            "left join BigQuestion on BigQuestion.BQuestionID=Question.BQuestionID\n" +
            "where BigQuestion.BQuestionID=%d";
    private String GetallBQ="select BigQuestion.BQuestionID,AudioForBigQuestion.FilePath \n" +
            "from BigQuestion \n" +
            "left join AudioForBigQuestion on AudioForBigQuestion.BQuestionID=BigQuestion.BQuestionID \n" +
            "where AssignmentID=%d\n";

   public ResultSet getallBQ(int AssignmentID){
       try {
           PreparedStatement st= c.prepareStatement(String.format(GetallBQ,AssignmentID));
           ResultSet res= st.executeQuery();
          return res;
       } catch (SQLException e) {

       }
       return null;
   }
   public float getScore(int as, int stu){
       try {
           ResultSet res =sta.executeQuery(String.format(getScore,stu,as));
           while(res.next()){
               return res.getFloat("Score");
           }
       } catch (SQLException e) {

       }
       return 0;
   }
   public boolean updateProgress(int as, int stu,float sc){
       try {


           PreparedStatement st= c.prepareStatement(String.format(score,as,stu,as,stu,sc));
           ResultSet res =sta.executeQuery(String.format(getScore,stu,as));
           while(res.next()){
               int score=0;
               score=res.getInt("Score");
               if(score>=sc) return false;
           }
           st.executeUpdate();
           return true;
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;
   }
    public ArrayList<String> getBQ(int AssignmentID){
        ArrayList<String> tmp= new ArrayList<>();
        try {
            PreparedStatement st= c.prepareStatement(String.format(findNumberofBQ,AssignmentID));
            ResultSet res= st.executeQuery();
            while(res.next()){
                tmp.add(res.getNString("BQuestionText"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }
    public HashMap<String,Integer> getSelectionForQuestion(int QID){
        HashMap<String,Integer> tmp= new HashMap<>();
        try {
            PreparedStatement st= c.prepareStatement(String.format(getSelection,QID));

            ResultSet res=st.executeQuery();

            while(res.next()){
                tmp.put(res.getNString("SelectionText"),res.getInt("TrueFalse"));
            }
        } catch (SQLException e) {

        }
        return tmp;
    }
    public ResultSet getQRes(int BQID){
        try {

            PreparedStatement st= c.prepareStatement(String.format(GetQRes,BQID));

            ResultSet res= st.executeQuery();

            if(!res.next()) return null;
            return st.executeQuery();
        } catch (SQLException e) {

        }
        return null;
    }

    public SQLQueries() {
        String connectionS;
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("ConnectionToSQL.dat");
            if (resourceUrl == null) {
                throw new FileNotFoundException("Resource 'ConnectionToSQL.dat' not found.");
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

             this.c=DriverManager.getConnection(connectionS);
            this.sta= c.createStatement();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public String[] getTeacherMail(String Acc){
        try {
            String txt=String.format(sendTeacher,Acc,Acc);
            String[] tmp= new String[2];
            PreparedStatement st= c.prepareStatement(txt);

            ResultSet res= st.executeQuery();
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
            ResultSet res= sta.executeQuery(txt);
            if(!res.next()){
                tmp[0]="no email";
                return tmp;
            }
            ResultSet res1= sta.executeQuery(txt);
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
            res=sta.executeQuery(text);
        } catch (SQLException e) {

        }
        return res;
    }
    public String LoginStudent(String Acc){
        try {
            ResultSet res= sta.executeQuery(String.format(loginStudent,Acc));
            if(!res.next()){
                return "no acc";
            }
            ResultSet res1= sta.executeQuery(String.format(loginStudent,Acc));
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
            ResultSet res= sta.executeQuery(String.format(loginTeacher,Acc));
            if(!res.next()){
                return "no acc";
            }
            ResultSet res1= sta.executeQuery(String.format(loginTeacher,Acc));
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
            ResultSet res= sta.executeQuery("select top 1 TeacherID from Teacher order by TeacherID DESC");
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
            ResultSet res= sta.executeQuery("select top 1 StudentID from Student order by StudentID DESC");
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
        ResultSet res= s.getQRes(1);
        while(res.next()){
            System.out.println(res.getNString("QuestionText")+res.getInt("QuestionID")+res.getNString("FilePath"));
        }
    }
}
