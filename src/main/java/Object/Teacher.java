package Object;

import java.util.Date;

public class Teacher {
    private String Name, Email,Gender;
    private int ID;
    private Date birthDate;;

    public Teacher(int ID, String gender, String email, String name, Date birthDate) {
        this.ID = ID;
        Gender = gender;
        Email = email;
        Name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
