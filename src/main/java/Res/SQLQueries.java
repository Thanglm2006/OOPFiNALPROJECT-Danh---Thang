package Res;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import Object.Student;
import Res.Queries;
import Object.Pair;
import javax.swing.*;

public class SQLQueries {
    private Connection c=null;
    private Statement sta=null;
    private Queries q= new Queries();
    public int getSTID(String Acc){
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getGetSTID(),Acc));
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
            PreparedStatement st= c.prepareStatement(String.format(q.getGetTID(),Acc));
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
            PreparedStatement st= c.prepareStatement(String.format(q.getInsertAssToClass(),Ass,ClassID));
            int c= st.executeUpdate();
            if(c==1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void updateAss(int id, String name){
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getUpdateAss(),name,id));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAss(int id){
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getDeleteAss(),id));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getAllAss(int Teacher){
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getGetAllAss(),Teacher));
            return st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteStudentOutOfClass(int Student, int Class){
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getDeleteStudentOutOfCLass(),Student,Class));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean insertStudentIntoClass(int Student, int Class){
        int check=0;
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getInsertStudentIntoClass(),Student,Class));
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
            ResultSet res=sta.executeQuery(String.format(q.getGetAllStudent(),Teacher,Class));
            tmp = new ArrayList<>();
            while(res.next()) {
                int STID=res.getInt("StudentID");
                String ID;
                if(STID<10) ID=String.valueOf("STELN00"+STID);
                else if(STID<100)ID=String.valueOf("STELN0"+STID);
                else ID=String.valueOf("STELN"+STID);
                Student tmp1=new Student(ID, res.getNString("StudentName"), res.getNString("Gender"),res.getNString("Email"), res.getDate("BirthDate"),res.getNString("ClassName"));
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
            PreparedStatement st= c.prepareStatement(String.format(q.getInsertClass(),name,teacher));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public HashMap<Integer, String> getClass(int id){
        HashMap<Integer,String> tmp= new HashMap<>();
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getCl(),id));
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
            PreparedStatement st= c.prepareStatement(String.format(q.getSaveAssignment(),name,teacher));
            st.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveBigQuestion(int AssignmentID, String BQuestionText,String Audio){
       if(Audio!=null){
              try {
                PreparedStatement st= c.prepareStatement(String.format(q.getSaveBigQuestion(),AssignmentID,BQuestionText));
                st.executeUpdate();
                  c.commit();
                ResultSet res=sta.executeQuery("SELECT IDENT_CURRENT('BigQuestion') AS next;");
                int BQuestionID=1;
                while(res.next()){
                     BQuestionID=res.getInt("next");
                }
                PreparedStatement st1= c.prepareStatement(String.format(q.getSaveAudioForBQ(),BQuestionID,Audio));
                st1.executeUpdate();
                  c.commit();
              } catch (SQLException e) {
                e.printStackTrace();
              }
       }
       else{
              try {
                PreparedStatement st= c.prepareStatement(String.format(q.getSaveBigQuestion(),AssignmentID,BQuestionText));
                st.executeUpdate();
                  c.commit();
              } catch (SQLException e) {
                e.printStackTrace();
              }
       }

    }
    public void saveSmallQuestion(int BQuestionID, String QuestionText, String Audio){
        if(Audio!=null){
            try {
                PreparedStatement st= c.prepareStatement(String.format(q.getSaveSmallQuestion(),BQuestionID,QuestionText));
                st.executeUpdate();
                c.commit();
                ResultSet res=sta.executeQuery("SELECT IDENT_CURRENT('Question') AS next;");
                int QuestionID=1;
                while(res.next()){
                    QuestionID=res.getInt("next");
                }
                PreparedStatement st1= c.prepareStatement(String.format(q.getSaveAudioForQ(),QuestionID,Audio));
                st1.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                PreparedStatement st= c.prepareStatement(String.format(q.getSaveSmallQuestion(),BQuestionID,QuestionText));
                st.executeUpdate();
                c.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
    public void saveSelection(int QuestionID, String SelectionText, int TrueFalse){
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getSaveSelection(),QuestionID,SelectionText,TrueFalse));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Student> searchSTByEmail(String Email, int Class) {
        try{
            ArrayList<Student> tmp = null;
            PreparedStatement st= c.prepareStatement(String.format(q.getSearchSTByEmail(),"%"+Email+"%",Class));
            ResultSet res= st.executeQuery();
            tmp = new ArrayList<>();
            while(res.next()) {
                int STID=res.getInt("StudentID");
                String ID;
                if(STID<10) ID=String.valueOf("STELN00"+STID);
                else if(STID<100)ID=String.valueOf("STELN0"+STID);
                else ID=String.valueOf("STELN"+STID);
                tmp.add( new Student(ID, res.getNString("StudentName"), res.getNString("Email"), res.getNString("Gender"), res.getDate("BirthDate"),res.getNString("ClassName")));
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
            PreparedStatement st= c.prepareStatement(String.format(q.getSearchSTBySTID(),id,Class));
            ResultSet res= st.executeQuery();
            tmp = new ArrayList<>();
            while(res.next()) {
                int STID=res.getInt("StudentID");
                String ID;
                if(STID<10) ID=String.valueOf("STELN00"+STID);
                else if(STID<100)ID=String.valueOf("STELN0"+STID);
                else ID=String.valueOf("STELN"+STID);
               tmp.add( new Student(ID, res.getNString("StudentName"), res.getNString("Email"), res.getNString("Gender"), res.getDate("BirthDate"),res.getNString("ClassName")));
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
            PreparedStatement st = c.prepareStatement(String.format(q.getSearchSTBySTName(), "%"+name+"%",Class));
            ResultSet res = st.executeQuery();
            tmp = new ArrayList<>();

            while (res.next()) {
                int STID=res.getInt("StudentID");
                String ID;
                if(STID<10) ID=String.valueOf("STELN00"+STID);
                else if(STID<100)ID=String.valueOf("STELN0"+STID);
                else ID=String.valueOf("STELN"+STID);
                tmp.add(new Student(ID, res.getNString("StudentName"), res.getNString("Email"), res.getNString("Gender"), res.getDate("BirthDate"),res.getNString("ClassName")));
            }
            return tmp;
        } catch (SQLException e) {

        }
        return null;
    }
    public ArrayList<Student> searchSTByGender(String gender,int Class) {
        try {
            ArrayList<Student> tmp = null;
            PreparedStatement st = c.prepareStatement(String.format(q.getSearchSTByGender(), gender,Class));
            ResultSet res = st.executeQuery();
            tmp = new ArrayList<>();
            while (res.next()) {
                int STID=res.getInt("StudentID");
                String ID;
                if(STID<10) ID=String.valueOf("STELN00"+STID);
                else if(STID<100)ID=String.valueOf("STELN0"+STID);
                else ID=String.valueOf("STELN"+STID);
                tmp.add(new Student(ID, res.getNString("StudentName"), res.getNString("Email"), res.getNString("Gender"), res.getDate("BirthDate"),res.getNString("ClassName")));
            }
            return tmp;
        } catch (SQLException e) {

        }
        return null;
    }
    public int getBID(){
        try {
            ResultSet res=sta.executeQuery(q.getGetBID());
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
            ResultSet res=sta.executeQuery(q.getGetAssID());
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
            ResultSet res=sta.executeQuery(q.getGetSMID());
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
            ResultSet res=sta.executeQuery(q.getGetAminMail());
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
            PreparedStatement st= c.prepareStatement(String.format(q.getResult(),id));
            return st.executeQuery();
        } catch (SQLException e) {

        }
        return null;
    }
    public boolean updateSTEmail(String email, int id){
        try {

            PreparedStatement st=c.prepareStatement(String.format(q.getUpdateSTEmail(),email,id));
            int r= st.executeUpdate();
            if(r==1) return true;
        } catch (SQLException e) {

        }
        return false;
    }
    public boolean updateTEmail(String email, int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(q.getUpdateTEmail(),email,id));
            int r= st.executeUpdate();
            if(r==1) return true;
        } catch (SQLException e) {

        }
        return false;
    }
    public boolean resetStudentPass(String pass, int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(q.getResetPassST(),pass,id));
            int r= st.executeUpdate();
            if(r==1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return false;
    }
    public boolean resetTeacherPass(String pass, int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(q.getResetPassTe(),pass,id));
            int r= st.executeUpdate();
            if(r==1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet stInfor(int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(q.getStInfor(),id));
            return st.executeQuery();
        } catch (SQLException e) {

        }
        return null;
    }
    public ResultSet TInfor(int id){
        try {
            PreparedStatement st=c.prepareStatement(String.format(q.getTInfor(),id));
            return st.executeQuery();
        } catch (SQLException e) {

        }
        return null;
    }
    public ResultSet allStudent(int Student){
        try{

            PreparedStatement st= c.prepareStatement(String.format(q.getAllST(),Student));
            ResultSet res= st.executeQuery();
            return res;
        } catch (SQLException e) {

        }
        return null;
    }
    public ArrayList<Integer> getNotFinished(int id){
        try{
            PreparedStatement st= c.prepareStatement(String.format(q.getGetNotFinish(),id));
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
            PreparedStatement st= c.prepareStatement(String.format(q.getGetAllAssignmentOfAStudent(),stID));
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
           PreparedStatement st= c.prepareStatement(String.format(q.getGetallBQ(),AssignmentID));
           ResultSet res= st.executeQuery();
          return res;
       } catch (SQLException e) {
            e.printStackTrace();
       }
       return null;
   }
   public float getScore(int as, int stu){
       try {
           ResultSet res =sta.executeQuery(String.format(q.getGetScore(),stu,as));
           while(res.next()){
               return res.getFloat("Score");
           }
       } catch (SQLException e) {

       }
       return 0;
   }
   public boolean updateProgress(int as, int stu,float sc){
       try {
           PreparedStatement st= c.prepareStatement(String.format(q.getSc(),as,stu,as,stu,sc));
           ResultSet res =sta.executeQuery(String.format(q.getGetScore(),stu,as));
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
            PreparedStatement st= c.prepareStatement(String.format(q.getFindNumberofBQ(),AssignmentID));
            ResultSet res= st.executeQuery();
            while(res.next()){
                tmp.add(res.getNString("BQuestionText"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }
    public ArrayList<Pair<String,Integer>> getSelectionForQuestion(int QID){
        ArrayList<Pair<String,Integer>> tmp= new ArrayList<>();
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getGetSelection(),QID));
            ResultSet res=st.executeQuery();
            while(res.next()){
                tmp.add(new Pair<>(res.getNString("SelectionText"),res.getInt("TrueFalse")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }
    public ResultSet getQRes(int BQID){
        try {
            PreparedStatement st= c.prepareStatement(String.format(q.getGetQRes(),BQID));
            ResultSet res= st.executeQuery();
            if(!res.next()) return null;
            return st.executeQuery();
        } catch (SQLException e) {

        }
        return null;
    }

    public SQLQueries() {
        String connectionS;

        URL url = getClass().getClassLoader().getResource("Security/ConnectionToSQL.dat");

        if (url != null) {
            try (InputStream inputStream = getClass().getResourceAsStream("/Security/ConnectionToSQL.dat");
                 ObjectInputStream ois = new ObjectInputStream(inputStream)) {
                connectionS = (String) ois.readObject();
                this.c = DriverManager.getConnection(connectionS);
                this.sta = c.createStatement();
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                JFrame f= new JFrame();
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
                f.setLocationRelativeTo(null);
                f.setLayout(new BorderLayout());
                JLabel l= new JLabel("Đã xảy ra lỗi khi kết nối đến hệ thống, vui lòng kiểm tra lại kết nối mạng!");
                l.setFont(new Font("Arial",Font.BOLD,40));
                f.add(l,BorderLayout.CENTER);
                f.setVisible(true);
            }
        } else {
            System.out.println("Resource not found: Security/ConnectionToSQL.dat");
        }
    }
    public String[] getTeacherMail(String Acc){
        try {
            String txt=String.format(q.getSendTeacher(),Acc,Acc);
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
            String txt=String.format(q.getSendStudent(),Acc,Acc);
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
            String text= String.format(q.getSearch(),txt+"%",txt);
            res=sta.executeQuery(text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public ResultSet  Search2(String txt){
        ResultSet res=null;
        try {
            String text= String.format(q.getSearch2(),txt+"%",txt);
            res=sta.executeQuery(text);
        } catch (SQLException e) {

        }
        return res;
    }
    public ResultSet LoginStudent(String Acc){
        try {
            ResultSet res= sta.executeQuery(String.format(q.getLoginStudent(),Acc));
            if(!res.next()){
                return null;
            }
            ResultSet res1= sta.executeQuery(String.format(q.getLoginStudent(),Acc));
           return res1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet LoginTeacher(String Acc){
        try {
            ResultSet res= sta.executeQuery(String.format(q.getLoginTeacher(),Acc));
            if(!res.next()){
                return null;
            }
            ResultSet res1= sta.executeQuery(String.format(q.getLoginTeacher(),Acc));
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
            String txt= String.format(q.getRegisterForTeacher(),acc,pass,id,name,Gender,email, birth);
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
            String txt= String.format(q.getRegisterForStudent(),acc,pass,id,name,Gender,email, birth);
            PreparedStatement pp= c.prepareStatement(txt);
            pp.executeUpdate();
            System.out.println("done");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void main(String[] args) throws SQLException {
        SQLQueries s=new SQLQueries();
        ResultSet  res=s.getQRes(6);
        while(res.next()){
            System.out.println(res.getNString("QuestionText"));
        }
    }
}
