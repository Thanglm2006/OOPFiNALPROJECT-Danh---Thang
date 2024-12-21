package Object;

import java.util.ArrayList;
public class Assignment {
    private int AssignmentID;
    private String AssignmentName;
    private ArrayList<BigQuestion> BQ=new ArrayList<>();
    public Assignment(int assignmentID, String assignmentName) {
        AssignmentID = assignmentID;
        AssignmentName = assignmentName;
    }
    public void addQuestion(int BQuestionID,String BQuestionText){
        BQ.add(new BigQuestion(BQuestionID,AssignmentID,BQuestionText));
    }
    public void addQuestion(int BQuestionID, String BQuestionText, String audio){
        BQ.add(new BigQuestion(BQuestionID,AssignmentID,BQuestionText,audio));
    }
}