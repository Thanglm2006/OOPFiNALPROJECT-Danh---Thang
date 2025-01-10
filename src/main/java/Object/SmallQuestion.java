package Object;

public class SmallQuestion {
    private String QuestionText;
    private int BQuestionID,ID;
    private String audio;
    private String[] selection;
    private int correctAnswer;
    public SmallQuestion(String questionText, int BQuestionID, int ID,String audio,String[] selection, int correctAnswer) {
        QuestionText = questionText;
        this.ID=ID;
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

    public SmallQuestion(String questionText, int BQuestionID,int ID, String [] selection, int correctAnswer) {
        QuestionText = questionText;
        this.ID=ID;
        this.correctAnswer=correctAnswer;
        this.BQuestionID = BQuestionID;
        this.selection=selection;
    }

    public String getQuestionText() {
        return QuestionText;
    }
    public int getId(){return this.ID;}
    public int getBQuestionID() {
        return BQuestionID;
    }
    public String getSelection(int i){
        return selection[i];
    }
}
