package Res;

public class Queries {
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
    private String search = "select Top 100 Word, Pronunciation, FilePath, Meaning, CategoryName " +
            "from AudioForVocab " +
            "join Vocabulary on Vocabulary.WordID = AudioForVocab.WordID " +
            "where Vocabulary.Word like '%s' " +
            "order by dbo.Levenshtein(Vocabulary.Word, '%s');";
    private String search2="select Top 100 Word,Pronunciation,FilePath,Meaning,CategoryName From AudioForVocab" +"\n"+
            " join Vocabulary on Vocabulary.WordID=AudioForVocab.WordID" +"\n"+
            " where Vocabulary.Meaning like '%s' "+
            "order by dbo.Levenshtein(Vocabulary.Meaning, '%s');";
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
            "group by student.StudentName,student.StudentID\n" +
            "order by score desc";
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

    public String getGetSTID() {
        return getSTID;
    }

    public String getGetTID() {
        return getTID;
    }

    public String getGetAllAssignmentOfAStudent() {
        return getAllAssignmentOfAStudent;
    }

    public String getGetScore() {
        return getScore;
    }

    public String getResult() {
        return result;
    }

    public String getSc() {
        return score;
    }

    public String getSearch() {
        return search;
    }

    public String getSearch2() {
        return search2;
    }

    public String getRegisterForStudent() {
        return registerForStudent;
    }

    public String getRegisterForTeacher() {
        return registerForTeacher;
    }

    public String getLoginStudent() {
        return loginStudent;
    }

    public String getLoginTeacher() {
        return loginTeacher;
    }

    public String getSendStudent() {
        return sendStudent;
    }

    public String getSendTeacher() {
        return sendTeacher;
    }

    public String getFindNumberofBQ() {
        return findNumberofBQ;
    }

    public String getGetAudioForBQ() {
        return getAudioForBQ;
    }

    public String getGetSelection() {
        return getSelection;
    }

    public String getGetQRes() {
        return GetQRes;
    }

    public String getGetallBQ() {
        return GetallBQ;
    }

    public String getGetAllAss() {
        return getAllAss;
    }

    public String getGetNotFinish() {
        return getNotFinish;
    }

    public String getAllST() {
        return allStudent;
    }

    public String getSearchSTBySTName() {
        return searchSTBySTName;
    }

    public String getSearchSTByEmail() {
        return searchSTByEmail;
    }

    public String getSearchSTByGender() {
        return searchSTByGender;
    }

    public String getSearchSTBySTID() {
        return searchSTBySTID;
    }

    public String getGetAllStudent() {
        return getAllStudent;
    }

    public String getTInfor() {
        return TInfor;
    }

    public String getStInfor() {
        return stInfor;
    }

    public String getResetPassST() {
        return resetPassST;
    }

    public String getResetPassTe() {
        return resetPassTe;
    }

    public String getSaveAssignment() {
        return saveAssignment;
    }

    public String getSaveBigQuestion() {
        return saveBigQuestion;
    }

    public String getSaveSmallQuestion() {
        return saveSmallQuestion;
    }

    public String getSaveAudioForBQ() {
        return saveAudioForBQ;
    }

    public String getSaveAudioForQ() {
        return saveAudioForQ;
    }

    public String getSaveSelection() {
        return saveSelection;
    }

    public String getUpdateSTEmail() {
        return updateSTEmail;
    }

    public String getUpdateTEmail() {
        return updateTEmail;
    }

    public String getGetAminMail() {
        return getAminMail;
    }

    public String getGetBID() {
        return getBID;
    }

    public String getGetAssID() {
        return getAssID;
    }

    public String getGetSMID() {
        return getSMID;
    }

    public String getCl() {
        return getClass;
    }

    public String getInsertClass() {
        return insertClass;
    }

    public String getDeleteStudentOutOfCLass() {
        return deleteStudentOutOfCLass;
    }

    public String getInsertStudentIntoClass() {
        return insertStudentIntoClass;
    }

    public String getGetAllStudentAcc() {
        return getAllStudentAcc;
    }

    public String getGetAllTeacherAcc() {
        return getAllTeacherAcc;
    }

    public String getChangeSTPass() {
        return ChangeSTPass;
    }

    public String getChangeTPass() {
        return ChangeTPass;
    }

    public String getDeleteAss() {
        return deleteAss;
    }

    public String getUpdateAss() {
        return updateAss;
    }

    public String getInsertAssToClass() {
        return insertAssToClass;
    }
}
