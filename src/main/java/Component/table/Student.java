package Component.table;

public class Student {
    private int stt;
    private String id;
    private String name;
    private String className;
    private String sex;
    private String email;
    private String dob;
    private boolean selected;

    public Student(int stt, String id, String name, String className, String sex, String email, String dob, boolean selected) {
        this.stt = stt;
        this.id = id;
        this.name = name;
        this.className = className;
        this.sex = sex;
        this.email = email;
        this.dob = dob;
        this.selected = selected;
    }

    public int getStt() {
        return stt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getSex() {
        return sex;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
