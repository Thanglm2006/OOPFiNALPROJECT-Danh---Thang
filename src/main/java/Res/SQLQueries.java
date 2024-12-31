package Res;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import Object.Student;
public class SQLQueries {
    private Connection c=null;
    private Statement sta=null;
    private String getSTID="select StudentID from StudentAccount where Account='%s'";
    private String getTID="select TeacherID from TeacherAccount where Account='%s'";
    private String getAllAssignmentOfAStudent="select assignment.AssignmentID,assignment.AssignmentName\n" +
            "from Student student\n" +
            "join ClassAndStudent classStudent on classStudent.StudentID=student.StudentID\n" +
            "join ClassAssignment classAssignment on classAssignment.ClassID=classStudent.ClassID\n" +
            "join Assignment assignment on assignment.AssignmentID=classAssignment.AssignmentID\n" +
            "where student.StudentID=%d";
    private String getScore="select Score from StudentProgress where StudentID=%d and AssignmentId=%d;";
    private String result="select  ass.AssignmentName,pr.Score\n" +
            "from StudentProgress pr \n" +
            "join Assignment ass on ass.AssignmentID=pr.AssignmentID \n" +
            "join Student st on st.StudentID=pr.StudentID\n" +
            "join ClassAndStudent clt on clt.StudentID=st.StudentID\n" +
            "where st.StudentID =%d and pr.Score>0.0";
    private String score="delete StudentProgress" +
            " where  AssignmentID=%d and StudentID=%d;" +
            " insert into StudentProgress(AssignmentID,StudentID,Score)" +
            " values (%d,%d,%.2f)";
    private String search="select Top 200 Word,Pronunciation,FilePath,Meaning,CategoryName From AudioForVocab" +"\n"+
            " join Vocabulary on Vocabulary.WordID=AudioForVocab.WordID" +"\n"+
            " where Vocabulary.Word like '%s';";
    private String search2="select Top 200 Word,Pronunciation,FilePath,Meaning,CategoryName From AudioForVocab" +"\n"+
            " join Vocabulary on Vocabulary.WordID=AudioForVocab.WordID" +"\n"+
            " where Vocabulary.Meaning like '%s';";
    private String registerForStudent="insert into StudentAccount (Account, Password)" +"\n"+
            " values ('%s', '%s');"+"\n"+
            "insert into Student (StudentID,StudentName,Gender, Email, BirthDate) "+"\n"+
            "values (%d,N'%s', N'%s','%s','%s')";
    private String registerForTeacher="insert into TeacherAccount (Account, Password)" +"\n"+
            " values ('%s', '%s');"+"\n"+
            "insert into Teacher (TeacherID,TeacherName,Gender, Email, BirthDate) "+"\n"+
            "values (%d,N'%s', N'%s', '%s', '%s')";
    private String loginStudent="select StudentAccount.StudentID,Account,Password,StudentName\n" +
            "from StudentAccount join Student on Student.StudentID=StudentAccount.StudentID\n" +
            "where StudentAccount.Account='%s'";
    private String loginTeacher="select TeacherAccount.TeacherID,Account,Password,TeacherName\n" +
            "from TeacherAccount join Teacher on Teacher.TeacherID=TeacherAccount.TeacherID\n" +
            "where TeacherAccount.Account='%s'";
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
    private String getAllAss="select * from Assignment where  Teacher=%d";
    private String getNotFinish="SELECT ca.AssignmentID\n" +
            "FROM ClassAssignment ca\n" +
            "JOIN ClassAndStudent cas ON ca.ClassID = cas.ClassID\n" +
            "LEFT JOIN StudentProgress sp ON ca.AssignmentID = sp.AssignmentID AND cas.StudentID = sp.StudentID\n" +
            "WHERE cas.StudentID = %d\n" +
            "  AND sp.AssignmentID IS NULL;";
    private String allStudent="select \tstudent.StudentName,\n" +
            "\tstudent.StudentID,\n" +
            "\tround(sum(stp.Score),2) as score\n" +
            "from \n" +
            "\t\tStudent student\n" +
            "join\tStudentProgress stp on stp.StudentID=student.StudentID\n" +
            "join ClassAndStudent cls on cls.StudentID=student.StudentID\n" +
            "where exists (select * from ClassAndStudent where StudentID=%d)\n" +
            "group by student.StudentName,student.StudentID,stp.completionDate\n" +
            "order by score desc,\n" +
            "stp.completionDate  ";
    private String searchSTBySTName="select student.StudentID, student.StudentName, student.Gender, student.Email, student.BirthDate, class.ClassName\n" +
            "from Student student\n" +
            "join ClassAndStudent cls on student.StudentId=cls.StudentID\n" +
            "join Class class on cls.ClassID=class.ClassID\n" +
            "where student.StudentName like '%s' and class.ClassID=%d";
    private String searchSTByEmail="select student.StudentID, student.StudentName, student.Gender, student.Email, student.BirthDate, class.ClassName\n" +
            "from Student student\n" +
            "join ClassAndStudent cls on student.StudentId=cls.StudentID\n" +
            "join Class class on cls.ClassID=class.ClassID\n" +
            "where student.Email like '%s' and class.ClassID=%d";
    private String searchSTByGender="select student.StudentID, student.StudentName, student.Gender, student.Email, student.BirthDate, class.ClassName\n" +
            "from Student student\n" +
            "join ClassAndStudent cls on student.StudentId=cls.StudentID\n" +
            "join Class class on cls.ClassID=class.ClassID\n" +
            "where student.Gender like '%s' and class.ClassID=%d";
    private String searchSTBySTID="select student.StudentID, student.StudentName, student.Gender, student.Email, student.BirthDate, class.ClassName\n" +
            "from Student student\n" +
            "join ClassAndStudent cls on student.StudentId=cls.StudentID\n" +
            "join Class class on cls.ClassID=class.ClassID\n" +
            "where student.StudentID like '%d' and class.ClassID=%d";
    private String getAllStudent="select student.StudentID, student.StudentName, student.Gender, student.Email, student.BirthDate, class.ClassName\n" +
            "from Student student\n" +
            "join ClassAndStudent cls on student.StudentId=cls.StudentID\n" +
            "join Class class on cls.ClassID=class.ClassID\n" +
            "where class.TeacherID=%d and class.classID=%d";
    private String TInfor="select * from Teacher join TeacherAccount on TeacherAccount.TeacherID= Teacher.TeacherID where Teacher.TeacherID=%d;";

