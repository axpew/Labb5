package domain;

import java.util.Date;

public class Employee {

    private int id;
    private String lastname;
    private String firstname;
    private String title;
    private Date birthday;

    public Employee(int id, String lastname, String firstname, String title, Date birthday) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.title = title;
        this.birthday = birthday;
    }

    public Employee(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public int getAge(Date date){
        //Deben implementar el método getAge de la clase utility
        return util.Utility.getAge(date);
    }

    public int getAge(){return getAge(this.birthday);}

    @Override
    public String toString() {
        return  "(ID)"+id +"/(Name)"+lastname+", "+firstname
                + "/(Birthday)"+util.Utility.dateFormat(birthday)+ "/(Title)"+title
                +"/(Age)"+ getAge(birthday);
    }
}
