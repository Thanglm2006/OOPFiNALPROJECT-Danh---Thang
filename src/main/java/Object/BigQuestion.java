package Object;

import java.util.ArrayList;

public class BigQuestion {
    private int BQuestionID,AssignmentID;
    private String BQuestionText;
    private String Audio;
    public BigQuestion(int BQuestionID, int assignmentID, String BQuestionText) {
        this.BQuestionID = BQuestionID;
        AssignmentID = assignmentID;
        this.BQuestionText = BQuestionText;
    }
    public BigQuestion(int BQuestionID, int assignmentID, String BQuestionText, String audio) {
        this.BQuestionID = BQuestionID;
        AssignmentID = assignmentID;
        this.BQuestionText = BQuestionText;
        Audio = audio;
    }


    public int getBQuestionID() {
        return BQuestionID;
    }

    public int getAssignmentID() {
        return AssignmentID;
    }

    public String getBQuestionText() {
        return BQuestionText;
    }
}