    private String stInfor="select * from Student join StudentAccount on StudentAccount.StudentID= Student.StudentID where Student.StudentID=%d;";
    private String resetPassST="update StudentAccount" +
            " set PassWord='%s' where StudentID=%d";
    private String resetPassTe="update TeacherAccount" +
            " set PassWord='%s' where TeacherID=%d";
    private String saveAssignment="insert into Assignment(AssignmentName,Teacher) values(N'%s',%d)";
    private String saveBigQuestion="insert into BigQuestion(AssignmentID,BQuestionText) values(%d,N'%s')";
    private String saveSmallQuestion="insert into Question(BQuestionID,QuestionText) values(%d,N'%s')";
    private String saveAudioForBQ="insert into AudioForBigQuestion(BQuestionID,FilePath) values(%d,N'%s')";
    private String saveAudioForQ="insert into AudioForQuestion(QuestionID,FilePath) values(%d,N'%s')";
    private String saveSelection="insert into Selection(QuestionID,SelectionText,TrueFalse) values(%d,N'%s',%d)";
    private String updateSTEmail="update Student set Email='%s' where StudentID=%d";
    private String updateTEmail="update Teacher set Email='%s' where TeacherID=%d";
    private String getAminMail="select Email,Pass from AdminMail";
    private String getBID="SELECT IDENT_CURRENT('BigQuestion') + 1 AS next; ";
    private String getAssID="SELECT IDENT_CURRENT('Assignment') + 1 AS next; ";//zjll birb smye avqh
    private String getSMID="SELECT IDENT_CURRENT('Question') + 1 AS next; ";
    private String getClass="select * from Class where TeacherID=%d";
    private String insertClass="insert into Class(ClassName,TeacherID) values(N'%s',%d)";
    private String deleteStudentOutOfCLass="delete ClassAndStudent where StudentID=%d and ClassID=%d";
    private String insertStudentIntoClass="insert into ClassAndStudent(StudentID,ClassID) values(%d,%d)";
    private String getAllStudentAcc="select * from StudentAccount";
    private String getAllTeacherAcc="select * from TeacherAccount";
    private String ChangeSTPass="update StudentAccount set Password='%s' where StudentID=%d";
    private String ChangeTPass="update TeacherAccount set Password='%s' where TeacherID=%d";
    private String deleteAss="delete Assignment where AssignmentID=%d";
    private String updateAss="update Assignment set AssignmentName=N'%s' where AssignmentID=%d";
    private String insertAssToClass="insert into ClassAssignment(AssignmentID,ClassID) values(%d,%d)";
    public int getSTID(String Acc){
        try {
            PreparedStatement st= c.prepareStatement(String.format(getSTID,Acc));
            ResultSet res= st.executeQuery();
            while(res.next()){
                return res.getInt("StudentID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getTID(String Acc){
        try {
            PreparedStatement st= c.prepareStatement(String.format(getTID,Acc));
            ResultSet res= st.executeQuery();
            while(res.next()){
                return res.getInt("TeacherID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean insertAssToClass(int Ass, int ClassID){
        try {
            PreparedStatement st= c.prepareStatement(String.format(insertAssToClass,Ass,ClassID));
            int c= st.executeUpdate();
            if(c==1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void updateAss(int id, String name){
        try {
            PreparedStatement st= c.prepareStatement(String.format(updateAss,name,id));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAss(int id){
        try {
            PreparedStatement st= c.prepareStatement(String.format(deleteAss,id));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getAllAss(int Teacher){
        try {
            PreparedStatement st= c.prepareStatement(String.format(getAllAss,Teacher));
            return st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void ChangeSTPass(String pass, int id){
        try {
            PreparedStatement st= c.prepareStatement(String.format(ChangeSTPass,pass,id));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void changeTPass(String pass, int id){
        try {
            PreparedStatement st= c.prepareStatement(String.format(ChangeTPass,pass,id));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getAllStudentAcc(){
        try {
            return sta.executeQuery(getAllStudentAcc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getAllTeacherAcc(){
        try {
            return sta.executeQuery(getAllTeacherAcc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteStudentOutOfClass(int Student, int Class){
        try {
            PreparedStatement st= c.prepareStatement(String.format(deleteStudentOutOfCLass,Student,Class));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean insertStudentIntoClass(int Student, int Class){
        int check=0;
        try {
            PreparedStatement st= c.prepareStatement(String.format(insertStudentIntoClass,Student,Class));
            check=st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(check==1){
            return true;
        }
        return false;
    }
    public ArrayList<Student> getAllStudent(int Teacher, int Class){
        try{
            ArrayList<Student> tmp;
            ResultSet res=sta.executeQuery(String.format(getAllStudent,Teacher,Class));
            tmp = new ArrayList<>();
            while(res.next()) {
                Student tmp1=new Student(res.getInt("StudentID"), res.getNString("StudentName"), res.getNString("Gender"),res.getNString("Email"), res.getDate("BirthDate"),res.getNString("ClassName"));
                tmp.add( tmp1);

            }

            return tmp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insertClass(String name, int teacher){
        try {
            PreparedStatement st= c.prepareStatement(String.format(insertClass,name,teacher));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public HashMap<Integer, String> getClass(int id){
        HashMap<Integer,String> tmp= new HashMap<>();
        try {
            PreparedStatement st= c.prepareStatement(String.format(getClass,id));
            ResultSet res= st.executeQuery();
            while(res.next()){
                tmp.put(res.getInt("ClassID"),res.getNString("ClassName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }
    public void saveAssignment(String name, int teacher){
        try {
            PreparedStatement st= c.prepareStatement(String.format(saveAssignment,name,teacher));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveBigQuestion(int AssignmentID, String BQuestionText,String Audio){
       if(Audio!=null){
              try {
                PreparedStatement st= c.prepareStatement(String.format(saveBigQuestion,AssignmentID,BQuestionText));
                st.executeUpdate();
                ResultSet res=sta.executeQuery("SELECT IDENT_CURRENT('BigQuestion') AS next;");
                int BQuestionID=1;
                while(res.next()){
                     BQuestionID=res.getInt("next");
                }
                PreparedStatement st1= c.prepareStatement(String.format(saveAudioForBQ,BQuestionID,Audio));
                st1.executeUpdate();
              } catch (SQLException e) {
                e.printStackTrace();
              }
         }
         else{
              try {
                PreparedStatement st= c.prepareStatement(String.format(saveBigQuestion,AssignmentID,BQuestionText));
                st.executeUpdate();
              } catch (SQLException e) {
                e.printStackTrace();
              }
       }
    }
    public void saveSmallQuestion(int BQuestionID, String QuestionText, String Audio){
        if(Audio!=null){
            try {
                PreparedStatement st= c.prepareStatement(String.format(saveSmallQuestion,BQuestionID,QuestionText));
                st.executeUpdate();
                ResultSet res=sta.executeQuery("SELECT IDENT_CURRENT('Question') AS next;");
                int QuestionID=1;
                while(res.next()){
                    QuestionID=res.getInt("next");
                }
                PreparedStatement st1= c.prepareStatement(String.format(saveAudioForQ,QuestionID,Audio));
                st1.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                PreparedStatement st= c.prepareStatement(String.format(saveSmallQuestion,BQuestionID,QuestionText));
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveSelection(int QuestionID, String SelectionText, int TrueFalse){
        try {
            PreparedStatement st= c.prepareStatement(String.format(saveSelection,QuestionID,SelectionText,TrueFalse));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Student> searchSTByEmail(String Email, int Class) {
        try{
            ArrayList<Student> tmp = null;
            PreparedStatement st= c.prepareStatement(String.format(searchSTByEmail,"%"+Email+"%",Class));
            ResultSet res= st.executeQuery();
            tmp = new ArrayList<>();
            while(res.next()) {

                tmp.add( new Student(res.getInt("StudentID"), res.getNString("StudentName"), res.getNString("Email"), res.getNString("Gender"), res.getDate("BirthDate"),res.getNString("ClassName")));
            }
            return tmp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Student> searchSTBySTID(int id, int Class) {
        try{
            ArrayList<Student> tmp = null;
            PreparedStatement st= c.prepareStatement(String.format(searchSTBySTID,id,Class));
            ResultSet res= st.executeQuery();
            tmp = new ArrayList<>();
            while(res.next()) {

               tmp.add( new Student(res.getInt("StudentID"), res.getNString("StudentName"), res.getNString("Email"), res.getNString("Gender"), res.getDate("BirthDate"),res.getNString("ClassName")));
            }
            return tmp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Student> searchSTByName(String name, int Class) {
        try {
            ArrayList<Student> tmp = null;
            PreparedStatement st = c.prepareStatement(String.format(searchSTBySTName, "%"+name+"%",Class));
            ResultSet res = st.executeQuery();
            tmp = new ArrayList<>();

            while (res.next()) {
                tmp.add(new Student(res.getInt("StudentID"), res.getNString("StudentName"), res.getNString("Email"), res.getNString("Gender"), res.getDate("BirthDate"),res.getNString("ClassName")));
            }
            return tmp;
        } catch (SQLException e) {

        }
        return null;
    }
    public ArrayList<Student> searchSTByGender(String gender,int Class) {
        try {
            ArrayList<Student> tmp = null;
            PreparedStatement st = c.prepareStatement(String.format(searchSTByGender, gender,Class));
            ResultSet res = st.executeQuery();
            tmp = new ArrayList<>();
            while (res.next()) {

                tmp.add(new Student(res.getInt("StudentID"), res.getNString("StudentName"), res.getNString("Email"), res.getNString("Gender"), res.getDate("BirthDate"),res.getNString("ClassName")));
            }
            return tmp;
        } catch (SQLException e) {

        }
        return null;
    }
    public int getBID(){
        try {
            ResultSet res=sta.executeQuery(getBID);
            while(res.next()){
                return res.getInt("next");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getAssID(){
        try {
            ResultSet res=sta.executeQuery(getAssID);
            while(res.next()){
                return res.getInt("next");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getSMID(){
        try {
            ResultSet res=sta.executeQuery(getSMID);
            while(res.next()){
                return res.getInt("next");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public String[] getAdminMail(){
        try {
            String[] tmp= new String[2];
            ResultSet res=sta.executeQuery(getAminMail);
            while(res.next()){
                tmp[0]=res.getNString("Email");
                tmp[1]=res.getNString("Pass");
                return tmp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getSTResult(int id){
        try{
            PreparedStatement st= c.prepareStatement(String.format(result,id));
            return st.executeQuery();
        } catch (SQLException e) {

        }
        return null;
    }
    public boolean updateSTEmail(String email, int id){
        try {

            PreparedStatement st=c.prepareStatement(String.format(updateSTEmail,email,id));
            int r= st.executeUpdate();
            if(r==1) return true;
        } catch (SQLException e) {

        }
        return false;
    }
    public boolean updateTEmail(String email, int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(updateTEmail,email,id));
            int r= st.executeUpdate();
            if(r==1) return true;
        } catch (SQLException e) {

        }
        return false;
    }
    public boolean resetStudentPass(String pass, int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(resetPassST,pass,id));
            int r= st.executeUpdate();
            if(r==1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return false;
    }
    public boolean resetTeacherPass(String pass, int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(resetPassTe,pass,id));
            int r= st.executeUpdate();
            if(r==1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet stInfor(int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(stInfor,id));
            return st.executeQuery();
        } catch (SQLException e) {

        }
        return null;
    }
    public ResultSet TInfor(int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(TInfor,id));
            return st.executeQuery();
        } catch (SQLException e) {

        }
        return null;
    }
    public ResultSet allStudent(int Student){
        try{
            PreparedStatement st= c.prepareStatement(String.format(allStudent,Student));
            ResultSet res= st.executeQuery();
            return res;
        } catch (SQLException e) {

        }
        return null;
    }
    public ArrayList<Integer> getNotFinished(int id){
        try{
            PreparedStatement st= c.prepareStatement(String.format(getNotFinish,id));
            ResultSet res= st.executeQuery();
            ArrayList<Integer> ans= new ArrayList<>();
            while(res.next()){
                ans.add(res.getInt("AssignmentID"));
            }
            return ans;
        } catch (SQLException e) {

        }
        return null;
    }
    public HashMap<Integer,String> getStudentAssignment(int stID){
        try{
            PreparedStatement st= c.prepareStatement(String.format(getAllAssignmentOfAStudent,stID));
            ResultSet res= st.executeQuery();
           HashMap<Integer,String> ans= new HashMap<>();

            while(res.next()){
                ans.put(res.getInt("AssignmentID"),res.getNString("AssignmentName"));
            }
            return ans;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
   public ResultSet getallBQ(int AssignmentID){
       try {
           PreparedStatement st= c.prepareStatement(String.format(GetallBQ,AssignmentID));
           ResultSet res= st.executeQuery();
          return res;
       } catch (SQLException e) {
            e.printStackTrace();
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
            URL resourceUrl = getClass().getClassLoader().getResource("Security/ConnectionToSQL.dat");
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
            System.out.println(connectionS);
            this.c=DriverManager.getConnection("jdbc:sqlserver://172.17.0.1:1433;Database=data;User=sa;Password=Thanglm#2006;encrypt=true;trustServerCertificate=true;loginTimeout=30;");
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
            String text= String.format(search,txt+"%");
            res=sta.executeQuery(text);
        } catch (SQLException e) {

        }
        return res;
    }
    public ResultSet  Search2(String txt){
        ResultSet res=null;
        try {
            String text= String.format(search2,txt+"%");
            res=sta.executeQuery(text);
        } catch (SQLException e) {

        }
        return res;
    }
    public ResultSet LoginStudent(String Acc){
        try {
            ResultSet res= sta.executeQuery(String.format(loginStudent,Acc));
            if(!res.next()){
                return null;
            }
            ResultSet res1= sta.executeQuery(String.format(loginStudent,Acc));
           return res1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet LoginTeacher(String Acc){
        try {
            ResultSet res= sta.executeQuery(String.format(loginTeacher,Acc));
            if(!res.next()){
                return null;
            }
            ResultSet res1= sta.executeQuery(String.format(loginTeacher,Acc));
           return res1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean insertTeacher(String acc, String pass, String name,String Gender, String email, String birth){

        try {
            int id=0;
            ResultSet res=sta.executeQuery("SELECT IDENT_CURRENT('TeacherAccount') + 1 AS next;");
            while(res.next()){
                id=res.getInt("next");
            }
            String txt= String.format(registerForTeacher,acc,pass,id,name,Gender,email, birth);
            PreparedStatement pp= c.prepareStatement(txt);
            pp.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean insertStudent(String acc, String pass, String name,String Gender, String email, String birth){

        try {
            int id=0;
            ResultSet res=sta.executeQuery("SELECT IDENT_CURRENT('StudentAccount') + 1 AS next;");
            while(res.next()){
                id=res.getInt("next");
            }
            String txt= String.format(registerForStudent,acc,pass,id,name,Gender,email, birth);
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
        System.out.println(s.allStudent);
    }
}
