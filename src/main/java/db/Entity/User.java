package db.Entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    //FIELDS
    private int id;
    private String firstName, lastName, login, password, email, telNumber;
    private int userType;
    private List<Book> bookList;

    //CONSTRUCTOR
    public User() {
        bookList = new ArrayList<>();
    }
    //METHODS
    public boolean addBookToUser(Book book){
        if(!bookList.contains(book)){
            bookList.add(book);
            return true;
        }
        return false;
    }



    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
