package Object;

public class SmallQuestion {
    private String QuestionText;
    private int QuestionID,BQuestionID;
    private String audio;
    private String[] selection;
    private int correctAnswer;
    public SmallQuestion(String questionText, int questionID, int BQuestionID, String audio,String[] selection, int correctAnswer) {
        QuestionText = questionText;
        QuestionID = questionID;
        this.correctAnswer=correctAnswer;
        this.BQuestionID = BQuestionID;
        this.audio = audio;
        this.selection=selection;
    }

    public String getAudio() {
        return audio;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public SmallQuestion(String questionText, int questionID, int BQuestionID, String [] selection, int correctAnswer) {
        QuestionText = questionText;
        QuestionID = questionID;
        this.correctAnswer=correctAnswer;
        this.BQuestionID = BQuestionID;
        this.selection=selection;
    }

    public String getQuestionText() {
        return QuestionText;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public int getBQuestionID() {
        return BQuestionID;
    }
    public String getSelection(int i){
        return selection[i];
    }
}
