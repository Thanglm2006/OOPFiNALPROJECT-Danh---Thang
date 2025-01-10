package Object;

import java.util.Date;

public class Student {
    private String Name, Email, Gender,Class,ID;
    private Date birthDate;
    private boolean isSelected=false;
    public Student(String ID, String name, String gender, String email, Date birthDate, String Class) {
        Name = name;
        Email = email;
        Gender = gender;
        this.birthDate = birthDate;
        this.ID = ID;
        this.Class=Class;
    }

    public String GetClass() {
        return Class;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public void setGender(String gender) {
        Gender = gender;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getGender() {
        return Gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getID() {
        return ID;
    }
}
