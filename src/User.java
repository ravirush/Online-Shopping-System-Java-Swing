// Define a class named User
// This class represents a user in the online shopping system
public class User {
    // Private fields to store user information
    private String username;
    private String password;

    // Constructor to initialize a user with a specified username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter method for retrieving the username of the user
    public String getUsername() {
        return username;
    }

    // Setter method for updating the username of the user
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
