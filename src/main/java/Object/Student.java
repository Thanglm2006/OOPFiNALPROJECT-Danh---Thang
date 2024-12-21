package Object;

import java.util.Date;

public class Student {
    private String Name, Email, Gender;
    private Date birthDate;
    private int ID;

    public Student(int ID, String name, String email, String gender, Date birthDate) {
        Name = name;
        Email = email;
        Gender = gender;
        this.birthDate = birthDate;
        this.ID = ID;
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

    public void setID(int ID) {
        this.ID = ID;
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

    public int getID() {
        return ID;
    }
}
